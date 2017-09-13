package springmvcmocking.persistence;

import java.util.List;

public interface MessagesView {
    List<Message> getMessages(Integer userId);
}
