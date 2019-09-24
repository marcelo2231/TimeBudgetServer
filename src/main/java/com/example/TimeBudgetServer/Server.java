package com.example.TimeBudgetServer;

import com.example.TimeBudgetServer.handlers.DefaultHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

/*********
 * Server class: Main TimeBudgetServer class. This class is responsible for starting the TimeBudgetServer.
 *********/
public class Server {
    // Global Fields
    private static Server _server = new Server();
    private int _port = 8080;
    private int MAX_WAITING_CONNECTIONS = 100;

    /**
     * main: Main TimeBudgetServer method.
     * @param args:
     */
    public static void main(String[] args){
        _server.startServer();
    }

    /**
     * startServer: Starts the Time Budget Server
     */
    private void startServer() {
        HttpServer server;

        try {
            server = HttpServer.create(new InetSocketAddress(_port), MAX_WAITING_CONNECTIONS);
            System.out.println("Server started on port: " + _port);
        }
        catch (IOException e) {
            System.out.println("Error: failed to start TimeBudgetServer on port: " + _port);
            e.printStackTrace();
            return;
        }

        server.setExecutor(null); // Using default "executor"

        /** Create Handlers */
        server.createContext("/", new DefaultHandler());

        /**  Start Server */
        server.start();
    }
}