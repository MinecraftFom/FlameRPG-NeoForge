package com.fomdev.flamerpg.registry;

import com.fomdev.flamerpg.registry.skill.SkillPage;
import com.fomdev.flamerpg.init.FlameRPG;
import com.mojang.serialization.Lifecycle;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.NewRegistryEvent;

import java.util.ArrayList;
import java.util.List;

public class FRPGRegistries
{
    public static final List<MappedRegistry<?>> REGISTRIES;

    public static final MappedRegistry<SkillPage> REGISTRY_SKILL_PAGE;

    private static <T> MappedRegistry<T> create(ResourceKey<Registry<T>> key)
    {
        MappedRegistry<T> registry = new MappedRegistry<>(key, Lifecycle.stable());
        REGISTRIES.add(registry);
        return registry;
    }

    public static void register(IEventBus bus)
    {
        bus.addListener((NewRegistryEvent event) -> REGISTRIES.forEach(event::register));
    }

    public static class Keys
    {
        public static final ResourceKey<Registry<SkillPage>> REGISTRY_KEY_SKILL_PAGE;

        private static <T> ResourceKey<Registry<T>> create(String id)
        {
            ResourceLocation location = ResourceLocation.fromNamespaceAndPath(FlameRPG.MODID, id);
            return ResourceKey.createRegistryKey(location);
        }

        static
        {
            REGISTRY_KEY_SKILL_PAGE = create("skill_page");
        }
    }

    static
    {
        REGISTRIES = new ArrayList<>();

        REGISTRY_SKILL_PAGE = create(Keys.REGISTRY_KEY_SKILL_PAGE);
    }
}