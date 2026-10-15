package com.fomdev.flamerpg.modules.frpg_skill.event;

import com.fomdev.flamerpg.modules.frpg_skill.skill.Skill;
import com.fomdev.flamerpg.modules.frpg_skill.skill.SkillPage;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.common.NeoForge;

import java.util.List;

public class FRPGSkillEventHooks
{
    public List<Skill.SkillInstance> fillSkillTree(ResourceKey<SkillPage> page)
    {
        FillSkillTreeEvent event = new FillSkillTreeEvent(page);
        event = NeoForge.EVENT_BUS.post(event);
        return event.getSkills();
    }
}