package Client.CommandExecution.Commands;

import Classes.ClientContext;
import Client.CommandExecution.Command;
import Client.Network.CommandMessage;

import java.io.IOException;

public class CommandSumOfNumberOfRooms extends Command {
    public CommandSumOfNumberOfRooms(String[] args, ClientContext clientContext) {
        super(args, clientContext);
    }

    @Override
    public String execute() throws IOException {
        clientContext.getCommunicationsArray().sendMessage(new CommandMessage(this.getClass().getName(),-1, null));
        return clientContext.getCommunicationsArray().getMessage(String.class);
    }
    public static String description(){
        return "sum_of_number_of_rooms : вывести сумму значений поля numberOfRooms для всех элементов коллекции";
    }
}
