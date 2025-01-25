package com.user.user.service;

import com.user.user.dto.UserDTO;
import com.user.user.dto.UserPublicDTO;
import com.user.user.exception.UserAlreadyExistsException;
import com.user.user.exception.UserNotFoundException;
import com.user.user.mapper.UserMapper;
import com.user.user.repository.UserRepository;
import com.user.user.repository.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final KafkaTemplate<String, String> kafkaTemplate;

  @Autowired
  public UserService(UserRepository userRepository, UserMapper userMapper, KafkaTemplate<String, String> kafkaTemplate) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
    this.kafkaTemplate = kafkaTemplate;
  }

  // CUD
  public void createUser(UserDTO userDTO) throws UserAlreadyExistsException {
    if (userRepository.getUserByEmail(userDTO.email()) != null || userRepository.getUserById(userDTO.id()) != null) {
      throw new UserAlreadyExistsException("User already exists");
    }
    
    User user = userMapper.toUser(userDTO);
    System.out.println(user.toString());
    userRepository.save(user);
  }

  @Transactional
  public void updateUser(UserDTO userDTO) throws UserNotFoundException {
    if (userRepository.getUserById(userDTO.id()) == null || userRepository.getUserById(userDTO.id()) == null) {
      throw new UserNotFoundException("User not found");
    }
    User user = userMapper.toUser(userDTO);
    userRepository.save(user);
  }

  @Transactional
  public void deleteUser(String id) throws UserNotFoundException {
    User user = userRepository.getUserById(id);
    if (user == null) {
      throw new UserNotFoundException("User not found");
    }
    kafkaTemplate.send("user-delete", id);
    userRepository.delete(user);
  }

  // R
  public UserDTO getUserById(String id) throws UserNotFoundException {
    User user = userRepository.getUserById(id);
    if (user == null) {
      throw new UserNotFoundException("User not found");
    }
    return userMapper.toUserDTO(user);
  }

  public UserDTO getUserByEmail(String email) throws UserNotFoundException {
    User user = userRepository.getUserByEmail(email);
    if (user == null) {
      throw new UserNotFoundException("User not found");
    }
    return userMapper.toUserDTO(user);
  }

  // R without password
  public UserPublicDTO getUserPublicById(String id) throws UserNotFoundException {
    User user = userRepository.getUserById(id);
    if (user == null) {
      throw new UserNotFoundException("User not found");
    }
    return userMapper.toUserPublicDTO(user);
  }

  public UserPublicDTO getUserPublicByEmail(String email) throws UserNotFoundException {
    User user = userRepository.getUserByEmail(email);
    if (user == null) {
      throw new UserNotFoundException("User not found");
    }
    return userMapper.toUserPublicDTO(user);
  }
}
