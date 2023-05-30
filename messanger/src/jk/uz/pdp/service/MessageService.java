package jk.uz.pdp.service;

import jk.uz.pdp.model.Message;
import jk.uz.pdp.model.User;
import jk.uz.pdp.model.dto.MessageResponseDto;

public class MessageService {
    static Message[] messageList = new Message[100];
    private int index = 0;

    public String add(User sender, User receiver, String messageText) {
        Message message = new Message(
                sender.getId(),
                receiver.getId(),
                messageText,
                System.currentTimeMillis()
        );
        messageList[index++] = message;
        return "message sent to " + receiver.getUsername() + "!";
    }


    //    interCurrentUser suhbatdosh
    public MessageResponseDto[] getMessageList(User currentUser, User interCurrentUser) {
        Message[] helper = new Message[40];
        int index = 0;
        for (Message message : messageList) {
            if (message != null) {
                if (message.getSender().equals(currentUser.getId())
                    && message.getReceiver().equals(interCurrentUser.getId())){
                    helper[index ++] = message;
                }
                if (message.getReceiver().equals(currentUser.getId())
                    && message.getSender().equals(interCurrentUser.getId())){
                    helper[index ++] = message;
                }
            }
        }
        return this.buildMessage(helper,currentUser);
    }

    private MessageResponseDto[] buildMessage(Message[] messageList, User currentUser){
        MessageResponseDto[] messageResponseDtoList = new MessageResponseDto[100];
        int index = 0;
        for (Message message: messageList) {
            if (message != null){
                if (message.getSender().equals(currentUser.getId())){
                    messageResponseDtoList[index ++] = new MessageResponseDto(message.getSender(), message.getMessage(),true);
                }else {
                    messageResponseDtoList[index ++] = new MessageResponseDto(message.getReceiver(), message.getMessage(),false);
                }
            }
        }
        return messageResponseDtoList;
    }

    public int getNotificationCount(User currentUser){
        int cnt = 0;
        for (Message message: messageList) {
            if (message != null){
                if (message.getReceiver().equals(currentUser.getId()) && !message.isRead()){
                    cnt ++;
                }
            }
        }
        return cnt;
    }

    public User[] getNotificationUserList(User currentUser, int cntOfNotificationUser){
        User[] notificationUser = new User[cntOfNotificationUser];
        int index = 0;
        for (Message message: messageList) {
            if (message != null){
                if (
                        message.getReceiver().equals(currentUser.getId()) &&
                        !message.isRead()
                ){
                    notificationUser[index ++] = UserService.getUser(message.getSender());
                }
            }
        }
        return notificationUser;
    }
}
