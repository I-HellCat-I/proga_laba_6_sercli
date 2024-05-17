package Server.CommandExecution;

import Classes.ServerContext;

import java.io.IOException;

/**
 * База для всех локальных команд.
 */
public abstract class LocalCommand {

    protected String[] args;
    protected ServerContext serverContext;

    public LocalCommand(String[] args, ServerContext serverContext) {
        this.args = args;
        this.serverContext = serverContext;
    }

    public abstract String execute() throws IOException;

    public static String description(){
        return "desc";
    }
}
