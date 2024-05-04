package hcmut.group2.project.chatapp;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import hcmut.group2.project.chatapp.usermanager.controllers.ChatUserController;

@SpringBootTest
class ApplicationTests {
	@Autowired
	private ChatUserController userController;

	@Test
	void contextLoads() {
		assertThat(userController).isNotNull();
	}

}
