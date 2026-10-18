package com.fomdev.flamerpg.modules.frpg_skill.event;

import com.fomdev.flamerpg.modules.frpg_skill.skill.Skill;
import com.fomdev.flamerpg.modules.frpg_skill.skill.SkillPage;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.Event;
import org.jetbrains.annotations.ApiStatus;

import java.util.HashMap;
import java.util.Map;

public class FillSkillTreeEvent extends Event
{
    private final Map<ResourceLocation, Skill.SkillInstance> skills;
    private final ResourceKey<SkillPage> page;

    public FillSkillTreeEvent(ResourceKey<SkillPage> parent)
    {
        this.skills = new HashMap<>();
        this.page   = parent;
    }

    public void addSkill(ResourceLocation location, Skill.SkillInstance skill)
    {
        if (this.skills.containsKey(location))
            throw new IllegalArgumentException("Illegal unique id: ID exists");

        this.skills.put(location, skill);
    }
    public boolean isFor(ResourceKey<SkillPage> parent)
    {
        return parent.registry().equals(this.page.location());
    }

    @ApiStatus.Internal public ResourceKey<SkillPage> getParentPage()
    {
        return this.page;
    }
    @ApiStatus.Internal public Map<ResourceLocation, Skill.SkillInstance> getSkills()
    {
        return Map.copyOf(this.skills);
    }
}