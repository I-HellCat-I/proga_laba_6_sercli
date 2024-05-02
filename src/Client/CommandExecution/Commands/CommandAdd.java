package Client.CommandExecution.Commands;

import Classes.ClientContext;
import Classes.SendedFlatUpdateRecord;
import Client.CommandExecution.Command;
import Client.Network.CommandMessage;

import java.io.IOException;


public class CommandAdd extends Command {
    public CommandAdd(String[] args, ClientContext clientContext) {
        super(args, clientContext);
    }

    @Override
    public String execute() throws IOException {
        try{
            SendedFlatUpdateRecord toAdd = new SendedFlatUpdateRecord(-1, clientContext.getInteractor().inputFlat(false));
            if (toAdd != null) {
                clientContext.getCommunicationsArray().sendMessage(new CommandMessage(this.getClass().getName(),-1, toAdd));
                return clientContext.getCommunicationsArray().getMessage(String.class);
            }
        } catch (NumberFormatException nfe){
            return "Не рекомендую пропускать числа.";
        }
        catch (RuntimeException e){
            return (e.getMessage() + " " + e.getClass());
        }
        return ("Что-то пошло не так, но что же?");
    }

    public static String description(){
        return "add {element} : добавить новый элемент в коллекцию";
    }
}
