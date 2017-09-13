package springmvcmocking.command;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import springmvcmocking.persistence.Message;
import springmvcmocking.persistence.MessagesRepository;

@Component
public class CreateMessageCommand {
    
    @Autowired
    private MessagesRepository messagesRepository;
    
    public Long execute(String text, Integer userSenderId, Integer userRecipientId) {
        Message message = new Message();
        message.setText(text);
        message.setUserSenderId(userSenderId);
        message.setUserRecipientId(userRecipientId);
        message.setMessageDate(new Date());
        return messagesRepository.createMessage(message);
    }
}
