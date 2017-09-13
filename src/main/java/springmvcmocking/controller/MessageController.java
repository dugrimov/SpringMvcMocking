package springmvcmocking.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import springmvcmocking.command.CreateMessageCommand;
import springmvcmocking.command.GetNewestMessagesCommand;
import springmvcmocking.model.MessageModel;

@Controller
public class MessageController {

    @Autowired
    private CreateMessageCommand createMessageCommand;
    @Autowired
    private GetNewestMessagesCommand getNewestMessagesCommand;

    @RequestMapping(value = "/message", method = RequestMethod.PUT)
    public ResponseEntity<Long> createMessage(@RequestBody MessageModel message) {
        Long messageId = createMessageCommand.execute(message.getText(), message.getUserSenderId(), message.getUserRecipientId());
        return new ResponseEntity<>(messageId, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/message/{userId}", method = RequestMethod.GET)
    public @ResponseBody List<MessageModel> newMessages(@PathVariable Integer userId) {
        return getNewestMessagesCommand.execute(userId);
    }
}
