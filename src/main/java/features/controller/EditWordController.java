package features.controller;

import features.advance.DictionaryV2;
import features.advance.Word;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.Optional;

public class EditWordController {
  @FXML
  public Label Confirm;
  @FXML
  public TextField editWord;
  
  public Word editProcess(Word inputWord) {
    Confirm.setText("Continue edit this " + inputWord.getWord_target());
    String editedWord = editWord.getText().trim().toLowerCase();
    Word returnWord = null;
    int index = DictionaryV2.getInstance().binaryLookUp(inputWord.getWord_target(), 0, DictionaryV2.getSize() - 1);
    if (editedWord.isEmpty()) {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Error");
      alert.setHeaderText("Edit word failed");
      alert.setContentText("Please enter the full word.");
      Optional<ButtonType> result_ = alert.showAndWait();
    } else {
      if (index != -1) {
        DictionaryV2.getDictionary().remove(inputWord);
        returnWord = new Word(editedWord, ".");
        DictionaryV2.push(returnWord);
      } else {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Edit word failed");
        alert.setContentText("This word existed");
        Optional<ButtonType> result_ = alert.showAndWait();
      }
    }
    return returnWord;
  }
}