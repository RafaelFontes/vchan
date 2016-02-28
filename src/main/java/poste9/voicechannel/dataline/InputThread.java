package poste9.voicechannel.dataline;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLLog;

public class InputThread extends Thread {
	
	public InputThread( DataLine.Info info, AudioFormat format )
	{
		this.format = format;
		this.info = info;
		
		setDaemon(true);
		setName("vchan.OutputThread");
		
		setPriority(MAX_PRIORITY);
	}
	
	public void run()
	{
		can_run = true;
		
		try {
			
			input = (TargetDataLine) AudioSystem.getLine(info);
			input.open(format);
			input.start();
			
			int bytes_read;
			
			byte[] data = new byte[input.getBufferSize() / 5];
			
			System.out.println("Starting input thread..." );
			
			while ( can_run )
			{
				if ( input.available() > 0 )
				{
					bytes_read = input.read( data, 0, data.length );
					
					if ( bytes_read == -1 )
					{
						can_run = false;
						break;
					}
					else if ( bytes_read > 0 )
					{
						try {
							output_thread.add_sample( data, bytes_read );
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
			
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		
		try {
			output_thread.stop_nicely();
			output_thread.join();

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Input thread stopped!" );
	}
	
	public void set_output_thread( OutputThread thread )
	{
		output_thread = thread;
	}
	
	public void stop_nicely() 
	{
		can_run = false;
	}
	
	protected TargetDataLine input;
	protected AudioFormat format;
	protected DataLine.Info info;
	protected Boolean can_run;
	
	private OutputThread output_thread;
}
