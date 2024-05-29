package Server.CommandExecution.NetworkCommands;

import Classes.Message;
import Classes.ServerContext;
import Server.CommandExecution.NetworkCommand;

import java.io.IOException;

public class NetworkCommandHandShake extends NetworkCommand {
    public NetworkCommandHandShake(ServerContext serverContext) {
        super(serverContext);
    }

    @Override
    public String execute(Message message) throws IOException {
        return "Handshake";
    }

    @Override
    public String toString() {
        return null;
    }
}
