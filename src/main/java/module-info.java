module DApp {
  requires javafx.controls;
  requires javafx.fxml;
  requires json.simple;
  requires org.json;
  requires freetts;
  requires java.logging;
  requires java.desktop;
  requires java.flac.encoder;
  requires java.google.speech.api;
  requires jlayer;
  
  opens features.controller to javafx.fxml;
  exports features.controller;
  
  opens application to javafx.fxml;
  exports application;
}