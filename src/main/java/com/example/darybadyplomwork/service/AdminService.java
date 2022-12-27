package com.example.darybadyplomwork.service;

import com.example.darybadyplomwork.entity.User;
import com.example.darybadyplomwork.entity.enums.UserRole;
import com.example.darybadyplomwork.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    private UserRepository userRepository;
    public Page<User> getUserPage (Pageable pageable){
        return userRepository.findAll(pageable);
    }
    public void updateUser(String action,long id){
        User user = userRepository.findAllById(List.of(id)).get(0);
        if(action.equals("delete")){
            userRepository.delete(user);
        }
        else if(action.equals("admin")){
            user.setRole(UserRole.ADMIN);
            userRepository.save(user);
        }
        else if(action.equals("manager")){
            user.setRole(UserRole.MANAGER);
            userRepository.save(user);
        }
        else {
            user.setRole(UserRole.BLOCKED);
            userRepository.save(user);
        }

    }
}
