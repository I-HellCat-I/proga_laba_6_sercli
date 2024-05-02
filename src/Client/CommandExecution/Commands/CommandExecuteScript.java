package Client.CommandExecution.Commands;

import Classes.ClientContext;
import Client.CommandExecution.Command;

public class CommandExecuteScript extends Command {
    public CommandExecuteScript(String[] args, ClientContext clientContext) {
        super(args, clientContext);
    }

    @Override
    public String execute() {
        return clientContext.getInteractor().executeScript(args[0]);
    }

    public static String description(){
        return "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.";
    }
}
