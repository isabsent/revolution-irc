package io.mrarm.chatlib.irc.cap;

import io.mrarm.chatlib.dto.MessageInfo;
import io.mrarm.chatlib.irc.CommandHandler;
import io.mrarm.chatlib.irc.ServerConnectionData;

import java.util.Map;

public abstract class Capability implements CommandHandler {//https://tools.ietf.org/html/draft-mitchell-irc-capabilities-01

    public abstract String[] getNames();

    public boolean shouldEnableCapability(ServerConnectionData connection, CapabilityEntryPair capability) {
        return true;
    }

    public boolean isBlockingNegotationFinish() {
        return false;
    }

    public void onEnabled(ServerConnectionData connection) {
    }

    public void onDisabled(ServerConnectionData connection) {
    }

    public void processMessage(MessageInfo.Builder message, Map<String, String> tags) {
    }

}
