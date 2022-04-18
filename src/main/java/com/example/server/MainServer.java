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
            server = new ServerSocket(8189);
            System.out.println("сервер запущен");
            while (true){
                socket = server.accept();
            System.out.println("клинт подключен");
            clientHandlers.add(new ClientHandler(socket));
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
