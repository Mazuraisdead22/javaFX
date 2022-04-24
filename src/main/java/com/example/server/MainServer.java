package com.example.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;


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
    public void sendOnLineUsers(){
        StringBuilder sb = new StringBuilder();
        List<String> list = clientHandlers.stream().map(ClientHandler::getNickname).collect(Collectors.toList());
        for (String s:
        list){
            sb.append(s);
            sb.append(" ");
        }
        sendToAll("/show" + sb.toString().trim());
    }

    public void subscribe(ClientHandler client){

        clientHandlers.add(client);
    }
    public void unsubscribe(ClientHandler client){
        sendToAll("User " + client.getNickname() + " is out!");
        sendOnLineUsers();
        clientHandlers.remove(client);
    }
    public boolean isNickFree(String nick){
        if(clientHandlers.isEmpty()) return true;

        for (ClientHandler client :
        clientHandlers){
            if (client.getNickname().equals(nick)){
                return false;
            }
        }
        return true;
    }
}
