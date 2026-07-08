package com.transacao.estudoBanco.domain.repository;

import com.transacao.estudoBanco.domain.dto.UserDTO;
import com.transacao.estudoBanco.domain.user.User;
import com.transacao.estudoBanco.domain.user.UserType;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("Should Get User Successfully from DB")
    void findByIdSucess() {
        UserDTO dto = new UserDTO("Kethlyn", "Justo", "34857603829",
                BigDecimal.valueOf(100.0), "keth@keth.com", "kkk", UserType.COMMUM);
        User user = createUser(dto);
        User found = entityManager.find(User.class, user.getId());

        assertThat(found).isNotNull();
        assertThat(found.getFirstName()).isEqualTo("Kethlyn");
    }

    @Test
    @DisplayName("Should Not Get User from DB")
    void findByIdFaial() {
        UserDTO dto = new UserDTO("Kethlyn", "Justo", "34857603829",
                BigDecimal.valueOf(100.0), "keth@keth.com", "kkk", UserType.COMMUM);
        User user = createUser(dto);
        User found = entityManager.find(User.class, user.getId());

        assertThat(found).isNotNull();
        assertThat(found.getFirstName()).isEqualTo("Kethlyn");
    }

    @Test
    void existsByDocument() {
    }

    private User createUser(UserDTO user){
        User newUser = User.builder()
                .firstName(user.getName())
                .lastName(user.getLastName())
                .document(user.getDocument())
                .email(user.getEmail())
                .password(user.getPassword())
                .userType(user.getUserType())
                .build();
        this.entityManager.persist(newUser);
        return newUser;
    }
}