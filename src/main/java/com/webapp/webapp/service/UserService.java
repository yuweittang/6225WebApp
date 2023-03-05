package com.webapp.webapp.service;

import com.webapp.webapp.data.models.User;
import com.webapp.webapp.data.payloads.request.UserRequest;
import com.webapp.webapp.data.payloads.response.UserResponse;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface UserService extends UserDetailsService {
    User createUser(UserRequest userRequest);

    Optional<User> updateUser(Integer userId, UserRequest userRequest);

    void deleteUser(Integer userId);

    User getASingleUser(Integer userId);

    User getUser(String username);

    List<User> getAllUser();
}
