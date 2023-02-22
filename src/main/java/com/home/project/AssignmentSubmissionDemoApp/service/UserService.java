package com.home.project.AssignmentSubmissionDemoApp.service;

import com.home.project.AssignmentSubmissionDemoApp.entity.User;
import com.home.project.AssignmentSubmissionDemoApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    public Optional<User> findUserByUsername(String username) {
        return userRepo.findByUsername(username);
    }

}
