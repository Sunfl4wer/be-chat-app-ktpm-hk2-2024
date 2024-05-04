package hcmut.group2.project.chatapp;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import hcmut.group2.project.chatapp.usermanager.controllers.ChatUserController;
// import hcmut.group2.project.chatapp.dto.ChatUserDto;
import hcmut.group2.project.chatapp.usermanager.dto.ChatUserRegistrationDto;
import hcmut.group2.project.chatapp.usermanager.entities.ChatUser;
import hcmut.group2.project.chatapp.usermanager.enums.UserActivity;
import hcmut.group2.project.chatapp.usermanager.enums.UserRole;
import hcmut.group2.project.chatapp.usermanager.enums.UserStatus;
import hcmut.group2.project.chatapp.usermanager.exceptions.UserDuplicatedException;
// import hcmut.group2.project.chatapp.mapper.ChatUserMapper;
import hcmut.group2.project.chatapp.usermanager.services.ChatUserService;

// @AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(ChatUserController.class)
public class UserControllerTests {
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private ChatUserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ModelMapper modelMapper;

    private final LocalDateTime nowTime = LocalDateTime.now();
    
    ChatUser USER_1 = ChatUser.builder().phoneNumber("01234567891")
        .username("unitTest01")
        .password("unitTestPassword01")
        .role(UserRole.USER)
        .status(UserStatus.ACTIVE)
        .activity(UserActivity.OFFLINE)
        .creationDatetime(nowTime)
        .modificationDatetime(nowTime)
        .build();
    ChatUser USER_2 = ChatUser.builder().phoneNumber("01234567892")
        .username("unitTest02")
        .password("unitTestPassword02")
        .role(UserRole.USER)
        .status(UserStatus.ACTIVE)
        .activity(UserActivity.OFFLINE)
        .creationDatetime(nowTime)
        .modificationDatetime(nowTime)
        .build();
    ChatUser USER_3 = ChatUser.builder().phoneNumber("01234567893")
        .username("unitTest03")
        .password("unitTestPassword03")
        .role(UserRole.ADMIN)
        .status(UserStatus.ACTIVE)
        .activity(UserActivity.OFFLINE)
        .creationDatetime(nowTime)
        .modificationDatetime(nowTime)
        .build();

    @Test
    public void registerUser_success() throws Exception {
        Mockito.when(userService.registerUser(Mockito.any(ChatUserRegistrationDto.class))).thenReturn(modelMapper.map(USER_1, ChatUserRegistrationDto.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(modelMapper.map(USER_1, ChatUserRegistrationDto.class))))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void registerUser_alreadyRegistered() throws Exception {
        Mockito.when(userService.registerUser(Mockito.any(ChatUserRegistrationDto.class))).thenThrow(new UserDuplicatedException("User with Phone number " + USER_1.getPhoneNumber() + " is already registered."));

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(modelMapper.map(USER_1, ChatUserRegistrationDto.class))))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }
}
