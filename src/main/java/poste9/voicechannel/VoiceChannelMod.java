package poste9.voicechannel;

import java.io.ByteArrayOutputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

import poste9.voicechannel.dataline.InputThread;
import poste9.voicechannel.dataline.OutputThread;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStoppedEvent;

@Mod(modid = "vchan", name="Voice Channel Mod", version = "0.1", useMetadata = true)
public class VoiceChannelMod 
{
	AudioFormat format = new AudioFormat(8000.0f, 16, 1, true, true);
	InputThread input_thread;
	OutputThread output_thread;
	Thread read_thread;
	Boolean is_voice_comm_available;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e)
	{
		is_voice_comm_available = false;
		
		DataLine.Info input_info = new DataLine.Info(TargetDataLine.class, format);
		DataLine.Info output_info = new DataLine.Info(SourceDataLine.class, format);
		
		if ( !AudioSystem.isLineSupported(input_info) )
		{
			System.err.println("Voice communication not supported on this device!");
		}
		else
		{
			is_voice_comm_available = true;
			
			input_thread = new InputThread( input_info, format );
			output_thread = new OutputThread( output_info, format );
		}
	}
	
	@EventHandler
	public void init(FMLInitializationEvent e)
	{
		if ( is_voice_comm_available )
		{
			output_thread.start();
			
			input_thread.set_output_thread( output_thread );
			
			input_thread.start();
		}
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent e)
	{
	}
	
	@EventHandler
	public void serverStopped(FMLServerStoppedEvent e)
	{
		System.out.println("server stopped!");
	}
	
	@EventHandler
	public void serverStarted(FMLServerStartedEvent e)
	{
		System.out.println("server started!");
		System.out.println("Start server thread here!");
	}
}
