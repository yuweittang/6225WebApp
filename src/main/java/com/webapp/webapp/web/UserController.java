package com.webapp.webapp.web;

import com.webapp.webapp.data.models.User;
import com.webapp.webapp.data.payloads.request.UserRequest;
import com.webapp.webapp.data.payloads.response.UserResponse;
import com.webapp.webapp.service.UserService;

import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/user")
    public ResponseEntity<UserResponse> addUser(@RequestBody UserRequest userRequest) {
        User newUser = userService.createUser(userRequest);
        UserResponse userResponse = new UserResponse();
        userResponse.setFirstName(newUser.getFirstName());
        userResponse.setLastName(newUser.getLastName());
        userResponse.setId(newUser.getId());
        userResponse.setUsername(newUser.getUsername());
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @GetMapping("/user/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> footballers = userService.getAllUser();
        return new ResponseEntity<>(footballers, HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Integer id) {
        User user = userService.getASingleUser(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/user/{id}")
    public Optional<User> updateUser(@PathVariable Integer id, @RequestBody UserRequest userRequest,
            @PathVariable LocalDateTime account_created, @PathVariable LocalDateTime account_updated) {
        return userService.updateUser(id, userRequest);

    }

    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable("id") Integer id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
