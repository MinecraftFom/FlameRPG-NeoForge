package com.fomdev.flamerpg.event;

import com.fomdev.flamerpg.modules.frpg_skill.event.FRPGSkillEventHooks;

public class EventHooks
{
    public static final FRPGSkillEventHooks EVT_HOOK_SKILL;

    static
    {
        EVT_HOOK_SKILL = new FRPGSkillEventHooks();
    }
}