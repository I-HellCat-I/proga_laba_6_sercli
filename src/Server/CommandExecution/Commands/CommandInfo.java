package Server.CommandExecution.Commands;

import Classes.ServerContext;
import Server.CommandExecution.Command;
import Server.Network.CommandMessage;

public class CommandInfo extends Command {
    public CommandInfo(ServerContext serverContext) {
        super(serverContext);
    }

    @Override
    public String execute(CommandMessage message) {
        return (serverContext.getStructureStorage().getClass() + " " + serverContext.getInitDate() + " " + serverContext.getStructureStorage().getSize());
    }

    @Override
    public String toString() {
        return "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)";
    }
}
