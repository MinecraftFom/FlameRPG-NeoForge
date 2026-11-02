package com.fomdev.flamerpg.modules.frpg_invasion.invasion;

import net.minecraft.resources.ResourceLocation;

import java.util.List;

public record Invasion(
        int maxMobCount,
        int maxWaveCount,
        List<MobComponent> mobs
)
{
    public record MobComponent(
            int maxCount,
            ResourceLocation mob
    )
    {}
}