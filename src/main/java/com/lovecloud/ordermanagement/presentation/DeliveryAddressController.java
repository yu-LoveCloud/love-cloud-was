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
}
