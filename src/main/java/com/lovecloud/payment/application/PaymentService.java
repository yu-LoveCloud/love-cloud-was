package com.lovecloud.payment.application;

import com.lovecloud.payment.domain.repository.PaymentRepository;
import com.lovecloud.payment.query.response.PaymentResponse;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@Transactional
public class PaymentService {

    @Value("${imp.api.key}")
    private String apiKey;

    @Value("${imp.api.secret}")
    private String apiSecret;
    private final IamportClient iamportClient;
    private final PaymentRepository paymentRepository;
    private PaymentService(PaymentRepository paymentRepository){
        this.iamportClient = new IamportClient(apiKey, apiSecret);
        this.paymentRepository = paymentRepository;
    }

    public PaymentResponse verifyAndSavePayment(String impUid) throws IamportResponseException, IOException {
        IamportResponse<Payment> iamportResponse = iamportClient.paymentByImpUid(impUid);
        Long amount = (iamportResponse.getResponse().getAmount()).longValue();
        String name = iamportResponse.getResponse().getName();
        String status = iamportResponse.getResponse().getStatus();
        String

        PaymentResponse paymentResponse = PaymentResponse.builder()
                .impUid(impUid)
                .amount(amount)
                .name(name)
                .status(status)
                .build();

        if(paymentRepository.countByImpUidContainsIgnoreCase(impUid)){
            return paymentResponse;
        }


    }
}
