package com.robocraft999.ping;

import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constants {

	public static final String MOD_ID = "ping";
	public static final String MOD_NAME = "Ping";
	public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

	public static ResourceLocation modLoc(String path){
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
	}
}
