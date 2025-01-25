package com.user.user.controller;

import com.user.user.dto.UserDTO;
import com.user.user.dto.UserPublicDTO;
import com.user.user.exception.UserAlreadyExistsException;
import com.user.user.exception.UserNotFoundException;
import com.user.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @PostMapping("/signup")
  public ResponseEntity<String> signup(
          @RequestBody UserDTO userDTO
  ) throws UserAlreadyExistsException {
    System.out.println(userDTO);
    userService.createUser(userDTO);
    return ResponseEntity.ok("User successfully signed up");
  }

  @GetMapping("/users")
  public ResponseEntity<UserPublicDTO> getUser(@RequestParam(required = false) String email, @RequestParam(required = false) String id) throws UserNotFoundException {
    if (id != null) {
      return ResponseEntity.ok(userService.getUserPublicById(id));
    } else {
      return ResponseEntity.ok(userService.getUserPublicByEmail(email));
    }
  }
}
