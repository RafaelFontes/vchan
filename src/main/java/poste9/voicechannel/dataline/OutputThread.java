package poste9.voicechannel.dataline;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import scala.actors.threadpool.Arrays;

public class OutputThread extends Thread {

	static final int MAX_PACKETS_IN_BUFFER = 100;
	
	public OutputThread( DataLine.Info info, AudioFormat format )
	{
		queue = new ArrayBlockingQueue<byte[]>(MAX_PACKETS_IN_BUFFER);
		
		this.info = info;
		this.format = format;
		
		setDaemon(true);
		setName("vchan.InputThread");
		
		setPriority(MAX_PRIORITY);
		
		junk_packet_count = 0;
	}
	
	public void run()
	{
		can_run = true;
		
		try {
			
			output = (SourceDataLine) AudioSystem.getLine(info);
			output.open();
			output.start();
		
			System.out.println("Starting output thread...");
			
			while ( can_run )
			{
				try {
					
					process_packet( queue.take() );
					
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}			
			}
			
			System.out.println("Output thread stopped!");
		
		} catch (LineUnavailableException e1) {
			e1.printStackTrace();
		}
	}
	
	public void add_sample( byte[] data, int bytes_read ) throws InterruptedException
	{
		if ( junk_packet_count >= 10 )
		{
			queue.put(Arrays.copyOfRange( data, 0, bytes_read ));
		}
		else
		{
			++junk_packet_count;
		}
	}
	
	private void process_packet( byte[] packet )
	{
		if ( packet != null )
		{
			output.write(packet, 0, packet.length);
		}
	}	
	
	
	public void stop_nicely() throws InterruptedException
	{
		can_run = false;
		
		byte[] mock = {0};
		
		add_sample( mock, 1 );
	}
	
	protected SourceDataLine output;
	
	protected AudioFormat format;
	protected DataLine.Info info;
	protected volatile Boolean can_run;

	private BlockingQueue<byte[]> queue;
	
	
	int junk_packet_count;
}
