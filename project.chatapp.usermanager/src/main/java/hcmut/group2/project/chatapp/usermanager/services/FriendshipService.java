package hcmut.group2.project.chatapp.usermanager.services;

import java.util.List;
import java.util.Collections;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hcmut.group2.project.chatapp.usermanager.dto.FriendPhoneNumberDto;
import hcmut.group2.project.chatapp.usermanager.dto.FriendRequestDto;
import hcmut.group2.project.chatapp.usermanager.dto.FriendRequestUpdateDto;
import hcmut.group2.project.chatapp.usermanager.dto.FriendshipDto;
import hcmut.group2.project.chatapp.usermanager.dto.PendingPhoneNumberDto;
import hcmut.group2.project.chatapp.usermanager.entities.ChatUser;
import hcmut.group2.project.chatapp.usermanager.entities.Friendship;
import hcmut.group2.project.chatapp.usermanager.entities.keys.FriendshipId;
import hcmut.group2.project.chatapp.usermanager.enums.FriendshipStatus;
import hcmut.group2.project.chatapp.usermanager.exceptions.FriendshipNotFoundException;
import hcmut.group2.project.chatapp.usermanager.exceptions.UserNotFoundException;
import hcmut.group2.project.chatapp.usermanager.repositories.ChatUserRepository;
import hcmut.group2.project.chatapp.usermanager.repositories.FriendshipRepository;
// import lombok.RequiredArgsConstructor;

@Service
// @RequiredArgsConstructor
public class FriendshipService {
    @Autowired
    private ChatUserRepository userRepo;

    @Autowired
    private FriendshipRepository friendRepo;

    @Autowired
    private ModelMapper modelMapper;

    public FriendshipDto sendFriendRequest(FriendRequestDto requestDto) throws UserNotFoundException{
        ChatUser sendUser = userRepo.findByPhoneNumber(requestDto.getSenderPhoneNumber())
                            .orElseThrow(() -> new UserNotFoundException("Friend Request sender with Phone number " + requestDto.getSenderPhoneNumber() + " not found."));

        ChatUser receiveUser = userRepo.findByPhoneNumber(requestDto.getReceiverPhoneNumber())
                            .orElseThrow(() -> new UserNotFoundException("Friend Request receiver with Phone number " + requestDto.getReceiverPhoneNumber() + " not found."));

        Friendship savingRequest = null;
        Friendship requestFromUser = friendRepo.findById(new FriendshipId(sendUser.getId(), receiveUser.getId())).get();
        Friendship requestFromOther = friendRepo.findById(new FriendshipId(receiveUser.getId(), sendUser.getId())).get();

        if (requestFromUser != null && requestFromUser.getStatus() != FriendshipStatus.BLOCKED) {
            requestFromOther.setStatus(FriendshipStatus.PENDING);
            savingRequest = requestFromUser;
        }
        else if (requestFromOther != null) {
            requestFromOther.setStatus(FriendshipStatus.ACCEPTED);
            savingRequest = requestFromOther;
        }
        else{
            savingRequest = new Friendship(sendUser, receiveUser);
        }

        return modelMapper.map(friendRepo.save(savingRequest), FriendshipDto.class);
    }

    public FriendshipDto updateFriendRequestStatus(String senderPhoneNumber, String receiverPhoneNumber, FriendRequestUpdateDto requestUpdateDto)
                                                    throws UserNotFoundException, FriendshipNotFoundException {
        ChatUser sendUser = userRepo.findByPhoneNumber(senderPhoneNumber)
                            .orElseThrow(() -> new UserNotFoundException("Friend Request sender with Phone number " + senderPhoneNumber + " not found."));

        ChatUser receiveUser = userRepo.findByPhoneNumber(receiverPhoneNumber)
                            .orElseThrow(() -> new UserNotFoundException("Friend Request receiver with Phone number " + receiverPhoneNumber + " not found."));

        Friendship existingRequest = friendRepo.findById(new FriendshipId(sendUser.getId(), receiveUser.getId()))
                            .orElseThrow(() -> new FriendshipNotFoundException("Friendship of " + senderPhoneNumber + " and " + receiverPhoneNumber + " not found."));

        existingRequest.setStatus(requestUpdateDto.getStatus());
        return modelMapper.map(friendRepo.save(existingRequest), FriendshipDto.class);
    }

    public List<PendingPhoneNumberDto> getAllPendingPhoneNumbers(String userPhoneNumber) throws UserNotFoundException {
        ChatUser currentUser = userRepo.findByPhoneNumber(userPhoneNumber)
                            .orElseThrow(() -> new UserNotFoundException("User with Phone number " + userPhoneNumber + " not found."));

        List<Friendship> pendingFriendships = friendRepo.findAllByUserIdAndStatus(currentUser.getId(), FriendshipStatus.PENDING);
        List<PendingPhoneNumberDto> pendingPhoneNumberDtos = Collections.emptyList();

        for (Friendship friendship : pendingFriendships) {
            if (friendship.getFriend().getPhoneNumber() == userPhoneNumber) {
                pendingPhoneNumberDtos.add(modelMapper.map(friendship.getUser().getPhoneNumber(), PendingPhoneNumberDto.class));
            }
        }

        return pendingPhoneNumberDtos;
    }

    public List<FriendPhoneNumberDto> getAllFriendPhoneNumbers(String userPhoneNumber) throws UserNotFoundException {
        ChatUser currentUser = userRepo.findByPhoneNumber(userPhoneNumber)
                            .orElseThrow(() -> new UserNotFoundException("User with Phone number " + userPhoneNumber + " not found."));

        List<Friendship> acceptedFriendships = friendRepo.findAllByUserIdAndStatus(currentUser.getId(), FriendshipStatus.ACCEPTED);
        List<FriendPhoneNumberDto> friendPhoneNumberDtos = Collections.emptyList();

        for (Friendship friendship : acceptedFriendships) {
            if (friendship.getUser().getPhoneNumber() == userPhoneNumber) {
                friendPhoneNumberDtos.add(modelMapper.map(friendship.getFriend().getPhoneNumber(), FriendPhoneNumberDto.class));
            }
            else if (friendship.getFriend().getPhoneNumber() == userPhoneNumber) {
                friendPhoneNumberDtos.add(modelMapper.map(friendship.getUser().getPhoneNumber(), FriendPhoneNumberDto.class));
            }
        }

        return friendPhoneNumberDtos;
    }

    // public List<FriendshipDto> getAllFriendshipsByUserId(Integer userId){
    //     return friendRepo.findAllByUserId(userId);
    // }

    // public List<FriendshipDto> getAllFriendshipsByUserIdAndStatus(Integer userId, FriendshipStatus status){
    //     return friendRepo.findAllByUserIdAndStatus(userId, status);
    // }
}
