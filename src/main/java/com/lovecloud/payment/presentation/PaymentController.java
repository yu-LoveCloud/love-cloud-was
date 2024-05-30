package com.lovecloud.payment.presentation;

import com.lovecloud.payment.application.PaymentService;
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

    @RequestMapping("/complete/{imp_uid}") //https://URL/payments/complete/{거래고유번호}
    public ResponseEntity<Long> completePayment(@PathVariable("imp_uid") String imp_uid) throws IamportResponseException, IOException {
        return ResponseEntity.ok(paymentService.completePayment(imp_uid));
    }

    @RequestMapping("/cancel/{imp_uid}") //https://URL/payments/cancel/{거래고유번호}
    public ResponseEntity<Long> cancelPayment(@PathVariable("imp_uid") String imp_uid) throws IamportResponseException, IOException {
        return ResponseEntity.ok(paymentService.cancelPayment(imp_uid));
    }

}
