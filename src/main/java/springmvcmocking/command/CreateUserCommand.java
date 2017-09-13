package springmvcmocking.command;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import springmvcmocking.model.UserModel;
import springmvcmocking.persistence.User;
import springmvcmocking.persistence.UserRepository;

@Component
public class CreateUserCommand {

    @Autowired
    private UserRepository userRepository;

    public Integer execute(UserModel userModel) {
        User user = new User();
        user.setName(userModel.getName());
        user.setEmail(userModel.getEmail());
        user.setCreateDate(new Date());
        return userRepository.create(user);
    }
}
