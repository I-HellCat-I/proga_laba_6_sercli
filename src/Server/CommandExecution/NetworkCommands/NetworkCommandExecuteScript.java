package Server.CommandExecution.NetworkCommands;

import Classes.CommandMessage;
import Classes.ServerContext;
import Server.CommandExecution.NetworkCommand;

public class NetworkCommandExecuteScript extends NetworkCommand {
    public NetworkCommandExecuteScript(ServerContext serverContext) {
        super(serverContext);
    }

//    @Override
//    public String execute() {
//        return serverContext.getInteractor().executeScript(args[0]);
//    }

    @Override
    public String execute(CommandMessage message) {
        return null;
    }

    @Override
    public String toString() {
        return "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.";
    }
}
