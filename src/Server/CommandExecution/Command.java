package Server.CommandExecution;

import Classes.ServerContext;
import Server.Network.CommandMessage;

import java.io.IOException;

/**
 * База для всех команд.
 */
public abstract class Command {

    protected ServerContext serverContext;

    public Command(ServerContext serverContext) {
        this.serverContext = serverContext;
    }

    public abstract String execute(CommandMessage message) throws IOException;

    public abstract String toString();
}
