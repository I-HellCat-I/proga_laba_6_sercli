package Client.CommandExecution.Commands;

import Classes.ClientContext;
import Classes.CommandMessage;
import Client.CommandExecution.Command;

import java.io.IOException;

public class CommandShow extends Command {
    public CommandShow(String[] args, ClientContext clientContext) {
        super(args, clientContext);
    }

    @Override
    public String execute() throws IOException {
        clientContext.getCommunicationsArray().sendMessage(new CommandMessage(this.getClass().getName(),-1, null));
        return clientContext.getCommunicationsArray().getMessage(String.class);
    }
    public static String description(){
        return "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }
}
