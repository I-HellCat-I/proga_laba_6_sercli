package Server.CommandExecution.Commands;

import Classes.ServerContext;
import Server.CommandExecution.Command;
import Server.Network.CommandMessage;

public class CommandShow extends Command {
    public CommandShow(ServerContext serverContext) {
        super(serverContext);
    }

    @Override
    public String execute(CommandMessage message) {
        StringBuilder ans = new StringBuilder();
        serverContext.getStructureStorage().getCollection().forEach(ans::append);
        if (ans.isEmpty()){
            ans.append("Пусто");
        }
        return ans.toString();
    }

    @Override
    public String toString() {
        return "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }
}
