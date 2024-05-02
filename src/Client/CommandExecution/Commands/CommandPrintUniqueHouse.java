package Client.CommandExecution.Commands;

import Classes.ClientContext;
import Client.CommandExecution.Command;
import Client.Network.CommandMessage;

import java.io.IOException;

public class CommandPrintUniqueHouse extends Command {
    public CommandPrintUniqueHouse(String[] args, ClientContext clientContext) {
        super(args, clientContext);
    }

    @Override
    public String execute() throws IOException {
        clientContext.getCommunicationsArray().sendMessage(new CommandMessage(this.getClass().getName(),-1, null));
        return clientContext.getCommunicationsArray().getMessage(String.class);
    }
    public static String description(){
        return "print_unique_house : вывести уникальные значения поля house всех элементов в коллекции";
    }
}
