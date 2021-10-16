package application;

import features.advance.DictionaryV2;

import features.controller.MainWindowController;
import features.controller.TranslateWordAndSentence;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
  Scene scene2, scene1;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    FXMLLoader fxmlLoader1 = new FXMLLoader(Main.class.getResource("graphic/fxml/MainWindow.fxml"));
    FXMLLoader fxmlLoader2 = new FXMLLoader(Main.class.getResource("graphic/fxml/TranslateWordAndSentence.fxml"));

    Parent root = fxmlLoader1.load();
    Parent subroot = fxmlLoader2.load();

    MainWindowController controllerMainWindow = fxmlLoader1.getController();
    Button buttonSwitchToTranslate = controllerMainWindow.getButtonSwitch();
    TranslateWordAndSentence controllerTranslate = fxmlLoader2.getController();
    Button buttonSwitchToMainWindow = controllerTranslate.getComeback();
    scene2 = new Scene(subroot, 1280, 720);
    scene1 = new Scene(root, 1280, 720);
    primaryStage
        .getIcons()
        .add(
            new Image(
                "https://img2.storyblok.com/400x0/filters:quality(80)/f/90801/960x1152/c77ba505ac/oxford-university-logo.png"));
    primaryStage.setResizable(false);
    buttonSwitchToTranslate.setOnAction(
        event -> primaryStage.setScene(scene2));
    buttonSwitchToMainWindow.setOnAction(
        event -> primaryStage.setScene(scene1));
    primaryStage.setScene(scene1);
    primaryStage.setTitle("OPPFord Dictionary");
    primaryStage.show();
  }

  @Override
  public void init() {
    try {
      DictionaryV2.getInstance().LoadDictionary();
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

  @Override
  public void stop() {
    try {
      DictionaryV2.getInstance().storeTodoItems();

    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }
}
