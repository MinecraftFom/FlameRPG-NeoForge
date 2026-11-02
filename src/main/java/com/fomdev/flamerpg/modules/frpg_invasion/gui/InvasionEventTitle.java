package com.fomdev.flamerpg.modules.frpg_invasion.gui;

import com.fomdev.flamerpg.init.FlameRPG;
import com.fomdev.flamerpg.modules.frpg_invasion.invasion.InvasionType;
import com.fomdev.flamerpg.util.RGBColor;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class InvasionEventTitle implements LayeredDraw.Layer
{
    @Override
    public void render(@NotNull GuiGraphics graphics, @NotNull DeltaTracker tracker)
    {
        new InvasionTitleDrawer(new InvasionType(new RGBColor(255, 255, 255, 255), new RGBColor(0, 0, 0, 255), Component.literal("惨白的午夜"), Component.literal("飞机被"))).onDraw(graphics);
    }
}