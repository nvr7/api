package com.example.springbootrestapiv2.controller;

import com.example.springbootrestapiv2.exception.*;
import com.example.springbootrestapiv2.model.User;
import com.example.springbootrestapiv2.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v2")
public class userController {

    @Autowired
    private UserRepository userRepository;

    /**
     * Get all user list.
     *
     * @return the list
     */

    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    /**
     * Get users by id.
     * @param userId the user id
     * @return the user by id
     * @throws ResourceNotFoundException the resource not found exception
     */

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUsersById(@PathVariable(value = "id") Long userId)
            throws ResourceNotFoundException {
        User user =
                userRepository
                        .findById(userId)
                        .orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + userId));
        return ResponseEntity.ok().body(user);
    }

    /**
     * Create user user.
     *
     * @param user the user
     * @return the user
     */

    @PostMapping("/user")
    public User createUser(@Valid @RequestBody User user){
        return userRepository.save(user);
    }

    /**
     * Update user response entity.
     *
     *
     * @param userId the user id
     * @param userDetails the user details
     * @return the response entity
     * @throws ResourceNotFoundException the resource not found exception
     */

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable(value = "id") Long userId, @Valid @RequestBody User userDetails)
            throws ResourceNotFoundException{

        User user =
                userRepository
                        .findById(userId)
                        .orElseThrow(()-> new ResourceNotFoundException("User not found on :: " + userId));
        user.setEmail(userDetails.getEmail());
        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        final User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Delete user map.
     *
     * @param userId the user id
     * @return the map
     * @thorws Exception the exception
     */
    @DeleteMapping("/user/{id}")
    public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long userId) throws Exception{
        User user =
                userRepository
                        .findById(userId)
                        .orElseThrow(()-> new ResourceNotFoundException("User not found on :: " + userId));

        userRepository.delete(user);
        Map<String, Boolean> response = new HashMap<>();
        return response;
    }
}
