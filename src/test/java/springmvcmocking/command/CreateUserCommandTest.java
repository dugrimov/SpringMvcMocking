package springmvcmocking.command;

import springmvcmocking.model.UserModel;
import springmvcmocking.persistence.User;
import springmvcmocking.persistence.UserRepository;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.argThat;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CreateUserCommandTest {

    @Mock // Mock for UserRepository
    private UserRepository userRepositoryMock;
    @InjectMocks // Testing command - mocks will be injected here instead real dependencies
    private CreateUserCommand command;

    @Test
    public void testExecute() {
        // Creates the object that command will pass to the repositorys
        final UserModel userModel = new UserModel();
        userModel.setName("John");
        userModel.setEmail("john@test.com");
        Integer expectedUserId = 2;
        // Check dao object from command in the anonymous class
        when(userRepositoryMock.create(argThat(new ArgumentMatcher<User>() {
            @Override
            public boolean matches(Object argument) {
                User user = (User) argument;
                return user.getId() == null && user.getName().equals(userModel.getName())
                        && user.getEmail().equals(userModel.getEmail()) && user.getCreateDate() != null;
            }
        }))).thenReturn(expectedUserId);
        Integer actualUserId = command.execute(userModel);
        assertEquals(expectedUserId, actualUserId);
    }
}
