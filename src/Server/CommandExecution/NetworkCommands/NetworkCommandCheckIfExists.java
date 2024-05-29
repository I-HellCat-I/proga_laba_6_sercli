package Server.CommandExecution.NetworkCommands;

import Classes.Message;
import Classes.ServerContext;
import Server.CommandExecution.NetworkCommand;

import java.io.IOException;

public class NetworkCommandCheckIfExists extends NetworkCommand {
    public NetworkCommandCheckIfExists(ServerContext serverContext) {
        super(serverContext);
    }

    @Override
    public String execute(Message message) throws IOException {
        boolean fl = false;
        if (serverContext.getStructureStorage().getFlatById(message.commandMessage().numericArgument()) != null) {
            return "true";
        }
        return "false";
    }

    @Override
    public String toString() {
        return null;
    }
}
