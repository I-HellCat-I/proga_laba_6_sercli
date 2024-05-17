package Client.Network;

import Classes.ClientContext;
import Classes.CommandMessage;
import Classes.Message;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Stack;


public class CommunicationsArray {
    private ClientContext clientContext;
    protected DatagramSocket datagramSocket;
    protected JsonMapper mapper;
    protected InetAddress host;
    protected int serverPort;

    public CommunicationsArray(ClientContext clientContext, InetAddress host, int serverPort) throws IOException {
        this.clientContext = clientContext;
        this.datagramSocket = new DatagramSocket();
        datagramSocket.setSoTimeout(4000);
        this.host = host;
        this.serverPort = serverPort;
        mapper = JsonMapper.builder().findAndAddModules().build();
    }

    public <T> T getMessage(Class<T> toGet) throws IOException {
        DatagramPacket lengthPacket = new DatagramPacket(new byte[1000], 1000), messagePacket;
        datagramSocket.receive(lengthPacket);
        int length = mapper.readValue(lengthPacket.getData(), new TypeReference<Stack<Integer>>() {}).pop();
        messagePacket = new DatagramPacket(new byte[length], length);
        datagramSocket.receive(messagePacket);
        return mapper.readValue(messagePacket.getData(), toGet);
    }


    public void sendMessage(CommandMessage toSend) throws IOException {
        byte[] bytes = mapper.writeValueAsBytes(new Message(toSend, (InetSocketAddress) datagramSocket.getLocalSocketAddress()));
        DatagramPacket packet = new DatagramPacket(bytes, bytes.length, host, 3123);
        datagramSocket.send(packet);
    }

    public void handshake() throws IOException {
        sendMessage(new CommandMessage("CommandExecution.Commands.CommandHandShake", -1, null));
        datagramSocket.receive(new DatagramPacket(new byte[1000], 1000));
        datagramSocket.receive(new DatagramPacket(new byte[1000], 1000));
    }
}
