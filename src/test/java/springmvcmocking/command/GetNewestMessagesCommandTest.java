package springmvcmocking.command;

import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;
import springmvcmocking.model.MessageModel;
import springmvcmocking.persistence.Message;
import springmvcmocking.persistence.MessagesView;
import springmvcmocking.persistence.UserRepository;

// This annotation allow mockito to setup mocks inside testing class
@RunWith(MockitoJUnitRunner.class)
public class GetNewestMessagesCommandTest {

    @Mock // Mock for MessagesView
    private MessagesView messagesViewMock; 
    @Mock // Mock for UserRepository
    private UserRepository userRepositoryMock;
    @InjectMocks // Testing class - mocks will inject in it
    private GetNewestMessagesCommand command;

    @Test
    public void testExecute() {
        // Creates messages that messagesViewMock returns
        final Integer userId = 1;
        final Message message1 = new Message();
        message1.setText("text 1");
        message1.setId(1l);
        message1.setUserSenderId(111);
        message1.setUserRecipientId(userId);
        final Message message2 = new Message();
        message2.setText("text 2");
        message2.setId(2l);
        message2.setUserSenderId(333);
        message2.setUserRecipientId(userId);
        // Setup mock for method messagesView.getMessages():
        // if input parameter equals userId, then return messages
        when(messagesViewMock.getMessages(userId)).thenReturn(Arrays.asList(message1, message2));

        // Call testing method, messagesViewMock & userRepositoryMock calls inside
        List<MessageModel> actualMessages = command.execute(userId);
        // Check command result
        assertEquals(2, actualMessages.size());
        assertEquals(message1.getText(), actualMessages.get(0).getText());
        assertEquals(message1.getUserSenderId(), actualMessages.get(0).getUserSenderId());
        assertEquals(message1.getUserRecipientId(), actualMessages.get(0).getUserRecipientId());
        assertEquals(message2.getText(), actualMessages.get(1).getText());
        assertEquals(message2.getUserSenderId(), actualMessages.get(1).getUserSenderId());
        assertEquals(message2.getUserRecipientId(), actualMessages.get(1).getUserRecipientId());
        // Checks that command calls userRepository.setLastViewedMessageId() with expected arguments
        verify(userRepositoryMock).setLastViewedMessageId(userId, message2.getId());
    }

}
