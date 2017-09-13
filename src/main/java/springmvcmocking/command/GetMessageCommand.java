package springmvcmocking.command;

import org.springframework.stereotype.Component;
import springmvcmocking.model.MessageModel;

@Component
public class GetMessageCommand {
    public MessageModel execute(Long messageId){
        throw new UnsupportedOperationException();
    }
}
