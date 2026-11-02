package com.fomdev.flamerpg.modules.frpg_invasion.gui;

import com.fomdev.flamerpg.init.FlameRPG;
import com.fomdev.flamerpg.modules.frpg_invasion.invasion.InvasionType;
import com.fomdev.flamerpg.util.RGBColor;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public class InvasionTitleDrawer
{
    public static final ResourceLocation BACKGROUND = ResourceLocation.fromNamespaceAndPath(FlameRPG.MODID, "textures/gui/invasion_notification_bg.png");
    public static final ResourceLocation FRONT      = ResourceLocation.fromNamespaceAndPath(FlameRPG.MODID, "textures/gui/invasion_notification_title.png");

    public static final int WIDTH  = 470;
    public static final int HEIGHT = 270;

    public static final float ALPHA = 0.75F; // Not too opaque

    private final InvasionType type;

    public InvasionTitleDrawer(InvasionType type)
    {
        this.type = type;
    }

    public void onDraw(GuiGraphics graphics)
    {
        RGBColor color = this.type.color();
        RGBColor background = this.type.background();
        String title = this.type.title().getString();
        String desc = this.type.desc().getString();

        Font font = Minecraft.getInstance().font;
        int titleWidth = font.width(title);
        int descWidth = font.width(desc) / 2;

        int gw = graphics.guiWidth();
        int gh = graphics.guiHeight();

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        graphics.setColor(background.red() / 255.0F, background.green() / 255.0F, background.blue() / 255.0F, background.alpha() / 255.0F);
        graphics.blit(BACKGROUND, 0, 0, 0F, 0F, gw, gh, WIDTH, HEIGHT);
        graphics.setColor(color.red() / 255.0F, color.green() / 255.0F, color.blue() / 255.0F, ALPHA);
        graphics.blit(FRONT, 0, 0, 0F, 0F, gw, gh, WIDTH, HEIGHT);
        graphics.drawString(font, title, (gw - titleWidth) / 2, (gh + font.lineHeight) / 2, background.rgba());
        graphics.pose().pushPose();
        graphics.pose().scale(0.5F, 0.5F, 0.5F);
        graphics.drawString(font, desc, (gw - descWidth), (gh + font.lineHeight * 4), background.rgba());
        graphics.pose().popPose();
        graphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);

        RenderSystem.disableBlend();
    }
}