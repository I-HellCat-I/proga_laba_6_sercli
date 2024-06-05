package Server.CommandExecution;

import Classes.Message;
import Client.Network.CommunicationsArray;
import Server.Network.ServerCommunicationsArray;

import java.io.IOException;
import java.net.SocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

class CommandRunnable implements Runnable {
    NetworkCommand command;
    SocketAddress client;
    Message message;

    public CommandRunnable(NetworkCommand command, SocketAddress client, Message message) {
        this.command = command;
        this.client = client;
        this.message = message;
    }

    @Override
    public void run() {
        try {
            ServerCommunicationsArray.sendMessage(
                    command.execute(message), client
            );
        } catch (IOException e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, e.getMessage());
        }
    }
}
