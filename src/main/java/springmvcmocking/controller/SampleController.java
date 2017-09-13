package springmvcmocking.controller;

import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import springmvcmocking.command.GetMessageCommand;
import springmvcmocking.model.MessageModel;

@Controller
public class SampleController {

    @Autowired
    private GetMessageCommand getMessageCommand;

    @RequestMapping(value = "/message/{messageId}", method = RequestMethod.GET)
    public @ResponseBody
    MessageModel getMessage(@PathVariable Long messageId, HttpServletResponse response) {
        if (messageId < 1) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        MessageModel message = getMessageCommand.execute(messageId);
        if (message != null) {
            return message;
        }
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        return null;
    }
}
