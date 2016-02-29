package poste9.voicechannel;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import poste9.voicechannel.net.VoicePacketDispatcher;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStoppedEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = VoiceChannelMod.MOD_ID, name = VoiceChannelMod.MOD_NAME, version = VoiceChannelMod.MOD_VERSION, useMetadata = true)
public class VoiceChannelMod 
{
	public final static String MOD_ID = "VCM";
	public final static String MOD_VERSION = "0.1";
	public final static String MOD_NAME = "Voice Channel Mod";

	public static final Logger logger = LogManager.getLogger(MOD_ID);
	
	@Mod.Instance(MOD_ID)
	public static VoiceChannelMod instance;
	
	@SidedProxy(clientSide = "poste9.voicechannel.ModProxyClient", serverSide = "poste9.voicechannel.ModProxy")
	public static ModProxy proxy;


	@EventHandler
	public void preInit(FMLPreInitializationEvent e)
	{
		logger.info("Beginning pre-initialization");
		VoicePacketDispatcher.registerPackets();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent e)
	{
		logger.info("Beginning initialization");
		
		// Register renders here
		
		ModEventHandler events = new ModEventHandler();
		MinecraftForge.EVENT_BUS.register(events);
		
		/*if ( is_voice_comm_available )
		{
			input_thread.start();
			sound_output.start();
		}*/
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent e)
	{
		logger.info("Post initialization");

	}
	
	@EventHandler
	public void serverStopped(FMLServerStoppedEvent e)
	{
		logger.info("Server stopped");
	}
	
	@EventHandler
	public void serverStarted(FMLServerStartedEvent e)
	{
		logger.info("Server started");
	}
}
