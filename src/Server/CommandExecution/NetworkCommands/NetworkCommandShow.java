package Server.CommandExecution.NetworkCommands;

import Classes.Message;
import Classes.ServerContext;
import Server.CommandExecution.NetworkCommand;

public class NetworkCommandShow extends NetworkCommand {
    public NetworkCommandShow(ServerContext serverContext) {
        super(serverContext);
    }

    @Override
    public String execute(Message message) {
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
