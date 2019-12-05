package io.rainrobot.maxdispachreq;

public interface IMessageSender {
    void send(RequestMsg msg, ServerAddress address);
}
