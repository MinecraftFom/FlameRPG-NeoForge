package com.fomdev.flamerpg.modules.frpg_skill.gui;

import com.fomdev.flamerpg.registry.FRPGRegistries;
import com.fomdev.flamerpg.modules.frpg_skill.skill.SkillPage;
import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class SkillTreeScreen extends Screen
{
    private static final Logger LOGGER;

    private final List<Holder<SkillPage>> tabs;

    ResourceKey<SkillPage> currentPage;

    public SkillTreeScreen(Component title)
    {
        super(title);

        this.tabs = List.copyOf(FRPGRegistries.REGISTRY_SKILL_PAGE.holders().toList());
    }

    @Override
    protected void init()
    {
        super.init();
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick)
    {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    static
    {
        LOGGER = LogUtils.getLogger();
    }
}