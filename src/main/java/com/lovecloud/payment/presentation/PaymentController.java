package com.lovecloud.payment.presentation;

import com.lovecloud.payment.application.PaymentService;
import com.lovecloud.payment.query.response.PaymentResponse;
import com.siot.IamportRestClient.exception.IamportResponseException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @RequestMapping("/verify/{imp_uid}") //https://URL/verify/{거래고유번호}
    public ResponseEntity<PaymentResponse> verifyAndSavePayment(@PathVariable("imp_uid") String imp_uid) throws IamportResponseException, IOException {
        return ResponseEntity.ok(paymentService.verifyAndSavePayment(imp_uid));
    }

}
