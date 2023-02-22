package com.home.project.AssignmentSubmissionDemoApp.util;

import com.home.project.AssignmentSubmissionDemoApp.entity.User;

public class AuthorityUtil {

    public static Boolean hasRole(String role, User user) {
        return user.getAuthorities().stream()
                .filter(auth -> auth.getAuthority().equals(role))
                .count() > 0;
    }
}
