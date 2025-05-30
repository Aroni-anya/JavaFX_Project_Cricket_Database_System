package Network;

import MainPackage.Main;
import PlayerData.Database;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Server {

    private ServerSocket serverSocket;
    public static Database database;
    public static List<SocketWrapper> clientlist;

    public Server() throws Exception {

        database = new Database();
        clientlist = Collections.synchronizedList(new ArrayList<>());
        database.loadFromFile();
        Main.setAllPlayerlist(database.getAllPlayers());
        System.out.println("Database all player size in server: "+database.getAllPlayers().getPlayers().size());
        System.out.println("Player and passwords loaded successfully");

        try {
            serverSocket = new ServerSocket(33333);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                serve(clientSocket);
            }
        } catch (Exception e) {
            System.out.println("Server starts:" + e);
        }
    }

    public void serve(Socket clientSocket) {
        try {
            SocketWrapper socketWrapper = new SocketWrapper(clientSocket);
            clientlist.add(socketWrapper);
            socketWrapper.write(database.getAllPlayers());
            new ReadThreadServer(socketWrapper, clientlist);
        } catch (IOException e) {
            System.err.println("Error handling client: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        new Server();
    }
}

