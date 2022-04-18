package com.example.javafx;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    TextArea textArea;

    @FXML
    Button button;

    Socket socket;
    DataInputStream in;
    DataOutputStream out;

    String IP_ADDRESS = "localhost";
    int PORT = 8189;

    @FXML
    TextField textField;

    public void sendMessage(){
       // textArea.appendText(textField.getText() + "\n");
        try {
            out.writeUTF(textField.getText());
            textField.clear();
            textField.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    protected void KeyListener(KeyEvent event){
        if (event.getCode().getCode() == 10){
            messageEnter();
        }
    }

    public void messageEnter(){
        textArea.appendText("Enter: " + textField.getText() + "\n");
        textField.clear();
        textField.requestFocus();


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            socket = new Socket(IP_ADDRESS, PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            new Thread(new Runnable() {
                @Override
                public void run() {

                        try {
                            while (true) {
                                String str = in.readUTF();
                                if(str.equals("/end")){
                                    break;
                                }
                                textArea.appendText(str + "\n");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                socket.close();
                                in.close();
                                out.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}