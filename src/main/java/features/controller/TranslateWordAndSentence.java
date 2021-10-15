package features.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import services.GoogleTranslate;
import services.SpeechToText;
import services.TextToSpeech;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class TranslateWordAndSentence {
  @FXML
  public ToggleButton micButton;
  @FXML
  public Button sound;
  @FXML
  Button comeback;
  @FXML
  private TextArea wordTargetArea;
  @FXML
  private TextArea wordExplainArea;
  @FXML
  private Button translateButton;
  Image recordImage;
  
  {
    try {
      recordImage = new Image(new FileInputStream("src/main/resources/application/graphic/ImageIcon/voice_recorder.png"));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }
  
  Image stopRecord;
  
  {
    try {
      stopRecord = new Image(new FileInputStream("src/main/resources/application/graphic/ImageIcon/voice.png"));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }
  
  public void initialize() {
    ImageView stopRecordView = new ImageView(stopRecord);
    stopRecordView.setFitWidth(26);
    stopRecordView.setFitHeight(26);
    this.micButton.setGraphic(stopRecordView);
  }
  
  @FXML
  public void translate(ActionEvent actionEvent) throws Exception {
    String wordTarget = wordTargetArea.getText().toLowerCase().trim();
    wordExplainArea.setText(GoogleTranslate.translate(wordTarget));
  }
  
  
  public Button getComeback() {
    return comeback;
  }
  
  @FXML
  public void onMic(MouseEvent mouseEvent) {
    SpeechToText.speechSearch(micButton, wordTargetArea, recordImage, stopRecord, 26, 26);
  }
  
  @FXML
  public void speak(ActionEvent actionEvent) {
    String selectedWord = wordTargetArea.getText();
    try {
      GoogleTranslate.speak(selectedWord);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  @FXML
  public void speakEplain(ActionEvent actionEvent) {
    String selectedWord = wordExplainArea.getText();
    try {
      GoogleTranslate.speak(selectedWord);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}