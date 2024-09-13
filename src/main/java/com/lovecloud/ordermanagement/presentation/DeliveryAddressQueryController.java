package com.lovecloud.ordermanagement.presentation;

import com.lovecloud.global.usermanager.SecurityUser;
import com.lovecloud.ordermanagement.application.DeliveryAddressQueryService;
import com.lovecloud.ordermanagement.query.response.DeliveryAddressResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/delivery-addresses")
@RequiredArgsConstructor
public class DeliveryAddressQueryController {
    private final DeliveryAddressQueryService deliveryAddressQueryService;

    // 내 배송지 목록 조회
    @GetMapping
    @PreAuthorize("hasRole('ROLE_WEDDING_USER')")
    public ResponseEntity<List<DeliveryAddressResponse>> listDeliveryAddresses(@AuthenticationPrincipal SecurityUser securityUser) {
        List<DeliveryAddressResponse> deliveryAddresses = deliveryAddressQueryService.findAllDeliveryAddressesByUserId(securityUser.user().getId());
        return ResponseEntity.ok(deliveryAddresses);
    }

    // 배송지 상세 조회
    @GetMapping("/{deliveryAddressId}")
    @PreAuthorize("hasRole('ROLE_WEDDING_USER')")
    public ResponseEntity<DeliveryAddressResponse> detailDeliveryAddress(@AuthenticationPrincipal SecurityUser securityUser, @PathVariable Long deliveryAddressId) {
        DeliveryAddressResponse deliveryAddress = deliveryAddressQueryService.findDeliveryAddressByDeliveryAddressIdAndUserId(securityUser.user().getId(), deliveryAddressId);
        return ResponseEntity.ok(deliveryAddress);
    }
    // 기본 배송지 조회
    @GetMapping("/default")
    @PreAuthorize("hasRole('ROLE_WEDDING_USER')")
    public ResponseEntity<DeliveryAddressResponse> defaultDeliveryAddress(@AuthenticationPrincipal SecurityUser securityUser) {
        DeliveryAddressResponse deliveryAddress = deliveryAddressQueryService.findDefaultDeliveryAddressByUserId(securityUser.user().getId());
        return ResponseEntity.ok(deliveryAddress);
    }
}
