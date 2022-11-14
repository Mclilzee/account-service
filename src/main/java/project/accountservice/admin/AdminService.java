package project.accountservice.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.accountservice.user.User;
import project.accountservice.user.UserRepository;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    UserRepository userRepository;

    public List<User> getUsers() {
        return userRepository.findAll();
    }
}
