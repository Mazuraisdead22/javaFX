package com.example.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;


public class MainServer {
    private Vector<ClientHandler> clientHandlers;

    public void start() {
        ServerSocket server;
        Socket socket;

        clientHandlers = new Vector<>();

        try {
            AuthServer.connect();

            server = new ServerSocket(8189);
            System.out.println("сервер запущен");

            while (true){
                socket = server.accept();
            System.out.println("клинт подключен");
            new ClientHandler(socket, this);

            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            sendToAll("/end");
        }
        AuthServer.disconnect();
    }
    public void sendToAll(String msg){
        for(ClientHandler client:
        clientHandlers){
            client.sendMsg(msg);
        }

    }
    public void subscribe(ClientHandler client){
        clientHandlers.add(client);
    }
    public void unsubscribe(ClientHandler client){
        clientHandlers.remove(client);
    }
}
