package hcmut.group2.project.chatapp.usermanager.controllers;

import hcmut.group2.project.chatapp.usermanager.dto.ChatUserDto;
import hcmut.group2.project.chatapp.usermanager.dto.ChatUserUpdateDto;
import hcmut.group2.project.chatapp.usermanager.services.ChatUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class ChatUserController implements JWTAuthController {
	@Autowired
	private ChatUserService userService;

    // @PostMapping
    // public ResponseEntity<ChatUserRegistrationDto> registerUser(@RequestBody @Valid ChatUserRegistrationDto userRegistrationDto) {
    //     ChatUserRegistrationDto registeredUserDto = userService.registerUser(userRegistrationDto);

    //     URI location = ServletUriComponentsBuilder
    //             .fromCurrentRequest()
    //             .path("/{phoneNumber}")
    //             .buildAndExpand(registeredUserDto.getPhoneNumber())
    //             .toUri();

    //     return ResponseEntity.created(location).body(registeredUserDto);
    // }

    // @PutMapping("/{id}")
    // public ResponseEntity<ChatUserDto> updateUserById(@PathVariable String id, ChatUserDto putUserDto){
    //     return ResponseEntity.ok(ChatUserMapper.toDto(userService.updateUserById(Integer.parseInt(id), ChatUserMapper.toEntity(putUserDto))));
    // }

    @PutMapping("/{phoneNumber}")
    public ResponseEntity<ChatUserDto> updateUserByPhoneNumber(@PathVariable String phoneNumber, ChatUserUpdateDto userUpdateDto){
        return ResponseEntity.ok(userService.updateUserByPhoneNumber(phoneNumber, userUpdateDto));
    }

    @GetMapping
    public ResponseEntity<List<ChatUserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // @GetMapping("/{id}")
    // public ResponseEntity<ChatUserDto> getUserById(@PathVariable String id){
    //     return ResponseEntity.ok(ChatUserMapper.toDto(userService.getUserById(Integer.parseInt(id))));
    // }

    @GetMapping("/{phoneNumber}")
    public ResponseEntity<ChatUserDto> getUserByPhoneNumber(@PathVariable String phoneNumber){
        return ResponseEntity.ok(userService.getUserByPhoneNumber(phoneNumber));
    }

    // @DeleteMapping("/{id}")
    // public ResponseEntity<Void> deleteUserById(@PathVariable String id) {
    //     userService.deleteUserById(Integer.parseInt(id));
    //     return ResponseEntity.ok().build();
    // }

    @DeleteMapping("/{phoneNumber}")
    public ResponseEntity<Void> deleteUserByPhoneNumber(@PathVariable String phoneNumber) {
        userService.deleteUserByPhoneNumber(phoneNumber);
        return ResponseEntity.ok().build();
    }
}
