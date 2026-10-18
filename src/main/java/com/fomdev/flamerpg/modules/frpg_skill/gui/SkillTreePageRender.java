package com.fomdev.flamerpg.modules.frpg_skill.gui;

import com.fomdev.flamerpg.event.EventHooks;
import com.fomdev.flamerpg.modules.frpg_skill.skill.Skill;
import com.fomdev.flamerpg.modules.frpg_skill.skill.SkillPage;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SkillTreePageRender implements GuiEventListener, Renderable, NarratableEntry
{
    private final SkillTreeScreen parent;
    private final Player player;

    private final Holder<SkillPage> page;
    private final Map<ResourceLocation, Skill.SkillInstance> skills;
    private final List<SkillTreeNodeButton> subnodes;

    private boolean focused;

    int xo; // XO = x_offset
    int yo; // YO = y_offset

    public SkillTreePageRender(SkillTreeScreen parent, Player player, Holder<SkillPage> page)
    {
        this.parent = parent;
        this.player = player;

        this.page = page;
        this.skills = EventHooks.EVT_HOOK_SKILL.fillSkillTree(page.getKey());
        this.subnodes = new ArrayList<>();

        this.xo = 0;
        this.yo = 0;
        this.focused = false;

        this.refreshNodes();
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mx, int my, float v)
    {
        if (!this.shouldRender())
            return;

        this.subnodes.forEach(n -> n.render(graphics, mx, my ,v));
    }

    @Override
    public void setFocused(boolean b)
    {
        this.focused = b;
    }

    @Override
    public boolean isFocused()
    {
        return this.focused;
    }

    @Override
    public @NotNull NarrationPriority narrationPriority()
    {
        return NarrationPriority.HOVERED;
    }

    @Override
    public void updateNarration(@NotNull NarrationElementOutput output)
    {
        output.add(NarratedElementType.TITLE, page.value().title());
        output.add(NarratedElementType.USAGE, page.value().subtitle());
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY)
    {
        this.xo = (int) dragX;
        this.yo = (int) dragX;

        return GuiEventListener.super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    protected void refreshNodes()
    {
        this.subnodes.clear();

        for (Map.Entry<ResourceLocation, Skill.SkillInstance> entry: this.skills.entrySet())
            this.subnodes.add(new SkillTreeNodeButton(this, this.player, entry.getKey(), entry.getValue()));
    }

    protected boolean shouldRender()
    {
        return this.parent.currentPage != null && this.parent.currentPage.registry().equals(Objects.requireNonNull(this.page.getKey()).location());
    }
}