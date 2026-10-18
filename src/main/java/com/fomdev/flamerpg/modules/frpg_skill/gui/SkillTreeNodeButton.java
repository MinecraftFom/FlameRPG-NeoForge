package com.fomdev.flamerpg.modules.frpg_skill.gui;

import com.fomdev.flamerpg.modules.frpg_skill.skill.Skill;
import com.fomdev.flamerpg.modules.frpg_skill.storage.SkillDataHolder;
import com.fomdev.flamerpg.util.Mth;
import com.fomdev.flamerpg.util.RGBColor;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class SkillTreeNodeButton implements GuiEventListener, Renderable, NarratableEntry
{
    public static final RGBColor TRANSPARENT_LIGHT_GRAY = new RGBColor(192, 192, 192, 192);
    public static final int MAX_CHAR_WIDTH = 32;
    public static final int TOOLTIP_OFFSET = 4;

    private final SkillTreePageRender parent;

    private final Player player;
    private final ResourceLocation uniqueId;

    private final Skill.SkillInstance skill;
    private boolean focus;

    private final int x;
    private final int y;
    private final int w;
    private final int h;

    private int xl; // XL = x_left
    private int yl; // YL = y_left
    private int xr; // XR = x_right
    private int yr; // YR = y_right

    private int xc; // XC = x_current
    private int yc; // YC = y_current

    public SkillTreeNodeButton(SkillTreePageRender parent, Player player, ResourceLocation uniqueId, Skill.SkillInstance instance)
    {
        this.parent = parent;

        this.player = player;
        this.uniqueId = uniqueId;

        this.skill = instance;
        this.focus = false;

        this.x = this.skill.display().x();
        this.y = this.skill.display().y();
        this.w = this.skill.display().size();
        this.h = this.skill.display().size();

        this.xl = this.x - this.w / 2; // Render X, left top
        this.yl = this.y - this.h / 2; // Render Y, left top
        this.xr = this.x + this.w / 2; // Render X, right bottom
        this.yr = this.y + this.h / 2; // Render Y, right bottom
        this.xc = x;
        this.yc = y;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button)
    {
        SkillDataHolder holder = new SkillDataHolder(this.player);
        if (!this.isMouseOver(mouseX, mouseY) || !holder.isSkillAvailable(this.skill.dependencies()))
            return false;

        this.skill.properties().onTrigger(this.player);
        holder.consume(this.uniqueId, this.skill.dependencies().points()).save(this.player);
        return true;
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mx, int my, float tick)
    {
        this.refreshPosition();

        ResourceLocation icon = this.skill.skill().icon();
        this.renderTooltip(graphics, mx, my);
        this.renderBg(graphics, mx, my, this.xl, this.yl, this.w, this.h);
        graphics.blit(icon, this.xl, this.yl, 0, 0, this.w, this.h, this.w, this.h);
    }

    @Override
    public void setFocused(boolean b)
    {
        this.focus = b;
    }

    @Override
    public boolean isFocused()
    {
        return this.focus;
    }

    @Override
    public @NotNull NarratableEntry.NarrationPriority narrationPriority()
    {
        return NarratableEntry.NarrationPriority.FOCUSED;
    }

    @Override
    public void updateNarration(@NotNull NarrationElementOutput element)
    {
        element.add(NarratedElementType.TITLE, this.skill.properties().getTitle());
        element.add(NarratedElementType.USAGE, this.skill.properties().getDescription());
    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY)
    {
        return this.xl <= mouseX && mouseX <= this.xr && this.yl <= mouseY && mouseY <= this.yr;
    }

    protected void refreshPosition()
    {
        this.xc = this.x  + this.parent.xo;
        this.yc = this.y  + this.parent.yo;
        this.xl = this.xc - this.w / 2; // Render X, left top
        this.yl = this.yc - this.h / 2; // Render Y, left top
        this.xr = this.xc + this.w / 2; // Render X, right bottom
        this.yr = this.yc + this.h / 2; // Render Y, right bottom
    }

    protected void renderBg(GuiGraphics graphics, int mx, int my, int tx, int ty, int w, int h)
    {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        Skill.SkillShape shape = this.skill.display().shape();
        RGBColor color = this.skill.display().color().orElse(TRANSPARENT_LIGHT_GRAY);
        RGBColor opposite = this.skill.display().border().orElse(new RGBColor(64, 64, 64));
        RGBColor border = this.isMouseOver(mx, my)? opposite: color;

        graphics.setColor(color.red() / 255.0F, color.green() / 255.0F, color.blue() / 255.0F, color.alpha() / 255.0F);
        graphics.blit(shape.resource(), tx, ty, w, h, 0, 0, shape.w(), shape.h(), shape.w(), shape.h());
        graphics.setColor(border.red() / 255.0F, border.green() / 255.0F, border.blue() / 255.0F, border.alpha() / 255.0F);
        graphics.blit(shape.border(), tx, ty, w, h, 0, 0, shape.w(), shape.h(), shape.w(), shape.h());
        graphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableBlend();
    }

    protected void renderTooltip(GuiGraphics graphics, int mx, int my)
    {
        if (!this.isMouseOver(mx, my))
            return;

        // Offset
        mx += TOOLTIP_OFFSET;
        my += TOOLTIP_OFFSET;

        net.minecraft.network.chat.Component title = this.skill.properties().getTitle();
        Component subtitle = this.skill.properties().getDescription();
        String titleText = title.getString();
        String subtitleText = subtitle.getString();
        TextColor titleColor = title.getStyle().getColor();
        TextColor subtitleColor = title.getStyle().getColor();

        int tc = titleColor == null? 0xFFFFFF: titleColor.getValue();
        int sc = subtitleColor == null? 0xFFFFFF: subtitleColor.getValue();

        net.minecraft.client.gui.Font font = Minecraft.getInstance().font;
        int tw = titleText.length();
        int sw = subtitleText.length();
        int lw = Math.min(Math.max(tw, sw), MAX_CHAR_WIDTH); // ... DONT MESS THIS UP WITH CLAMP()
        int th = (Mth.round(tw, lw));
        int h = th + Mth.round(sw, lw);
        int alw = font.width("_".repeat(lw));

        graphics.fill(mx, my, mx + alw, my + h * font.lineHeight, TRANSPARENT_LIGHT_GRAY.rgba());
        this.renderTitle(graphics, font, titleText, tc, mx, my, lw);
        this.renderSubtitle(graphics, font, subtitleText, sc, mx, my + th * font.lineHeight, lw);
    }

    protected void renderTitle(GuiGraphics graphics, net.minecraft.client.gui.Font font, String text, int color, int x, int y, int lw)
    {
        int lines = Mth.round(text.length(), lw);
        for (int i = 0; i < lines; i++)
        {
            String sub = i >= lines - 1? text.substring(i * lw): text.substring(i * lw, (i + 1) * lw - 1);
            graphics.drawString(font, sub, x, y + font.lineHeight * i, color);
        }
    }

    protected void renderSubtitle(GuiGraphics graphics, Font font, String text, int color, int x, int y, int lw)
    {
        graphics.pose().pushPose();
        graphics.pose().scale(0.5F, 0.5F, 1.0F);
        int lines = Mth.round(text.length(), lw);
        for (int i = 0; i < lines; i++)
        {
            String sub = i >= lines - 1? text.substring(i * lw): text.substring(i * lw, (i + 1) * lw - 1);
            graphics.drawString(font, sub, x * 2, (y + font.lineHeight * i) * 2, color);
        }
        graphics.pose().popPose();
    }
}