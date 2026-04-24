package com.ramyres.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public void Run(int port) throws IOException {
        ServerSocket ss = new ServerSocket(port);
        while (true) {
            Socket clientSocket = ss.accept();
            new Thread(() -> handleClient(clientSocket)).start();
        }
    }

    private void handleClient(Socket socket) {
        try (
            Socket s = socket; 
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter out = new PrintWriter(s.getOutputStream(), true)
        ) {
            String request = in.readLine();
            
            if (request != null) {
                // TODO:
                out.println("Pong");
                s.close();
            }
        }
        catch(IOException e){
            System.err.println("Erro ao comunicar com o cliente: " + e.getMessage());
        }
    }
}
