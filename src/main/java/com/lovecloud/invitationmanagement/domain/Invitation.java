package com.lovecloud.invitationmanagement.domain;

import com.lovecloud.global.domain.CommonRootEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "invitation")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Invitation extends CommonRootEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invitation_id")
    private Long id;

    @Column(name = "place", nullable = false, length = 100)
    private String place;

    @Column(name = "wedding_date_time", nullable = false)
    private LocalDateTime weddingDateTime;

    @Column(name = "content", nullable = false, length = 300)
    private String content;

    @Column(name = "image_url", nullable = false, length = 100)
    private String imageUrl;

    @Builder
    public Invitation(String place, LocalDateTime weddingDateTime, String content,
            String imageUrl) {
        this.place = place;
        this.weddingDateTime = weddingDateTime;
        this.content = content;
        this.imageUrl = imageUrl;
    }
}
