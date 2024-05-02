package Client.CommandExecution.Commands;

import Classes.ClientContext;
import Client.CommandExecution.Command;
import Client.Network.CommandMessage;

import java.io.IOException;

public class CommandRemoveLast extends Command {

    public CommandRemoveLast(String[] args, ClientContext clientContext) {
        super(args, clientContext);
    }

    @Override
    public String execute() throws IOException {
        clientContext.getCommunicationsArray().sendMessage(new CommandMessage(this.getClass().getName(),-1, null));
        return clientContext.getCommunicationsArray().getMessage(String.class);
    }
    public static String description(){
        return "remove_last : удалить последний элемент из коллекции";
    }
}
