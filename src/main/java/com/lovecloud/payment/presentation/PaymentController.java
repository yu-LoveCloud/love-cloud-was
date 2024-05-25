package com.lovecloud.payment.presentation;

import com.lovecloud.payment.application.PaymentCreateService;
import com.lovecloud.payment.query.response.PaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentCreateService paymentCreateService;

    @RequestMapping("/verify/{imp_uid}") //https://URL/verify/{거래고유번호}
    public ResponseEntity<PaymentResponse> verifyAndSavePayment(@PathVariable("imp_uid") String imp_uid) {
        return ResponseEntity.ok(paymentCreateService.verifyAndSavePayment(imp_uid));
    }

}
