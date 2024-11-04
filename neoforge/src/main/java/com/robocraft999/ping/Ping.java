package com.robocraft999.ping;


import com.robocraft999.ping.client.ClientEvents;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

@Mod(Constants.MOD_ID)
public class Ping {

    public Ping(IEventBus eventBus) {
        // This method is invoked by the NeoForge mod loader when it is ready
        // to load your mod. You can access NeoForge and Common code in this
        // project.

        // Use NeoForge to bootstrap the Common mod.
        Constants.LOG.info("Hello NeoForge world!");
        CommonClass.init();
        eventBus.addListener(this::registerKeybinds);
    }

    private void registerKeybinds(RegisterKeyMappingsEvent event){
        Constants.LOG.debug("registering keybinds");
        event.register(ClientEvents.PING_KEY.get());
    }
}
