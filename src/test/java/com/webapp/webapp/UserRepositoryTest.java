package com.webapp.webapp;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.webapp.webapp.data.models.User;
import com.webapp.webapp.data.repository.UserRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest

class UserRepositoryTest {

	@Autowired
	UserRepository userRepository;

	@Test
	final void testFindUserByFirstName() {

		User userEntity2 = new User();
		userEntity2.setFirstName("Sergey");
		userEntity2.setLastName("Kargopolov");
		userEntity2.setId(999);
		userEntity2.setUsername("test9@test.com");
		userRepository.save(userEntity2);
		String first_name = "Sergey";
		String username = "test9@test.com";
		Optional<User> users = userRepository.findByUsername(username);
		assertNotNull(users);
		System.out.println(users.isEmpty());
		assertTrue(users.get().getFirstName().equals(first_name));
	}

}