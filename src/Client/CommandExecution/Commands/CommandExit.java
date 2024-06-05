package Client.CommandExecution.Commands;

import Classes.ClientContext;
import Client.CommandExecution.Command;

import static java.lang.System.exit;

public class CommandExit extends Command {
    public CommandExit(String[] args, ClientContext clientContext) {
        super(args, clientContext);
    }

    @Override
    public String execute() {
        clientContext.setExitCommandUsed();
        exit(1);
        return "Ok";
    }

    public static String description(){
        return "exit : завершить программу";
    }
}
