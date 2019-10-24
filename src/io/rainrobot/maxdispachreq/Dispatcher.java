package io.rainrobot.maxdispachreq;

public class Dispatcher {

    private AddressService addressService;
    private IMessageSender messageSender;

    //API GET MSG
    public void getMessage(RequestMsg req) {
        handleMessage(req);
    }

    public void handleMessage(RequestMsg msg) {
        String id = msg.getId();
        ServerAddress address = addressService.getAddress(id);
        messageSender.send(msg, address);
    }


}
