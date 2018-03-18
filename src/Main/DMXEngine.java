/*
 * Copyright (c) 2014 Peter Dikant
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package Main;

import ola.OlaClient;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * This is the main DMX engine. It will loop with the configured frame rate and render DMX 
 * frames. These frames are then sent to OLA for hardware output.
 *
 */
public class DMXEngine implements Runnable {
	//
//	private final Show show;
	private short[] currentFrame = new short[512];

	private boolean stop;
	private OlaClient ola;

	/**
	 * Initialize the engine with a loaded show file. Setup connection to OLA and start
	 * a new thread to monitor keyboard input.
	 * <p>
	 * //	 * @param show represents the show file
	 *
	 * @throws Exception thrown if the intialization of OLA fails.
	 */
	public DMXEngine() throws Exception {
		for (int i = 0; i < 512; i++) {
			currentFrame[i] = 0;
		}
		stop = false;

		try {
			ola = new OlaClient();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	/**
	 * The main execution loop for the DMX engine.
	 * <p>
	 * <p>It will check for new key presses by the user, calculate the DMX
	 * values for a frame and then wait for the remainder of the frame time.</p>
	 */
	public void run() {
		long startTime;
		long timeDelta;

		while (!stop) {
			startTime = System.nanoTime();
//			checkUserInput();
//			computeCurrentFrame();
//			for (int i =0; i < 512 ; i++) {
//				System.out.println("currentFrame is " + currentFrame[i]);
//			}
			ola.streamDmx(1, currentFrame);
			// time is converted to ms
			timeDelta = Math.round((System.nanoTime() - startTime) / 1000000);
			if (timeDelta < 40) {
				try {
					Thread.sleep(40 - timeDelta);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("Slow frame encountered: " + timeDelta + " ms");
			}
		}
	}

//	private void checkUserInput() {
//
//	}

	public void controlChannel(int channel) {
//		Fade channel [channel] from 255 to 0 over 4s
		System.out.println("controlling channel " + channel);
		Timer timer = new Timer();
		FadeLights task = new FadeLights(channel);
		//Set initial value to FL
		currentFrame[channel - 1] = 255;
		timer.schedule(task, 0, 5);

	}


	//Class to fade down level over time
	private class FadeLights extends TimerTask implements Runnable {
		private int channel;
		private short level = 255;

		FadeLights(int channel) {
			this.channel = channel;
		}

		@Override
		public void run() {
			currentFrame[channel - 1] = level;
			level--;
			if (level == 0) {
				this.cancel();
			}
		}
	}

}