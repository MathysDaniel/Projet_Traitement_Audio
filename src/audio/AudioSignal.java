package audio;

import javax.sound.sampled.*;
import java.util.ArrayList;
import java.util.List;

import static audio.AudioIO.obtainAudioInput;
import static audio.AudioIO.obtainAudioOutput;

/** A container for an audio signal backed by a double buffer so as to allow floating point calculation
  * for signal processing and avoid saturation effects. Samples are 16 bit wide in this implementation. */
 public class AudioSignal {
         private double[] sampleBuffer; // floating point representation of audio samples
        public double dBlevel; // current signal level

        private int frameSize;


         /** Construct an AudioSignal that may contain up to "frameSize" samples.
  * @param frameSize the number of samples in one audio frame */
         public AudioSignal(int frameSize) {
             double signal[] = new double[frameSize];
             this.sampleBuffer = new double[frameSize];
         }

         /** Sets the content of this signal from another signal.
  * @param other other.length must not be lower than the length of this signal. */
         public void setFrom(AudioSignal other) {
             for(int j=0; j<other.sampleBuffer.length; j++){
                 this.sampleBuffer[j] = other.sampleBuffer[j];
             }
         }

         /** Fills the buffer content from the given input. Byte's are converted on the fly to double's.
  * @return false if at end of stream */
         public boolean recordFrom(TargetDataLine audioInput) {
             byte[] byteBuffer = new byte[sampleBuffer.length * 2]; // 16 bit samples
             if (audioInput.read(byteBuffer, 0, byteBuffer.length) == -1) return false;
             int i;
             for (i = 0; i < sampleBuffer.length-1; i++) {
                 audioInput.read(byteBuffer, 0, byteBuffer.length);

                 sampleBuffer[i] = ((byteBuffer[2 * i] << 8) + byteBuffer[2 * i + 1]) / 32768.0; // big endian

                 // ... TODO : dBlevel = update signal level in dB here ...
                 dBlevel = 20 * Math.log10(Math.abs(sampleBuffer[i]));
             }
             return true;
         }

         /** Plays the buffer content to the given output.
  * @return false if at end of stream */

         public boolean playTo(SourceDataLine audioOutput) {
             byte[] byteBuffer = new byte[sampleBuffer.length*2]; // 16 bit samples
             if (audioOutput.write(byteBuffer, 0, byteBuffer.length)==-1) return false;
            audioOutput.write(byteBuffer,0,sampleBuffer.length);


             return false;
         }
    public static void main (String[] args) throws LineUnavailableException {
        AudioSignal Audio1 = new AudioSignal(100);
        AudioFormat format1 = new AudioFormat(8000,16,1,true,true);
        TargetDataLine line1 = AudioSystem.getTargetDataLine(format1);
       /* AudioSignal Audio2 = new AudioSignal(100);
        AudioFormat format2 = new AudioFormat(8000,16,1,true,true);
        SourceDataLine line2 = AudioSystem.getSourceDataLine(format2); *\

        */
        line1.open(format1);
        line1.start();
        List<Mixer.Info> infosList = AudioIO.getAudioMixers();
        infosList.forEach(info -> System.out.println('"' + info.getName() + '"'));
        TargetDataLine input = obtainAudioInput("Microphone Array (Realtek(R) Au", 8000);
        SourceDataLine output = obtainAudioOutput("Speaker (Realtek(R) Audio)", 8000);

        Audio1.recordFrom(line1);
        // Audio1.playTo((SourceDataLine) line2);

        System.out.println("Starting ...");
        System.out.println("Starting Recording ...");
        System.out.println(Audio1.dBlevel);



    }
    public double getSampleBuffer(int i) {
        return sampleBuffer[i];
    }

    public double getdBlevel() {
        return dBlevel;
    }

    public int getFrameSize() {
        return frameSize;
    }

    public void setSampleBuffer(int i, double sampleBuffer[]) {
        this.sampleBuffer[i] = sampleBuffer[i];
    }


         // Can be implemented much later: Complex[] computeFFT()
         }
