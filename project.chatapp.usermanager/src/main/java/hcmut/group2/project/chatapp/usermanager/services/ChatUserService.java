package hcmut.group2.project.chatapp.usermanager.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import hcmut.group2.project.chatapp.usermanager.dto.ChatUserDto;
import hcmut.group2.project.chatapp.usermanager.dto.ChatUserRegistrationDto;
import hcmut.group2.project.chatapp.usermanager.dto.ChatUserUpdateDto;
import hcmut.group2.project.chatapp.usermanager.dto.SearchUserDto;
import hcmut.group2.project.chatapp.usermanager.entities.ChatUser;
import hcmut.group2.project.chatapp.usermanager.enums.UserActivity;
import hcmut.group2.project.chatapp.usermanager.enums.UserRole;
import hcmut.group2.project.chatapp.usermanager.enums.UserStatus;
import hcmut.group2.project.chatapp.usermanager.exceptions.UserDuplicatedException;
import hcmut.group2.project.chatapp.usermanager.exceptions.UserNotFoundException;
import hcmut.group2.project.chatapp.usermanager.repositories.ChatUserRepository;

@Service
public class ChatUserService implements UserDetailsService {
    @Autowired
    private ChatUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    private ChatUser updateUser(ChatUser currentUser, ChatUserUpdateDto userUpdateDto){
        currentUser.setUsername(userUpdateDto.getUsername());
        currentUser.setPassword(passwordEncoder.encode(userUpdateDto.getPassword()));
        currentUser.setEmailAddress(userUpdateDto.getEmailAddress());
        currentUser.setAvatarUrl(userUpdateDto.getAvatarUrl());
        currentUser.setFirstName(userUpdateDto.getFirstName());
        currentUser.setLastName(userUpdateDto.getLastName());
        currentUser.setBirthDate(userUpdateDto.getBirthDate());
        currentUser.setModificationDatetime(LocalDateTime.now());

        return userRepository.save(currentUser);
    }

    public ChatUserRegistrationDto registerUser(ChatUserRegistrationDto userRegistrationDto) throws UserDuplicatedException{
        if (userRepository.findByPhoneNumber(userRegistrationDto.getPhoneNumber()).isEmpty()) {
                LocalDateTime nowTime = LocalDateTime.now();
        
                ChatUser userEntity = ChatUser.builder().username(userRegistrationDto.getUsername())
                .password(passwordEncoder.encode(userRegistrationDto.getPassword()))
                .role(UserRole.USER)
                .status(UserStatus.ACTIVE)
                .activity(UserActivity.OFFLINE)
                .emailAddress(userRegistrationDto.getEmailAddress())
                .firstName(userRegistrationDto.getFirstName())
                .lastName(userRegistrationDto.getLastName())
                .birthDate(userRegistrationDto.getBirthDate())
                .creationDatetime(nowTime)
                .modificationDatetime(nowTime).build();

            return modelMapper.map(userRepository.save(userEntity), ChatUserRegistrationDto.class);
        }
        else {
            throw new UserDuplicatedException("User with Phone number " + userRegistrationDto.getPhoneNumber() + " is already registered.");
        }
    }

    // public ChatUser updateUserById(Integer id, ChatUser updatedUser) throws UserNotFoundException {
    //     ChatUser updatingUserEntity = getUserById(id);
    //     updatingUserEntity = updateUserEntity(updatingUserEntity, updatedUser);
    //     return userRepository.save(updatingUserEntity);
    // }
    
    public ChatUserDto updateUserByPhoneNumber(String phoneNumber, ChatUserUpdateDto userUpdateDto) throws UserNotFoundException {
        ChatUser existingUser = userRepository.findByPhoneNumber(phoneNumber)
                            .orElseThrow(() -> new UserNotFoundException("User with Phone number " + phoneNumber + " not found."));

        return modelMapper.map(updateUser(existingUser, userUpdateDto), ChatUserDto.class);
    }

    public List<SearchUserDto> searchUsers(String searchString) {
        return userRepository.findByPartialUsernameOrPhoneNumber(searchString).stream()
                    .map(user -> modelMapper.map(user, SearchUserDto.class))
                    .collect(Collectors.toList());
    }

    // public ChatUser getUserById(Integer id) throws UserNotFoundException{
    //     return userRepository.findById(id)
    //     .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found."));
    // }

    // public ChatUser getUserByUsername(String userName) throws UserNotFoundException{
    //     return userRepository.findByUsername(userName)
    //     .orElseThrow(() -> new UserNotFoundException("User with username " + userName + " not found."));
    // }

    public ChatUserDto getUserByPhoneNumber(String phoneNumber) throws UserNotFoundException{
        return Optional.of(modelMapper.map(userRepository.findByPhoneNumber(phoneNumber).get(), ChatUserDto.class)) 
                .orElseThrow(() -> new UserNotFoundException("User with Phone number " + phoneNumber + " not found."));
    }
    
    // public void deleteUserById(Integer id) throws UserNotFoundException{
    //     userRepository.delete(userRepository.findById(id).get());
    // }

    public void deleteUserByPhoneNumber(String phoneNumber) throws UserNotFoundException{
        userRepository.delete(userRepository.findByPhoneNumber(phoneNumber).get());
    }

    @Override
    public ChatUser loadUserByUsername(String username) throws UserNotFoundException {
        return userRepository.findByPhoneNumber(username)
                .orElseThrow(()-> new UsernameNotFoundException("User with Phone number " + username + " not found."));
    }
}
