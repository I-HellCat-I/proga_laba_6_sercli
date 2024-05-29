package Server.CommandExecution;

import Classes.Message;
import Classes.ServerContext;

import java.io.IOException;

/**
 * База для всех команд.
 */
public abstract class NetworkCommand{

    protected ServerContext serverContext;

    public NetworkCommand(ServerContext serverContext) {
        this.serverContext = serverContext;
    }


    public abstract String execute(Message message) throws IOException;

    public abstract String toString();
}
