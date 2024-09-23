package com.lovecloud.ordermanagement.presentation;

import com.lovecloud.global.usermanager.SecurityUser;
import com.lovecloud.ordermanagement.application.DeliveryAddressService;
import com.lovecloud.ordermanagement.presentation.request.CreateDeliveryAddressRequest;
import com.lovecloud.ordermanagement.presentation.request.UpdateDeliveryAddressRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/delivery-addresses")
public class DeliveryAddressController {

    private final DeliveryAddressService deliveryAddressService;

    //내 배송지 저장
    @PostMapping
    @PreAuthorize("hasRole('ROLE_WEDDING_USER')")
    public ResponseEntity<Long> createDeliveryAddress(
            @AuthenticationPrincipal SecurityUser securityUser,
            @Valid @RequestBody CreateDeliveryAddressRequest request
    ) {
        final Long userId = securityUser.user().getId();
        Long deliveryAddressId = deliveryAddressService.createDeliveryAddress(request.toCommand(userId));
        return ResponseEntity.created(URI.create("/delivery-addresses/" + deliveryAddressId)).build();
    }

    //내 배송지 수정
    @PutMapping("/{deliveryAddressId}")
    @PreAuthorize("hasRole('ROLE_WEDDING_USER')")
    public ResponseEntity<Long> updateDeliveryAddress(
            @AuthenticationPrincipal SecurityUser securityUser,
            @PathVariable Long deliveryAddressId,
            @Valid @RequestBody UpdateDeliveryAddressRequest request
    ) {
        final Long userId = securityUser.user().getId();
        Long id = deliveryAddressService.updateDeliveryAddress(request.toCommand(userId, deliveryAddressId));
        return ResponseEntity.ok().build();
    }

    //내 배송지 삭제
    @DeleteMapping("/{deliveryAddressId}")
    @PreAuthorize("hasRole('ROLE_WEDDING_USER')")
    public ResponseEntity<Void> deleteDeliveryAddress(
            @AuthenticationPrincipal SecurityUser securityUser,
            @PathVariable Long deliveryAddressId
    ) {
        final Long userId = securityUser.user().getId();
        deliveryAddressService.deleteDeliveryAddress(userId, deliveryAddressId);
        return ResponseEntity.noContent().build();
    }

    //기본 배송지 설정
    @PutMapping("/{deliveryAddressId}/default")
    @PreAuthorize("hasRole('ROLE_WEDDING_USER')")
    public ResponseEntity<Void> setDefaultDeliveryAddress(
            @AuthenticationPrincipal SecurityUser securityUser,
            @PathVariable Long deliveryAddressId
    ) {
        final Long userId = securityUser.user().getId();
        deliveryAddressService.setDefaultDeliveryAddress(userId, deliveryAddressId);
        return ResponseEntity.ok().build();
    }
}
