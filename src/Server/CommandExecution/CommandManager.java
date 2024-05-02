package Server.CommandExecution;

import Classes.ServerContext;
import Server.CommandExecution.Commands.*;
import Server.Network.CommandMessage;
import Server.Network.ServerCommunicationsArray;
import lombok.Getter;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Класс, отвечающий за обработку команд
 */
public class CommandManager {

    @Getter
    private HashMap<String, Class<? extends Command>> commands = new HashMap<>(); // Хранит в себе команды
    private ServerContext serverContext;

    /*
      Блок, задающий базовые команды
      */
    public CommandManager(ServerContext serverContext) {
        this.serverContext = serverContext;
        addCommand("Server.Client.CommandExecution.Commands.CommandInfo", CommandInfo.class);
        addCommand("Server.Client.CommandExecution.Commands.CommandShow", CommandShow.class);
        addCommand("Server.Client.CommandExecution.Commands.CommandAdd", CommandAdd.class);
        addCommand("Server.Client.CommandExecution.Commands.CommandSort", CommandSort.class);
        addCommand("Server.Client.CommandExecution.Commands.CommandUpdate", CommandUpdate.class);
        addCommand("Server.Client.CommandExecution.Commands.CommandRemoveById", CommandRemoveById.class);
        addCommand("Server.Client.CommandExecution.Commands.CommandClear", CommandClear.class);
        addCommand("Server.Client.CommandExecution.Commands.CommandRemoveAt", CommandRemoveAt.class);
        addCommand("Server.Client.CommandExecution.Commands.CommandRemoveLast", CommandRemoveLast.class);
        addCommand("Server.Client.CommandExecution.Commands.CommandCountLTFurnish", CommandCountLTFurnish.class);
        addCommand("Server.Client.CommandExecution.Commands.CommandRemoveById", CommandRemoveById.class);
        addCommand("Server.Client.CommandExecution.Commands.CommandExecuteScript", CommandExecuteScript.class);
        addCommand("Server.Client.CommandExecution.Commands.CommandSumOfNumberOfRooms", CommandSumOfNumberOfRooms.class);
        addCommand("Server.Client.CommandExecution.Commands.CommandPrintUniqueHouse", CommandPrintUniqueHouse.class);
    }

    public void addCommand(String s, Class<? extends Command> f) {
        commands.put(s, f);
    }

    public <T> void exec(CommandMessage message, ServerCommunicationsArray communicationsArray) {
        try {
            communicationsArray.sendMessage(
                    commands.get(message.commandClass()).getConstructor(ServerContext.class)
                            .newInstance(serverContext).execute(message)
            );
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException |
                 IOException e) {
            Logger.getAnonymousLogger().log(Level.WARNING, "CommandManager.exec", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
