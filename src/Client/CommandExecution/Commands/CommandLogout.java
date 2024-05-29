package Client.CommandExecution.Commands;

import Classes.ClientContext;
import Client.CommandExecution.Command;

import java.io.IOException;

public class CommandLogout extends Command {
    public CommandLogout(String[] args, ClientContext clientContext) {
        super(args, clientContext);
    }

    @Override
    public String execute() throws IOException {
        clientContext.logout();
        return "Done";
    }
}
