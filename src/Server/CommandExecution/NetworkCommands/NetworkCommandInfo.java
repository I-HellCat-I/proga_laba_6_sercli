package Server.CommandExecution.NetworkCommands;

import Classes.Message;
import Classes.ServerContext;
import Server.CommandExecution.NetworkCommand;

public class NetworkCommandInfo extends NetworkCommand {
    public NetworkCommandInfo(ServerContext serverContext) {
        super(serverContext);
    }

    @Override
    public String execute(Message message) {
        return (serverContext.getStructureStorage().getClass() + " " + serverContext.getInitDate() + " " + serverContext.getStructureStorage().getSize());
    }

    @Override
    public String toString() {
        return "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)";
    }
}
