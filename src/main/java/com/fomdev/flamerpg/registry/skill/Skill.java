package com.fomdev.flamerpg.registry.skill;

import com.fomdev.flamerpg.init.FlameRPG;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.awt.Color;
import java.util.List;
import java.util.Optional;

public record Skill(
        ResourceLocation icon // The only thing a parent Skill object can control LOL
)
{
    public record SkillDependencies(
            int points,
            List<ResourceLocation> parent
    )
    {

    }

    public record SkillDisplayProperties(
            int x,
            int y,
            int size,
            SkillShape shape,
            Optional<Color> color,
            Optional<Color> border
    )
    {

    }

    public static abstract class SkillProperties
    {
        private final Component title;
        private final Component description;

        public SkillProperties(Component title, Component description)
        {
            this.title = title;
            this.description = description;
        }

        public Component getTitle()
        {
            return this.title;
        }

        public Component getDescription()
        {
            return this.description;
        }

        public abstract void onTrigger(Player player);
    }

    public record SkillInstance(
            Skill skill,
            SkillDependencies dependencies,
            SkillDisplayProperties display,
            SkillProperties properties
    )
    {

    }

    public interface SkillShape
    {
        ResourceLocation resource();
        ResourceLocation border();
        int w();
        int h();
    }

    public enum SkillShapes implements SkillShape
    {
        CIRCLE("textures/gui/circle.png", "textures/gui/circle_bg.png", 512, 512),
        SQUARE("textures/gui/square.png", "textures/gui/square_bg.png", 512, 512),
        TRIANGLE("textures/gui/triangle.png", "textures/gui/triangle_bg.png", 512, 512);

        private final ResourceLocation background;
        private final ResourceLocation border;
        private final int w;
        private final int h;

        SkillShapes(ResourceLocation background, ResourceLocation border, int w, int h)
        {
            this.background = background;
            this.border = border;
            this.w = w;
            this.h = h;
        }

        SkillShapes(String path, String path2, int w, int h)
        {
            this(ResourceLocation.fromNamespaceAndPath(FlameRPG.MODID, path), ResourceLocation.fromNamespaceAndPath(FlameRPG.MODID, path2), w, h);
        }

        @Override
        public ResourceLocation resource()
        {
            return this.background;
        }

        @Override
        public ResourceLocation border()
        {
            return this.border;
        }

        @Override
        public int w()
        {
            return this.w;
        }

        @Override
        public int h()
        {
            return this.h;
        }
    }
}