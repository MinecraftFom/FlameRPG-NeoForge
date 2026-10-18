package com.fomdev.flamerpg.modules.frpg_skill.skill;

import com.mojang.datafixers.util.Either;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import java.util.Optional;

public record SkillPage(
        Component title,
        Component subtitle,
        Optional<IconImageResource> iconResource,
        Optional<ImageResource> backgroundResource
)
{
    public record IconImageResource(Either<ItemStack, ResourceLocation> resource, IconType type, int x, int y)
    {
        public IconImageResource(ResourceLocation location, int x, int y)
        {
            this(Either.right(location), IconType.IMAGE, x, y);
        }

        public IconImageResource(ItemStack stack)
        {
            this(Either.left(stack), IconType.ITEM, -1, -1); // X and Y don't count
        }

        public IconImageResource(Item item)
        {
            this(item.getDefaultInstance());
        }

        public IconImageResource(Block block)
        {
            this(block.asItem());
        }
    }

    public record ImageResource(ResourceLocation resource, int x, int y)
    {
        public ImageResource(ResourceLocation resource)
        {
            this(resource, 1920, 1080); // 1920x1080 image
        }
    }

    public enum IconType
    {
        ITEM,
        IMAGE
    }
}