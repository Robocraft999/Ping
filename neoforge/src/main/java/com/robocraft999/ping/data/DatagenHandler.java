package com.robocraft999.ping.data;

import com.robocraft999.ping.Constants;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = Constants.MOD_ID)
public class DatagenHandler {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event){
        var dataGenerator = event.getGenerator();
        var packOutput = dataGenerator.getPackOutput();

        dataGenerator.addProvider(event.includeClient(), new LangProvider(packOutput));
    }
}
