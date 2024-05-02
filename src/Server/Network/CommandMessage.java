package Server.Network;

import Classes.SendedFlatUpdateRecord;

public record CommandMessage(String commandClass, int numericArgument, SendedFlatUpdateRecord sendedFlatUpdateRecord) {

}
