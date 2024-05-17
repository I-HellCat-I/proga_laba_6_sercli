package Server;

import Classes.ServerContext;
import Server.CommandExecution.Interactor;

import java.io.IOException;
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
            serverContext.getCommandManager().exec(serverContext.getServerCommunicationsArray().getCommandMessage(), serverContext.getServerCommunicationsArray());
        }
    }
}