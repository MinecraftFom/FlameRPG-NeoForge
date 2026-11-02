package com.fomdev.flamerpg.modules.frpg_invasion.event;

import com.fomdev.flamerpg.modules.frpg_invasion.invasion.Invasion;
import com.fomdev.flamerpg.modules.frpg_invasion.invasion.InvasionInstance;
import com.fomdev.flamerpg.modules.frpg_invasion.invasion.InvasionType;
import net.neoforged.bus.api.Event;

public abstract class InvasionEvent extends Event
{
    private final InvasionInstance instance;
    private final Invasion invasion;
    private final InvasionType type;

    public InvasionEvent(Invasion invasion, InvasionType type, InvasionInstance instance)
    {
        this.instance = instance;
        this.invasion = invasion;
        this.type = type;
    }

    public InvasionInstance getInstance()
    {
        return this.instance;
    }

    public Invasion getInvasion()
    {
        return this.invasion;
    }

    public InvasionType getType()
    {
        return this.type;
    }

    public static class InvasionEnd extends InvasionEvent
    {
        public InvasionEnd(Invasion invasion, InvasionType type, InvasionInstance instance)
        {
            super(invasion, type, instance);
        }
    }

    public static class InvasionStart extends InvasionEvent
    {
        public InvasionStart(Invasion invasion, InvasionType type, InvasionInstance instance)
        {
            super(invasion, type, instance);
        }
    }

    public static class InvasionWave extends InvasionEvent
    {
        private final int wave;

        public InvasionWave(Invasion invasion, InvasionType type, InvasionInstance instance, int wave)
        {
            super(invasion, type, instance);

            this.wave = wave;
        }

        public int getWave()
        {
            return this.wave;
        }
    }
}