package com.fomdev.flamerpg.init;

import com.fomdev.flamerpg.modules.frpg_skill.event.FillSkillTreeEvent;
import com.fomdev.flamerpg.modules.frpg_skill.gui.SkillTreeScreen;
import com.fomdev.flamerpg.modules.frpg_skill.skill.Skill;
import com.fomdev.flamerpg.modules.frpg_skill.skill.SkillPage;
import com.fomdev.flamerpg.registry.FRPGRegistries;
import com.fomdev.flamerpg.util.IconImageResource;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.client.event.ClientChatEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;

import java.util.List;
import java.util.Optional;

@Mod(FlameRPG.MODID)
public class FlameRPG {
    public static final String MODID = "flamerpg";
    public static final Logger LOGGER = LogUtils.getLogger();

    public FlameRPG(IEventBus bus, ModContainer container)
    {
        FRPGRegistries.register(bus);

        DeferredRegister<SkillPage> register = DeferredRegister.create(FRPGRegistries.Keys.REGISTRY_KEY_SKILL_PAGE, MODID);
        register.register("neko_nyan_girl", () -> new SkillPage(Component.literal("nyan"), Component.literal("as a neko!"), Optional.of(new IconImageResource(Items.END_ROD)), Optional.empty()));
        register.register(bus);

        NeoForge.EVENT_BUS.addListener((ClientChatEvent event) ->
        {
            Player player = Minecraft.getInstance().player;
            Minecraft.getInstance().setScreen(new SkillTreeScreen(player, Component.literal("NYAN!")));
        });

        NeoForge.EVENT_BUS.addListener((FillSkillTreeEvent event) -> {
            event.addSkill(ResourceLocation.fromNamespaceAndPath(MODID, "nyanawa"), new Skill.SkillInstance(
                    new Skill(new IconImageResource(Items.END_ROD)),
                    new Skill.SkillDependencies(10, List.of()),
                    new Skill.SkillDisplayProperties(16, 100, 16, Skill.SkillShapes.CIRCLE, Optional.empty(), Optional.empty()),
                    new Skill.SkillProperties(Component.literal("nyanarch"), Component.literal("neko girl great!")) {
                        @Override
                        public void onTrigger(Player player) {
                            player.sendSystemMessage(Component.literal("NYAN!"));
                        }
                    }
            ));
        });
    }
}
