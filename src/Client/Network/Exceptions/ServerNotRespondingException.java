package Client.Network.Exceptions;

public class ServerNotRespondingException extends RuntimeException{
    public ServerNotRespondingException(){
        super("server is not responding");
    }
}
