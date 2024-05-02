package Client.CommandExecution.Commands;

import Classes.ClientContext;
import Client.CommandExecution.Command;
import Client.Network.CommandMessage;

import java.io.IOException;

public class CommandRemoveAt extends Command {
    public CommandRemoveAt(String[] args, ClientContext clientContext) {
        super(args, clientContext);
    }

    @Override
    public String execute() throws IOException {
        clientContext.getCommunicationsArray().sendMessage(new CommandMessage(this.getClass().getName(),Integer.parseInt(args[0]), null));
        return clientContext.getCommunicationsArray().getMessage(String.class);
    }
    public static String description(){
        return "remove_at index : удалить элемент, находящийся в заданной позиции коллекции (index)";
    }
}
