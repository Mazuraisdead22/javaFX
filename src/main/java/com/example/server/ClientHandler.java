package com.example.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {

    Socket socket;
    DataOutputStream out;
    DataInputStream in;

    public ClientHandler( Socket socket, MainServer mainServer) {

        this.socket = socket;
        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream((socket.getOutputStream()));

            new  Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                    while(true){
                        String str;
                        str = in.readUTF();
                        if(str.equals("/end")){
                            out.writeUTF("/end");
                            break;
                            }
                        mainServer.sendToAll(str);


                        }
                        }catch (IOException e) {
                        e.printStackTrace();
                    }finally {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            out.writeUTF("/end");
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            socket.close();
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
    public void sendMsg(String msg){
        System.out.println("Client send message: " + msg);
        try {
            out.writeUTF("ECHO:" + msg + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

