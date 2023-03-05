package com.webapp.webapp.service;

import com.webapp.webapp.data.models.User;
import com.webapp.webapp.data.payloads.request.UserRequest;
import com.webapp.webapp.data.repository.UserRepository;
import com.webapp.webapp.exception.ResourceNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User createUser(UserRequest userRequest) {
        Optional<User> user = userRepository.findByUsername(userRequest.getUsername());
        if (!user.isEmpty()) {
            throw new ResourceNotFoundException("the username has existed");
        }
        User newUser = new User();
        newUser.setFirstName(userRequest.getFirstName());
        newUser.setLastname(userRequest.getLastName());
        newUser.setUsername(userRequest.getUsername());
        newUser.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()));
        userRepository.save(newUser);
        return newUser;
    }

    @Override
    public Optional<User> updateUser(Integer userId, UserRequest userRequest)
            throws ResourceNotFoundException {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("User", "id", userId);
        } else
            user.get().setFirstName(userRequest.getFirstName());
        user.get().setLastname(userRequest.getLastName());
        user.get().setUsername(userRequest.getUsername());
        // user.get().setPassword(userRequest.getPassword());
        userRepository.save(user.get());
        return user;
    }

    @Override
    public User getASingleUser(Integer userId) throws ResourceNotFoundException {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(Integer userId) throws ResourceNotFoundException {
        if (userRepository.getById(userId).getId().equals(userId)) {
            userRepository.deleteById(userId);
        } else
            throw new ResourceNotFoundException("User", "id", userId);
    }

    @Override
    public User getUser(String username) {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty())
            throw new UsernameNotFoundException(username);

        User returnValue = new User();
        BeanUtils.copyProperties(user, returnValue);

        return returnValue;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty())
            throw new UsernameNotFoundException(username);
        org.springframework.security.core.userdetails.User userDetail = new org.springframework.security.core.userdetails.User(
                user.get().getUsername(), user.get().getPassword(), new ArrayList<>());
        return userDetail;
    }

}
