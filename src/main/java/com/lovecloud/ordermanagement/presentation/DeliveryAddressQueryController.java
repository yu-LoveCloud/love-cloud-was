package com.lovecloud.ordermanagement.presentation;

import com.lovecloud.global.usermanager.SecurityUser;
import com.lovecloud.ordermanagement.application.DeliveryAddressQueryService;
import com.lovecloud.ordermanagement.query.response.DeliveryAddressResponse;
import lombok.RequiredArgsConstructor;
import okhttp3.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/delivery-addresses")
@RequiredArgsConstructor
public class DeliveryAddressQueryController {
    private final DeliveryAddressQueryService deliveryAddressQueryService;

    // 내 배송지 목록 조회
    public ResponseEntity<List<DeliveryAddressResponse>> listDeliveryAddresses(@AuthenticationPrincipal SecurityUser securityUser) {
        List<DeliveryAddressResponse> deliveryAddresses = deliveryAddressQueryService.findAllDeliveryAddressesByUserId(securityUser.user().getId());
        return ResponseEntity.ok(deliveryAddresses);
    }

    // 배송지 상세 조회

    // 기본 배송지 조회
}
