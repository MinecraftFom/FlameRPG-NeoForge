package com.fomdev.flamerpg.modules.frpg_skill.skill;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

public record SkillPage(
        Component title,
        Component subtitle,
        Optional<ResourceLocation> iconResource,
        Optional<ResourceLocation> backgroundResource
)
{
}