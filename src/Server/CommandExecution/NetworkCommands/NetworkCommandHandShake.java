package Server.CommandExecution.NetworkCommands;

import Classes.CommandMessage;
import Classes.ServerContext;
import Server.CommandExecution.NetworkCommand;

import java.io.IOException;

public class NetworkCommandHandShake extends NetworkCommand {
    public NetworkCommandHandShake(ServerContext serverContext) {
        super(serverContext);
    }

    @Override
    public String execute(CommandMessage message) throws IOException {
        return "Handshake";
    }

    @Override
    public String toString() {
        return null;
    }
}
