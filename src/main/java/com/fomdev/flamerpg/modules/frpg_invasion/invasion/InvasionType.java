package com.fomdev.flamerpg.modules.frpg_invasion.invasion;

import com.fomdev.flamerpg.util.RGBColor;
import net.minecraft.network.chat.Component;

public record InvasionType(
        RGBColor color,
        RGBColor background,
        Component title,
        Component desc
)
{
}