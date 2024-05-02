package Server.Network;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.SocketException;

public class GetterDatagramSocket extends DatagramSocket {

    public GetterDatagramSocket() throws SocketException {
    }

    public GetterDatagramSocket(SocketAddress bindaddr) throws SocketException {
        super(bindaddr);
    }

    public GetterDatagramSocket(int port) throws SocketException {
        super(port);
    }

    public GetterDatagramSocket(int port, InetAddress laddr) throws SocketException {
        super(port, laddr);
    }
}
