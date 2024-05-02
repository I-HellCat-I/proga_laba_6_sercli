package Client.CommandExecution;

import Classes.ClientContext;

import java.io.IOException;

/**
 * База для всех команд.
 */
public abstract class Command {

    protected String[] args;
    protected ClientContext clientContext;

    public Command(String[] args, ClientContext clientContext) {
        this.args = args;
        this.clientContext = clientContext;
    }

    public abstract String execute() throws IOException;

    public static String description(){
        return "desc";
    }
}
