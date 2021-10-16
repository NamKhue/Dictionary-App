package services;

import com.darkprograms.speech.microphone.Microphone;
import com.darkprograms.speech.recognizer.*;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import net.sourceforge.javaflacencoder.FLACFileWriter;

import java.util.Objects;

public class SpeechToText implements GSpeechResponseListener {
  
  
  public static final Microphone mic = new Microphone(FLACFileWriter.FLAC);
  public static GSpeechDuplex duplex = new GSpeechDuplex("AIzaSyBOti4mM-6x9WDnZIjIeyEU21OpBXqWBgw");
  
  
  public static void speechSearch(ToggleButton speechSearch, TextInputControl textSearch, Image recordImage, Image stopRecord, int widthToggleBut
          , int heightToggleBut) {
    duplex.setLanguage("en");
    ImageView recordImageView = new ImageView(recordImage);
    recordImageView.setFitHeight(heightToggleBut);
    recordImageView.setFitWidth(widthToggleBut);
    ImageView stopRecordView = new ImageView(stopRecord);
    stopRecordView.setFitWidth(widthToggleBut);
    stopRecordView.setFitHeight(heightToggleBut);
    
    if (speechSearch.isSelected()) {
      speechSearch.setGraphic(recordImageView);
      new Thread(() -> {
        try {
          duplex.recognize(mic.getTargetDataLine(), mic.getAudioFormat());
        } catch (Exception e) {
          e.printStackTrace();
        }
      }).start();
      
    } else {
      speechSearch.setGraphic(stopRecordView);
      mic.close();
      duplex.stopSpeechRecognition();
    }
    
    duplex.addResponseListener(new GSpeechResponseListener() {
      public void onResponse(GoogleResponse gr) {
        String output = "";
        output = gr.getResponse();
        textSearch.setText("");
        textSearch.appendText(output.toLowerCase());
      }
    });
  }
  
  @Override
  public void onResponse(GoogleResponse googleResponse) {
  
  }
}