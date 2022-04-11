package com.example.javafx;

import javafx.animation.KeyValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class HelloController {
    @FXML
    TextArea textArea;

    @FXML
    Button button;

    @FXML
    Button button2;

    @FXML
    TextField textField;

    public void sendMessage(){
        textArea.appendText("Question: " + textField.getText() + "\n");
        textField.clear();
        textField.requestFocus();
    }
    @FXML
    protected void KeyListener(KeyEvent event){
        if (event.getCode().getCode() == 10){
            messageEnter();
        }
    }

    public void sendMessage2(){
        textArea.appendText("Answer: " + textField.getText() + "\n");
        textField.clear();
        textField.requestFocus();
    }
    public void messageEnter(){
        textArea.appendText("Enter: " + textField.getText() + "\n");
        textField.clear();
        textField.requestFocus();

    }
}