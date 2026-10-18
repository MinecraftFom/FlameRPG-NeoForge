package com.fomdev.flamerpg.modules.frpg_skill.skill;

import com.fomdev.flamerpg.util.IconImageResource;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

public record SkillPage(
        Component title,
        Component subtitle,
        Optional<IconImageResource> iconResource,
        Optional<ImageResource> backgroundResource
)
{
    public record ImageResource(ResourceLocation resource, int x, int y)
    {
        public ImageResource(ResourceLocation resource)
        {
            this(resource, 1920, 1080); // 1920x1080 image
        }
    }
}