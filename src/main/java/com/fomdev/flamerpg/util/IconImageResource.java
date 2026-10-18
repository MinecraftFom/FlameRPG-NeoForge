package com.fomdev.flamerpg.util;

import com.fomdev.flamerpg.modules.frpg_skill.skill.SkillPage;
import com.mojang.datafixers.util.Either;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

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

    public enum IconType
    {
        ITEM,
        IMAGE
    }
}