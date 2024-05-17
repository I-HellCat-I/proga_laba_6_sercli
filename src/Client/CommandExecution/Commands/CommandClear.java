package Client.CommandExecution.Commands;

import Classes.ClientContext;
import Classes.CommandMessage;
import Client.CommandExecution.Command;

import java.io.IOException;

public class CommandClear extends Command {
    public CommandClear(String[] args, ClientContext clientContext) {
        super(args, clientContext);
    }

    @Override
    public String execute() throws IOException {
        clientContext.getCommunicationsArray().sendMessage(new CommandMessage(this.getClass().getName(),-1, null));
        return clientContext.getCommunicationsArray().getMessage(String.class);
    }

    public static String description(){
        return "clear : очистить коллекцию";
    }
}
