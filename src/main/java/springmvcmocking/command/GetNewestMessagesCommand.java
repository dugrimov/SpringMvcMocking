package springmvcmocking.command;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import springmvcmocking.model.MessageModel;
import springmvcmocking.persistence.Message;
import springmvcmocking.persistence.MessagesView;
import springmvcmocking.persistence.UserRepository;

@Component
public class GetNewestMessagesCommand {

    @Autowired
    private MessagesView messagesView;
    @Autowired
    private UserRepository userRepository;

    public List<MessageModel> execute(Integer userId) {
        List<Message> dbMessages = messagesView.getMessages(userId);
        List<MessageModel> userMessages = new ArrayList<>();
        for (Message message : dbMessages) {
            MessageModel userMessage = new MessageModel();
            userMessage.setText(message.getText());
            userMessage.setUserSenderId(message.getUserSenderId());
            userMessage.setUserRecipientId(message.getUserRecipientId());
            userMessages.add(userMessage);
        }
        userRepository.setLastViewedMessageId(userId, dbMessages.get(dbMessages.size() - 1).getId());
        return userMessages;
    }
}
