package app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class TranslateWordAndSentence {
    @FXML
    Button comeback;
    @FXML
    private TextArea wordTargetArea;
    @FXML
    private TextArea wordExplainArea;
    @FXML
    private Button translateButton;

    @FXML
    public void translate(ActionEvent actionEvent) throws Exception {
        String wordTarget = wordTargetArea.getText().toLowerCase().trim();
        wordExplainArea.setText(GoogleTranslate.googleTranslate(wordTarget));
    }


    public Button getComeback() {
        return comeback;
    }


}
