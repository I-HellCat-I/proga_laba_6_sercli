package Server.CommandExecution.LocalCommands;

import Classes.ServerContext;
import Server.CommandExecution.LocalCommand;

import java.io.IOException;

public class LocalCommandSave extends LocalCommand {

    public LocalCommandSave(String[] args, ServerContext serverContext) {
        super(args, serverContext);
    }

    @Override
    public String execute() throws IOException {
        serverContext.getFileManager().saveCollection();
        return null;
    }
}
