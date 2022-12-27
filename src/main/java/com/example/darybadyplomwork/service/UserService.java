package com.example.darybadyplomwork.service;


import com.example.darybadyplomwork.entity.User;
import com.example.darybadyplomwork.entity.enums.UserRole;
import com.example.darybadyplomwork.mail.Sender;
import com.example.darybadyplomwork.repos.UserRepository;
import com.example.darybadyplomwork.utils.PasswordGen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.random.RandomGenerator;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Sender sender;
    public boolean isEmailExist(String email){
        return userRepository.findUserByEmail(email).isPresent();
    }
    public User login(String email, String password){
        if(!isEmailExist(email)){
            throw new IllegalArgumentException();
        }
        User user = userRepository.findUserByEmail(email).get();
        if(user.getPassword().equals(password)){
            return user;
        }
        else throw new IllegalArgumentException();
    }

    public void registerUser(String email){
        String sufNum = ""+new Random().nextInt()*100000;
        if(isEmailExist(email))
            throw new IllegalArgumentException("EmailInUse");
        String password = PasswordGen.generatePass();
        sender.sendLetterAboutRegistration(email,password);
        User user = new User();
        user.setRole(UserRole.USER);
        user.setEmail(email);
        user.setPassword(password);
        user.setFirstName("user"+sufNum);
        userRepository.save(user);
    }
}
