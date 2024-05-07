package hcmut.group2.project.chatapp.usermanager.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
// import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import hcmut.group2.project.chatapp.usermanager.dto.FriendDto;
import hcmut.group2.project.chatapp.usermanager.dto.FriendRequestDto;
import hcmut.group2.project.chatapp.usermanager.dto.FriendRequestUpdateDto;
import hcmut.group2.project.chatapp.usermanager.dto.FriendshipDto;
import hcmut.group2.project.chatapp.usermanager.dto.PendingFriendDto;
import hcmut.group2.project.chatapp.usermanager.enums.FriendshipStatus;
import hcmut.group2.project.chatapp.usermanager.services.FriendshipService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/friends")
public class FriendshipController implements JWTAuthController {
	@Autowired
	private FriendshipService friendService;

    @PostMapping
    public ResponseEntity<FriendshipDto> sendFriendRequest(@Valid @RequestBody FriendRequestDto requestDto) {
        FriendshipDto friendshipDto = friendService.sendFriendRequest(requestDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{senderPhoneNumber}/{receiverPhoneNumber}")
                .buildAndExpand(requestDto.getSenderPhoneNumber(), requestDto.getReceiverPhoneNumber())
                .toUri();

        if (friendshipDto.getStatus() == FriendshipStatus.PENDING) {
            return ResponseEntity.created(location).body(friendshipDto);
        }
        else { // Assuming all other cases will always be ACCEPTED
            return ResponseEntity.ok(friendshipDto);
        }
    }

    @PutMapping("/{senderPhoneNumber}/{receiverPhoneNumber}")
    public ResponseEntity<FriendshipDto> updateFriendRequestStatus(@PathVariable String senderPhoneNumber,
                                                                @PathVariable String receiverPhoneNumber,
                                                                @Valid @RequestBody FriendRequestUpdateDto requestUpdateDto){
        return ResponseEntity.ok(friendService.updateFriendRequestStatus(senderPhoneNumber, receiverPhoneNumber, requestUpdateDto));
    }

    @GetMapping("/pending/{currentUserPhoneNumber}")
    public ResponseEntity<List<PendingFriendDto>> getAllPendingPhoneNumbers(@PathVariable String currentUserPhoneNumber) {
        return ResponseEntity.ok(friendService.getAllPendingPhoneNumbers(currentUserPhoneNumber));
    }

    @GetMapping("/accepted/{currentUserPhoneNumber}")
    public ResponseEntity<List<FriendDto>> getAllFriendPhoneNumbers(@PathVariable String currentUserPhoneNumber) {
        return ResponseEntity.ok(friendService.getAllFriendPhoneNumbers(currentUserPhoneNumber));
    }

    // @GetMapping("/{id}")
    // public ResponseEntity<ChatUserDto> getUserById(@PathVariable String id){
    //     return ResponseEntity.ok(ChatUserMapper.toDto(userService.getUserById(Integer.parseInt(id))));
    // }
}
