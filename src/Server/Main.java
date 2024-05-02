package Server;

import Classes.ServerContext;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ServerContext serverContext = new ServerContext();
        while (true) {
            serverContext.getCommandManager().exec(serverContext.getServerCommunicationsArray().getCommandMessage(), serverContext.getServerCommunicationsArray());
        }
    }
}