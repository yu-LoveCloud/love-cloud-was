package com.lovecloud.invitationmanagement.application;

import com.lovecloud.invitationmanagement.domain.repository.InvitationImageRepository;
import com.lovecloud.invitationmanagement.domain.repository.InvitationRepository;
import com.lovecloud.productmanagement.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest
class InvitationCreateServiceTest {
    @Autowired
    private InvitationCreateService invitationCreateService;

    @Autowired
    private InvitationRepository invitationRepository;

    @Autowired
    private InvitationImageRepository invitationImageRepository;

    private Category category;

    @BeforeEach
    void setUp() {
        category = new Category("에어컨");
        category = categoryRepository.save(category);
    }

    }
}