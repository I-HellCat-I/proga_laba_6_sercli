package Server;

import Classes.Message;
import Classes.ServerContext;
import Server.CommandExecution.Interactor;

import java.io.IOException;
import java.net.SocketAddress;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        ServerContext serverContext = new ServerContext();
        Thread serverCommandsExecutor = new Thread(){
            @Override
            public void run() {
                Interactor interactor = new Interactor(serverContext);
                Scanner scanner = new Scanner(System.in);
                while (true){
                    interactor.masterProcessInput(scanner.nextLine());
                }
            }
        };
        serverCommandsExecutor.start();
        while (true) {
            Message message = serverContext.getServerCommunicationsArray().getMessage();
            SocketAddress lastClient = serverContext.getServerCommunicationsArray().getLastClient();
            serverContext.getCommandManager().exec(message, lastClient);
        }
    }
}