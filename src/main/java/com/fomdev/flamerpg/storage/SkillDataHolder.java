package com.fomdev.flamerpg.storage;

import com.fomdev.flamerpg.registry.data.FRPGAttachments;
import com.fomdev.flamerpg.registry.skill.Skill;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

public record SkillDataHolder(
        int points,
        List<ResourceLocation> unlocked
)
{
    public static final Codec<SkillDataHolder> CODEC;
    public static final StreamCodec<ByteBuf, SkillDataHolder> STREAM_CODEC;

    public SkillDataHolder(SkillDataHolder parent)
    {
        this(parent.points, parent.unlocked);
    }

    public SkillDataHolder(Player player)
    {
        this(loadFromPlayer(player));
    }

    public static SkillDataHolder empty()
    {
        return new SkillDataHolder(0, List.of());
    }

    public static SkillDataHolder loadFromPlayer(Player player)
    {
        return player.getData(FRPGAttachments.DC_SKILL);
    }

    public SkillDataHolder(int points, List<ResourceLocation> unlocked)
    {
        this.points = points;
        this.unlocked = new ArrayList<>(unlocked);
    }

    public SkillDataHolder addPoint(int v)
    {
        return this.setPoint(this.points + v);
    }

    public SkillDataHolder clear()
    {
        return empty();
    }

    public SkillDataHolder clearUnlocked()
    {
        return new SkillDataHolder(this.points, List.of());
    }

    public SkillDataHolder consumePoint(int v)
    {
        return this.addPoint(-v);
    }

    public SkillDataHolder setPoint(int v)
    {
        return new SkillDataHolder(v, this.unlocked);
    }

    public SkillDataHolder unlock(ResourceLocation v)
    {
        this.unlocked.add(v);
        return this;
    }

    public boolean isSkillAvailable(Skill.SkillDependencies dependencies)
    {
        for (ResourceLocation dep: dependencies.parent())
            if (!this.unlocked.contains(dep))
                return false;

        return dependencies.points() <= this.points();
    }

    public SkillDataHolder consume(ResourceLocation id, int amount)
    {
        SkillDataHolder holder = this.unlock(id);
        return holder.consumePoint(amount);
    }

    public void save(Player player)
    {
        player.setData(FRPGAttachments.DC_SKILL, this);
    }

    static
    {
        CODEC = RecordCodecBuilder.create(
                inst ->
                        inst
                                .group(
                                        Codec.INT
                                                .fieldOf("points")
                                                .forGetter(SkillDataHolder::points)
                                )
                                .and(
                                        ResourceLocation.CODEC
                                                .listOf()
                                                .fieldOf("unlocked")
                                                .forGetter(SkillDataHolder::unlocked)
                                )
                                .apply(
                                        inst,
                                        SkillDataHolder::new
                                )
        );

        STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.VAR_INT,
                SkillDataHolder::points,
                ResourceLocation.STREAM_CODEC.apply(ByteBufCodecs.list()),
                SkillDataHolder::unlocked,
                SkillDataHolder::new
        );
    }
}