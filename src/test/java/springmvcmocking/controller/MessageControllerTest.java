package springmvcmocking.controller;

import java.util.Arrays;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import springmvcmocking.command.CreateMessageCommand;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import springmvcmocking.command.GetNewestMessagesCommand;
import springmvcmocking.model.MessageModel;

@RunWith(MockitoJUnitRunner.class)
public class MessageControllerTest {

    @Mock
    private CreateMessageCommand createMessageCommandMock;
    @Mock
    private GetNewestMessagesCommand getNewestMessagesCommandMock;
    @InjectMocks
    private MessageController controller;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testCreateMessage() throws Exception {
        // Initialize source data
        MessageModel message = new MessageModel();
        message.setText("Привет!");
        message.setUserSenderId(1);
        message.setUserRecipientId(2);
        final Long messageId = 999l;
        // Setup mock for createMessageCommand
        when(createMessageCommandMock.execute(anyString(), anyInt(), anyInt())).thenReturn(messageId);
        // Emulate HTTP request to controller
        mockMvc.perform(put("/message") // PUT to the path of /message
                .contentType(TestUtil.APPLICATION_JSON_UTF8) // setup content type header
                .content(TestUtil.convertObjectToJsonBytes(message))) // Content has object in JSON format
                .andExpect(status().isCreated()) // Expects in response HTTP 201 status
                .andExpect(content().string(messageId.toString())); // Expects in response new object id
        
        // Check that controller calls command with correct arguments
        verify(createMessageCommandMock).execute(message.getText(), message.getUserSenderId(), message.getUserRecipientId());
    }

    @Test
    public void testNewMessages() throws Exception {
        // Initialize source data
        final Integer userId = 111;
        MessageModel message1 = new MessageModel();
        message1.setText("текст 1");
        message1.setUserRecipientId(userId);
        message1.setUserSenderId(222);
        MessageModel message2 = new MessageModel();
        message2.setText("текст 2");
        message2.setUserRecipientId(userId);
        message2.setUserSenderId(333);
        // Setup mock for getNewestMessagesCommand
        when(getNewestMessagesCommandMock.execute(userId)).thenReturn(Arrays.asList(message1, message2));
        // Emulate HTTP request to controller
        mockMvc.perform(get("/message/{userId}", userId)) // GET to the path of /message/111
                .andExpect(status().isOk()) // Expects in response HTTP 200 status
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8)) // Expects that response contains valid JSON content type header
                // Below checks response content
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].userRecipientId", is(message1.getUserRecipientId())))
                .andExpect(jsonPath("$[0].userSenderId", is(message1.getUserSenderId())))
                .andExpect(jsonPath("$[0].text", is(message1.getText())))
                .andExpect(jsonPath("$[1].userRecipientId", is(message2.getUserRecipientId())))
                .andExpect(jsonPath("$[1].userSenderId", is(message2.getUserSenderId())))
                .andExpect(jsonPath("$[1].text", is(message2.getText())));
        
        // Checks that command calls getNewestMessagesCommand with expected arguments
        verify(getNewestMessagesCommandMock).execute(userId);
    }
}
