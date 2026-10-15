package com.fomdev.flamerpg.registry.data;

import com.fomdev.flamerpg.init.FlameRPG;
import com.fomdev.flamerpg.modules.frpg_skill.storage.SkillDataHolder;
import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class FRPGAttachments
{
    private static final DeferredRegister<AttachmentType<?>> REGISTER;

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<SkillDataHolder>> DC_SKILL;

    public static void register(IEventBus bus)
    {
        REGISTER.register(bus);
    }

    private static <T> DeferredHolder<AttachmentType<?>, AttachmentType<T>> register(String id, T defaultValue, Codec<T> codec, StreamCodec<ByteBuf, T> stream)
    {
        return REGISTER.register(id, () -> AttachmentType.builder(() -> defaultValue).serialize(codec).sync(stream).build());
    }

    static
    {
        REGISTER = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, FlameRPG.MODID);
        DC_SKILL = register("skill_data_holder", SkillDataHolder.empty(), SkillDataHolder.CODEC, SkillDataHolder.STREAM_CODEC);
    }
}