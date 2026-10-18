package com.fomdev.flamerpg.modules.frpg_skill.gui;

import com.fomdev.flamerpg.modules.frpg_skill.skill.SkillPage;
import com.fomdev.flamerpg.util.IconImageResource;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.core.Holder;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class SkillTreePageButton implements GuiEventListener, Renderable, NarratableEntry
{
    private final SkillTreeScreen screen;
    private final Holder<SkillPage> page;

    private final int x;
    private final int y;
    private final int w;
    private final int h;

    private boolean focused;

    public SkillTreePageButton(SkillTreeScreen parent, Holder<SkillPage> tab, int x, int y, int w, int h)
    {
        this.screen = parent;
        this.page = tab;

        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;

        this.focused = false;
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int i, int i1, float v)
    {
        IconImageResource resource;
        if ((resource = this.page.value().iconResource().orElse(null)) == null)
            return;

        // Using switch-case syntax for future extension in display types.
        switch (resource.type())
        {
            case IMAGE -> graphics.blit(resource.resource().right().get(), this.x, this.y, 0, 0, w, h, resource.x(), resource.y());
            case ITEM  -> graphics.renderItem(resource.resource().left().get(), this.x, this.y);
        }
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
    public boolean isMouseOver(double mouseX, double mouseY)
    {
        int xr = this.x + w;
        int yr = this.y + h;

        return this.x <= mouseX && mouseX <= xr && this.y <= mouseY && mouseX <= yr;
    }

    @Override
    public @NotNull NarrationPriority narrationPriority()
    {
        return NarrationPriority.NONE;
    }

    @Override
    public void updateNarration(@NotNull NarrationElementOutput output)
    {
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button)
    {
        if (!this.isMouseOver(mouseX, mouseY) || (this.screen.currentPage != null && !this.shouldBlur()))
            return false;

        this.screen.switchPage(this.page);
        return true;
    }

    protected boolean shouldBlur()
    {
        return !this.screen.currentPage.is(Objects.requireNonNull(this.page.getKey()));
    }
}