package com.ramyres;

import com.ramyres.server.Server;

public class Main {
    public static void main(String[] args) {
        try {
            int port = 7567;
            System.out.println("Executando servidor na porta "+port);
            new Server().Run(port);
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}