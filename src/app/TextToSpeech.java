package app;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class TextToSpeech {
    private static final String VOICENAME = "kevin16";

    public static void mySpeak(String inputText) {
        Voice voice;
        VoiceManager vm = VoiceManager.getInstance();
        voice = vm.getVoice(VOICENAME);
        voice.allocate();
        try {
            voice.speak(inputText);
        } catch (Exception e) {
        }
    }
}
