package springmvcmocking.command;

import springmvcmocking.persistence.Message;
import springmvcmocking.persistence.MessagesRepository;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CreateMessageCommandTest {

    @Mock
    private MessagesRepository messagesRepositoryMock;
    @InjectMocks
    private CreateMessageCommand command;

    @Test
    public void testExecute() {
        // Initializing source data for the test
        String expectedText = "Hello, world!";
        Integer expectedUserSenderId = 111;
        Integer expectedUserRecipientId = 222;
        Long expectedMessageId = 999l;
        // Init the argument captor for mock
        ArgumentCaptor<Message> messageCaptor = ArgumentCaptor.forClass(Message.class);
        when(messagesRepositoryMock.createMessage(messageCaptor.capture())).thenReturn(expectedMessageId);
        
        // Call the testing method with arguments
        Long actualMessageId = command.execute(expectedText, expectedUserSenderId, expectedUserRecipientId);
        assertEquals(expectedMessageId, actualMessageId);
        // Retrive parameter passed to message repository
        Message actualMessage = messageCaptor.getValue();
        // Check that the command build DAO object correctly 
        assertEquals(expectedText, actualMessage.getText());
        assertEquals(expectedUserSenderId, actualMessage.getUserSenderId());
        assertEquals(expectedUserRecipientId, actualMessage.getUserRecipientId());
        assertNotNull(actualMessage.getMessageDate());
    }
}
