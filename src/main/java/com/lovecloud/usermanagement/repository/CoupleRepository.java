package com.lovecloud.usermanagement.repository;

import com.lovecloud.usermanagement.domain.Couple;
import com.lovecloud.usermanagement.exeption.NotFoundCoupleException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CoupleRepository extends JpaRepository<Couple, Long> {

    /**
     * 작성자: 염동환
     * Bride 또는 Groom의 user_id가 userId인 객체를 조회한다.
     * @param userId 유저 id
     * @return Couple 커플
     */
    @Query("SELECT c FROM Couple c WHERE c.bride.id = :userId OR c.groom.id = :userId")
    Optional<Couple> findByUserId(
            @Param("userId") Long userId);

    default Couple findByUserIdOrThrow(Long userId) {
        return findByUserId(userId)
                .orElseThrow(NotFoundCoupleException::new);
    }
}
