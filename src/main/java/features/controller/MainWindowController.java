package features.controller;

import application.Main;
import com.darkprograms.speech.recognizer.GSpeechResponseListener;
import com.darkprograms.speech.recognizer.GoogleResponse;
import features.advance.DictionaryV2;
import features.advance.Word;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import services.GoogleTranslate;
import services.Translator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

public class MainWindowController implements GSpeechResponseListener {

    private ObservableList<Word> data =
            FXCollections.observableArrayList(DictionaryV2.getDictionary());
    FilteredList<Word> flWordTarget = new FilteredList<>(data, p -> true);

    @FXML
    private ListView<Word> listView;
    @FXML
    private TextArea itemDetailsTextArea;
    @FXML
    private TextField textSearch;
    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private ContextMenu listContextMenu;
    @FXML
    private Button buttonSwitch;

    private final int widthToggleBut = 22;
    private final int heightToggleBut = 22;

    Image recordImage;

    {
        try {
            recordImage =
                    new Image(
                            new FileInputStream(
                                    "src/main/resources/application/graphic/ImageIcon/voice_recorder.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    Image stopRecord;

    {
        try {
            stopRecord =
                    new Image(
                            new FileInputStream("src/main/resources/application/graphic/ImageIcon/voice.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * initialize func: initialize buttons, show data.
     */
    public void initialize() {
        listContextMenu = new ContextMenu();
        MenuItem deleteWordMenu = new MenuItem("Delete");
        ImageView stopRecordView = new ImageView(stopRecord);
        stopRecordView.setFitWidth(widthToggleBut);
        stopRecordView.setFitHeight(heightToggleBut);
        deleteWordMenu.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Word word_to_delete = listView.getSelectionModel().getSelectedItem();
                        deleteWord(word_to_delete);
                    }
                });
        listContextMenu.getItems().addAll(deleteWordMenu);
        listView
                .getSelectionModel()
                .selectedItemProperty()
                .addListener(
                        new ChangeListener<Word>() {
                            @Override
                            public void changed(
                                    ObservableValue<? extends Word> observable, Word oldValue, Word newValue) {
                                if (newValue != null) {
                                    Word word = listView.getSelectionModel().getSelectedItem();
                                    if (Translator.oxfordSearch(word.getWord_target())
                                            .equals("Can't find this word!")) {
                                        String displayWord = word.getWord_target() + '\n' + word.getWord_explain();
                                        itemDetailsTextArea.setText(displayWord);
                                    } else {
                                        itemDetailsTextArea.setText(Translator.oxfordSearch(word.getWord_target()));
                                    }
                                    // itemDetailsTextArea.setText(Translator.oxfordSearch(word.getWord_target()));

                                }
                            }
                        });

        listView.setCellFactory(
                new Callback<ListView<Word>, ListCell<Word>>() {
                    @Override
                    public ListCell<Word> call(ListView<Word> param) {
                        ListCell<Word> cell =
                                new ListCell<Word>() {

                                    @Override
                                    protected void updateItem(Word item, boolean empty) {
                                        super.updateItem(item, empty);
                                        if (empty) {
                                            setText(null);
                                        } else {
                                            setText(item.getWord_target());
                                        }
                                    }
                                };

                        cell.emptyProperty()
                                .addListener(
                                        (obs, wasEmpty, isNowEmpty) -> {
                                            if (isNowEmpty) {
                                                cell.setContextMenu(null);
                                            } else {
                                                cell.setContextMenu(listContextMenu);
                                            }
                                        });

                        return cell;
                    }
                });
        textSearch.setPromptText("Search here");
        textSearch
                .textProperty()
                .addListener(
                        ((observable, oldValue, newValue) -> {
                            flWordTarget.setPredicate(
                                    p -> p.getWord_target().toLowerCase().startsWith(newValue.toLowerCase().trim()));
                        }));
        if (flWordTarget.size() > 0) {
            listView.setItems(flWordTarget);
        } else {
            listView.getItems().setAll();
        }
        listView.getSelectionModel().selectFirst();
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    /**
     * showNewWordDialog func: open new word window.
     */
    @FXML
    public void showNewWordDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();

        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Add new word");
        dialog.setHeaderText("Use this dialog to add word");
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("graphic/fxml/Dialog.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            DialogController controller = fxmlLoader.getController();

            Word newWord = controller.processResults();
            if (newWord != null) {

                data = DictionaryV2.getDictionary();
                if (flWordTarget.size() > 0) {
                    flWordTarget = data.filtered(p -> true);
                    listView.setItems(flWordTarget);
                } else {
                    listView.getItems().setAll();
                }

                listView.getSelectionModel().select(newWord);
                listView.scrollTo(newWord);
            } else {
                showNewWordDialog();
            }
        }
    }

    @FXML
    public void handleClickListView() {
        Word item = listView.getSelectionModel().getSelectedItem();
        itemDetailsTextArea.setText(item.getWord_explain());
    }

    /**
     * deleteWord func: delete word.
     *
     * @param word_to_delete
     */
    public void deleteWord(Word word_to_delete) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete this word");
        alert.setHeaderText("Delete this word: " + word_to_delete.getWord_target());
        alert.setContentText("Are you sure?  Press OK to confirm, or cancel to Back out.");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && (result.get() == ButtonType.OK)) {
            DictionaryV2.getDictionary().remove(word_to_delete);
            data = DictionaryV2.getDictionary();
            if (flWordTarget.size() > 0) {
                flWordTarget = data.filtered(p -> true);
                listView.setItems(flWordTarget);
            } else {
                listView.getItems().setAll();
            }
            listView.getSelectionModel().selectFirst();
            listView.scrollTo(0);
            textSearch.setText("");
        }
    }

    /**
     * handleKeyPressed func: handle pressed keys.
     *
     * @param keyEvent
     */
    @FXML
    public void handleKeyPressed(KeyEvent keyEvent) {
        Word selectedWord = listView.getSelectionModel().getSelectedItem();
        if (selectedWord != null) {
            if (keyEvent.getCode().equals(KeyCode.DELETE)) {
                deleteWord(selectedWord);
            }
        }
    }

    public Button getButtonSwitch() {
        return buttonSwitch;
    }

    /**
     * speechEnglish func: speech target word.
     *
     * @throws IOException
     */
    @FXML
    public void speechEnglish() throws IOException {
        String selectedWord = listView.getSelectionModel().getSelectedItem().getWord_target();
        GoogleTranslate.speak(selectedWord);
    }

    /**
     * enterSearch func: start searching when pressed enter key.
     *
     * @param keyEvent
     */
    @FXML
    public void enterSearch(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            itemDetailsTextArea.setText(Translator.oxfordSearch(textSearch.getText()));
        }
    }

    /**
     * removeWord func: remove word.
     *
     * @param mouseEvent
     */
    @FXML
    public void removeWord(MouseEvent mouseEvent) {
        deleteWord(listView.getSelectionModel().getSelectedItem());
    }

    /**
     * showEditWordDialog func: show edit word window.
     */
    @FXML
    public void showEditWordDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();

        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Edit Word");
        dialog.setHeaderText("Use this dialog to edit a new Word");
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("graphic/fxml/EditWord.fxml"));
        //    fxmlLoader.setLocation(MainWindowController.class.getResource("EditWord.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {

            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            EditWordController controller = fxmlLoader.getController();

            Word selectedWord = listView.getSelectionModel().getSelectedItem();
            Word editedWord = controller.editProcess(selectedWord);

            if (editedWord != null) {
                data = DictionaryV2.getDictionary();
                if (flWordTarget.size() > 0) {
                    flWordTarget = data.filtered(p -> true);
                    listView.setItems(flWordTarget);
                } else {
                    listView.getItems().setAll();
                }
                listView.getSelectionModel().select(editedWord);
                listView.scrollTo(editedWord);
                textSearch.setText("");

            } else {
                showEditWordDialog();
            }
        }
    }
    // .

    @Override
    public void onResponse(GoogleResponse googleResponse) {
    }
}