package Client.CommandExecution.Commands;

import Classes.ClientContext;
import Classes.CommandMessage;
import Classes.SendedFlatUpdateRecord;
import Client.CommandExecution.Command;

import java.io.IOException;


public class CommandUpdate extends Command {
    public CommandUpdate(String[] args, ClientContext clientContext) {
        super(args, clientContext);
    }

    @Override
    public String execute() throws IOException {
        int id;
        try {
            id = Integer.parseInt(args[0]);
        } catch (NumberFormatException e){
            return "То, что вы ввели - не число";
        }
        clientContext.getCommunicationsArray().sendMessage(new CommandMessage("CommandExecution.Commands.CommandCheckIfExists", id, null));
        boolean ans = clientContext.getCommunicationsArray().getMessage(boolean.class);
        if (!ans){
            return "Квартиры с таким id не найдено";
        }
        clientContext.getCommunicationsArray().sendMessage(new CommandMessage(this.getClass().getName(),-1,
                new SendedFlatUpdateRecord(id, clientContext.getInteractor().inputFlat(true))));
        return clientContext.getCommunicationsArray().getMessage(String.class);
    }
    public static String description(){
        return "update id {element} : обновить значение элемента коллекции, id которого равен заданному";
    }
}
