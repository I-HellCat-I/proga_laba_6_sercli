package Server.CommandExecution;

import Classes.Message;
import Classes.ServerContext;
import Server.Network.ServerCommunicationsArray;

import java.net.SocketAddress;
import java.util.concurrent.ThreadFactory;

public class CommandThreadFactory implements ThreadFactory {
    private ServerContext serverContext;
    public CommandThreadFactory(ServerContext serverContext){
        this.serverContext = serverContext;
    }
    public Thread newThread(NetworkCommand command, Message message, SocketAddress client){
        return new Thread(new CommandRunnable(command, client, message));
    }
    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r);
    }
}

