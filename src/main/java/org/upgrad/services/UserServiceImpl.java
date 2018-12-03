package org.upgrad.services;

import org.springframework.stereotype.Service;
import org.upgrad.models.User;
import org.upgrad.models.UserAuthToken;
import org.upgrad.repositories.UserAuthTokenRepository;
import org.upgrad.repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String findUserPassword(String contactNumber) {
        return userRepository.findUserPassword(contactNumber);
    }

    @Override
    public User findUser(String contactNumber) {
        return userRepository.findUser(contactNumber);
    }

    @Override
    public void addUser(String firstName, String lastName, String email, String contactNumber, String password) {
        User user = new User(firstName, lastName, email, contactNumber, password);
        userRepository.save(user);
    }
}
