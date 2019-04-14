package io.mrarm.chatlib.android.storage;

import java.util.List;

import io.mrarm.chatlib.dto.MessageId;
import io.mrarm.chatlib.dto.MessageInfo;

class MessageQueryResult {

    private List<MessageInfo> messages;
    private List<MessageId> messageIds;
    private int afterId;

    public MessageQueryResult(List<MessageInfo> messages, List<MessageId> messageIds, int afterId) {
        this.messages = messages;
        this.afterId = afterId;
        this.messageIds = messageIds;
    }

    public List<MessageInfo> getMessages() {
        return messages;
    }

    public List<MessageId> getMessageIds() {
        return messageIds;
    }

    public int getAfterId() {
        return afterId;
    }

}
