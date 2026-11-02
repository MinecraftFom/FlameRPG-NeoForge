package com.fomdev.flamerpg.modules.frpg_invasion.invasion;

import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.apache.commons.lang3.tuple.Triple;

public abstract class InvasionManager
{
    private static InvasionManager INSTANCE;
    private static boolean         STATUS;

    public static InvasionManager getInstance()
    {
        if (!isInvasionAvailable())
            throw new IllegalStateException("Invasion currently not available");

        return INSTANCE;
    }

    public static boolean isInvasionAvailable()
    {
        return STATUS;
    }

    public static void initServer(Level level)
    {
        INSTANCE = level.isClientSide()? new InvasionManagerClient(): new InvasionManagerServer();
        STATUS = true;
    }

    public static void stopServer()
    {
        INSTANCE = null;
        STATUS = false;
    }

    public abstract Triple<Invasion, InvasionType, InvasionInstance> getCurrentInvasion();

    public abstract void popInvasion();
    public abstract void pushInvasion(Invasion invasion, InvasionType type, InvasionInstance instance);

    public boolean isOnInvasion()
    {
        return this.getCurrentInvasion() != null;
    }

    @OnlyIn(Dist.CLIENT)
    public static class InvasionManagerClient extends InvasionManager
    {

        @Override
        public Triple<Invasion, InvasionType, InvasionInstance> getCurrentInvasion()
        {
            return null;
        }

        @Override
        public void popInvasion()
        {

        }

        @Override
        public void pushInvasion(Invasion invasion, InvasionType type, InvasionInstance instance)
        {

        }
    }

    @OnlyIn(Dist.DEDICATED_SERVER)
    public static class InvasionManagerServer extends InvasionManager
    {

        @Override
        public Triple<Invasion, InvasionType, InvasionInstance> getCurrentInvasion()
        {
            return null;
        }

        @Override
        public void popInvasion()
        {

        }

        @Override
        public void pushInvasion(Invasion invasion, InvasionType type, InvasionInstance instance)
        {

        }
    }
}