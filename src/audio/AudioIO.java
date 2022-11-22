package audio;
import javax.sound.sampled.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
    /** A collection of static utilities related to the audio system. */
 public class AudioIO {

        public AudioIO() throws LineUnavailableException {
        }

        /** Displays every audio mixer available on the current system. */
         public static void printAudioMixers() {
             System.out.println("Mixers:");
             Arrays.stream(AudioSystem.getMixerInfo())
             .forEach(e -> System.out.println("- name=\"" + e.getName()
                     + "\" description=\"" + e.getDescription() + " by " + e.getVendor() + "\""));
             }

        public static List<Mixer.Info> getAudioMixers() {
            List<Mixer.Info> infosList = new ArrayList<>(Arrays.asList(AudioSystem.getMixerInfo()));
            infosList.removeIf(info -> Pattern.matches("Port .*", info.getName()));
            infosList = infosList.stream().distinct().collect(Collectors.toList());
            return infosList;
        }

         /** @return a Mixer.Info whose name matches the given string.
 Example of use: getMixerInfo("Macbook default output") */
         public static Mixer.Info getMixerInfo(String mixerName) {
             // see how the use of streams is much more compact than for() loops!
             return Arrays.stream(AudioSystem.getMixerInfo())
             .filter(e -> e.getName().equalsIgnoreCase(mixerName)).findFirst().get();
             }

         /** Return a line that's appropriate for recording sound from a microphone.
  * Example of use:
  * TargetDataLine line = obtainInputLine("USB Audio Device", 8000);
  * @param mixerName a string that matches one of the available mixers.
  * @see AudioSystem.getMixerInfo() which provides a list of all mixers on your system.
  */
         public static TargetDataLine obtainAudioInput(String mixerName, int sampleRate) throws LineUnavailableException {
                 Mixer.Info mixerInfo = getMixerInfo(mixerName);
             AudioFormat format = new AudioFormat(sampleRate, 16, 1, true, false);
                 return AudioSystem.getTargetDataLine(format, mixerInfo);
         }

         /** Return a line that's appropriate for playing sound to a loudspeaker. */
         public static SourceDataLine obtainAudioOutput(String mixerName, int sampleRate) throws LineUnavailableException {
             Mixer.Info mixerInfo = getMixerInfo(mixerName);
             AudioFormat format = new AudioFormat(sampleRate, 16, 1, true, true);
             return AudioSystem.getSourceDataLine(format, mixerInfo);
         }

         public static void main (String[] args) throws LineUnavailableException {
             List<Mixer.Info> infosList = AudioIO.getAudioMixers();
             infosList.forEach(info -> System.out.println('"' + info.getName() + '"'));
             TargetDataLine input = obtainAudioInput("Microphone Array (Realtek(R) Au", 8000);
             SourceDataLine output = obtainAudioOutput("Speaker (Realtek(R) Audio)", 8000);
             }
 }