package audio;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

/** The main audio processing class, implemented as a Runnable so
 * as to be run in a separated execution Thread. */
 public class AudioProcessor implements Runnable {
 private boolean threadIsRunning = false;
 private int bufferSize = 128;
 private int bytesPerSample;
 private int sampleRate;

 private AudioSignal inputSignal, outputSignal;
 private TargetDataLine audioInput;
 private SourceDataLine audioOutput;
 private boolean isThreadRunning; // makes it possible to "terminate" thread

 /** Creates an AudioProcessor that takes input from the given TargetDataLine, and plays back
  * to the given SourceDataLine.
  * @param frameSize the size of the audio buffer. The shorter, the lower the latency. */
 public AudioProcessor(TargetDataLine audioInput, SourceDataLine audioOutput, int frameSize) {
 }

 /** Audio processing thread code. Basically an infinite loop that continuously fills the sample
  * buffer with audio data fed by a TargetDataLine and then applies some audio effect, if any,
  * and finally copies data back to a SourceDataLine.*/
 @Override
 public void run() {
  isThreadRunning = true;
  while (isThreadRunning) {
   inputSignal.recordFrom(audioInput);
   inputSignal = outputSignal;
   outputSignal.playTo(audioOutput);
  }
 }

 /** Tells the thread loop to break as soon as possible. This is an asynchronous process. */
 public void terminateAudioThread() {threadIsRunning = false; }

 //todo here: all getters and setters

 public boolean isThreadIsRunning() {
  return threadIsRunning;
 }

 public int getBufferSize() {
  return bufferSize;
 }

 public int getBytesPerSample() {
  return bytesPerSample;
 }

 public int getSampleRate() {
  return sampleRate;
 }

 public AudioSignal getInputSignal() {
  return inputSignal;
 }

 public AudioSignal getOutputSignal() {
  return outputSignal;
 }

 public TargetDataLine getAudioInput() {
  return audioInput;
 }

 public SourceDataLine getAudioOutput() {
  return audioOutput;
 }

 public boolean isThreadRunning() {
  return isThreadRunning;
 }

 public void setThreadIsRunning(boolean threadIsRunning) {
  this.threadIsRunning = threadIsRunning;
 }

 public void setBufferSize(int bufferSize) {
  this.bufferSize = bufferSize;
 }

 public void setBytesPerSample(int bytesPerSample) {
  this.bytesPerSample = bytesPerSample;
 }

 public void setSampleRate(int sampleRate) {
  this.sampleRate = sampleRate;
 }

 public void setInputSignal(AudioSignal inputSignal) {
  this.inputSignal = inputSignal;
 }

 public void setOutputSignal(AudioSignal outputSignal) {
  this.outputSignal = outputSignal;
 }

 public void setAudioInput(TargetDataLine audioInput) {
  this.audioInput = audioInput;
 }

 public void setAudioOutput(SourceDataLine audioOutput) {
  this.audioOutput = audioOutput;
 }

 public void setThreadRunning(boolean threadRunning) {
  isThreadRunning = threadRunning;
 }



 /* an example of a possible test code */
 public static void main(String[] args) throws LineUnavailableException {
  TargetDataLine inLine = AudioIO.obtainAudioInput("Pilote de capture audio principal", 16000);
  SourceDataLine outLine = AudioIO.obtainAudioOutput("Périphérique audio principal", 16000);
  AudioProcessor as = new AudioProcessor(inLine, outLine, 1024);
  inLine.open(); inLine.start(); outLine.open(); outLine.start();
  new Thread(as).start();
  System.out.println("A new thread has been created!");
 }
 }