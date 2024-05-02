package Client.CommandExecution.Commands;

import Classes.ClientContext;
import Classes.SendedFlatUpdateRecord;
import Client.CommandExecution.Command;
import Client.Network.CommandMessage;

import java.io.IOException;


public class CommandUpdate extends Command {
    public CommandUpdate(String[] args, ClientContext clientContext) {
        super(args, clientContext);
    }

    @Override
    public String execute() throws IOException {
        int id = Integer.parseInt(args[0]);
        clientContext.getCommunicationsArray().sendMessage(new CommandMessage(this.getClass().getName(),-1, new SendedFlatUpdateRecord(id, clientContext.getInteractor().inputFlat(true))));
        return clientContext.getCommunicationsArray().getMessage(String.class);
    }
    public static String description(){
        return "update id {element} : обновить значение элемента коллекции, id которого равен заданному";
    }
}
