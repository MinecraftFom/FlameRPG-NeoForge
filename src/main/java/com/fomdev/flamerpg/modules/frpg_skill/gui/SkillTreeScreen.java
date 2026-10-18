package com.fomdev.flamerpg.modules.frpg_skill.gui;

import com.fomdev.flamerpg.registry.FRPGRegistries;
import com.fomdev.flamerpg.modules.frpg_skill.skill.SkillPage;
import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class SkillTreeScreen extends Screen
{
    public static final int ICON_IMAGE_WIDTH = 16;
    public static final int ICON_IMAGE_HEIGHT = 16;

    private static final Logger LOGGER;

    private final Player player;
    private final List<Holder<SkillPage>> tabs;
    private final List<SkillTreePageButton> pageButtons;
    private final Map<ResourceKey<SkillPage>, SkillTreePageRender> pages;

    Holder<SkillPage> currentPage;

    public SkillTreeScreen(Player player, Component title)
    {
        super(title);

        this.player = player;
        this.tabs = List.copyOf(FRPGRegistries.REGISTRY_SKILL_PAGE.holders().toList());
        this.pages = new HashMap<>();
        this.pageButtons = new ArrayList<>();

        this.currentPage = null;
    }

    @Override
    protected void init()
    {
        super.init();
        this.refreshPages();
        this.refreshPageButtons();

        this.pages.values().forEach(this::addRenderableWidget);
        this.pageButtons.forEach(this::addRenderableWidget);
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick)
    {
        super.render(guiGraphics, mouseX, mouseY, partialTick);


    }

    void switchPage(Holder<SkillPage> page)
    {
        if (!this.pages.containsKey(page.getKey()))
            throw new IllegalArgumentException("Invalid page: ID doesn't exist");

        this.currentPage = page;
    }

    protected void renderBg(GuiGraphics graphics, int mx, int my, float v)
    {
        SkillPage.ImageResource background;
        if (this.currentPage == null || (background = this.currentPage.value().backgroundResource().orElse(null)) == null)
            return;

        graphics.blit(background.resource(), 0, 0, 0, 0, graphics.guiWidth(), graphics.guiHeight(), background.x(), background.y());
    }

    protected void refreshPages()
    {
        this.pages.clear();

        for (Holder<SkillPage> page: this.tabs)
            this.pages.put(page.getKey(), new SkillTreePageRender(this, this.player, page));
    }

    protected void refreshPageButtons()
    {
        this.pageButtons.clear();

        int x = 0;
        for (Holder<SkillPage> page: this.tabs)
        {
            this.pageButtons.add(new SkillTreePageButton(this, page, x, 0, ICON_IMAGE_WIDTH, ICON_IMAGE_HEIGHT));
            x += ICON_IMAGE_WIDTH;
        }
    }

    static
    {
        LOGGER = LogUtils.getLogger();
    }
}