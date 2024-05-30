package com.lovecloud.invitationmanagement.domain;

import com.lovecloud.global.domain.CommonRootEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "invitation_image")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InvitationImage extends CommonRootEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invitation_image_id")
    private Long id;

    @Column(name = "image_name", nullable = false, length = 100)
    private String imageName;

    @Builder
    public InvitationImage(String imageName) {
        this.imageName = imageName;
    }

}
