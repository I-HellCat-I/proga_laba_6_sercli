package Server.CommandExecution;

import Classes.Message;
import Server.Network.ServerCommunicationsArray;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

class CommandRunnable implements Runnable {
    NetworkCommand command;
    ServerCommunicationsArray communicationsArray;
    Message message;

    public CommandRunnable(NetworkCommand command, ServerCommunicationsArray communicationsArray, Message message) {
        this.command = command;
        this.communicationsArray = communicationsArray;
        this.message = message;
    }

    @Override
    public void run() {
        try {
            communicationsArray.sendMessage(
                    command.execute(message)
            );
        } catch (IOException e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, e.getMessage());
        }
    }
}
