package Client.CommandExecution.Commands;

import Classes.ClientContext;
import Client.CommandExecution.Command;
import Client.Network.CommandMessage;

import java.io.IOException;

public class CommandRemoveById extends Command {
    public CommandRemoveById(String[] args, ClientContext clientContext) {
        super(args, clientContext);
    }

    @Override
    public String execute() throws IOException {
        Integer id;
        try {
            id = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            return ("Неверный формат Id (Не целое число)");
        }
        clientContext.getCommunicationsArray().sendMessage(new CommandMessage(this.getClass().getName(),Integer.parseInt(args[0]), null));
        return clientContext.getCommunicationsArray().getMessage(String.class);
    }
    public static String description(){
        return "remove_by_id id : удалить элемент из коллекции по его id";
    }
}
