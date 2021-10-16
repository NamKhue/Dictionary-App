package services;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class TextToSpeech
{
  private static final String VOICENAME = "kevin16";
  public static void startSpeak(String input)
  {
    VoiceManager vm = VoiceManager.getInstance();
    Voice voice = vm.getVoice(VOICENAME);
    voice.allocate();
    try {
      voice.speak(input);
    } catch(Exception e) {
      e.printStackTrace();
    }
  }
}