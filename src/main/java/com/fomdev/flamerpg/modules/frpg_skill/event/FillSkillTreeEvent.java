package com.fomdev.flamerpg.modules.frpg_skill.event;

import com.fomdev.flamerpg.modules.frpg_skill.skill.Skill;
import com.fomdev.flamerpg.modules.frpg_skill.skill.SkillPage;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.Event;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.List;

public class FillSkillTreeEvent extends Event
{
    private final List<Skill.SkillInstance> skills;
    private final ResourceKey<SkillPage> page;

    public FillSkillTreeEvent(ResourceKey<SkillPage> parent)
    {
        this.skills = new ArrayList<>();
        this.page   = parent;
    }

    public void addSkill(Skill.SkillInstance skill)
    {
        this.skills.add(skill);
    }
    public boolean isFor(ResourceKey<SkillPage> parent)
    {
        return parent.registry().equals(this.page.location());
    }

    @ApiStatus.Internal public ResourceKey<SkillPage> getParentPage()
    {
        return this.page;
    }
    @ApiStatus.Internal public List<Skill.SkillInstance> getSkills()
    {
        return List.copyOf(this.skills);
    }
}