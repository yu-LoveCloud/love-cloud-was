package com.lovecloud.payment.application;

import com.lovecloud.payment.domain.PaymentStatus;
import com.lovecloud.payment.domain.repository.PaymentRepository;
import com.lovecloud.payment.exception.DuplicatePaymentException;
import com.lovecloud.payment.exception.PaymentCancellationFailedException;
import com.lovecloud.payment.exception.PaymentNotCompletedException;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    @Value("${imp.api.key}")
    private String apiKey;

    @Value("${imp.api.secret}")
    private String apiSecret;
    private IamportClient iamportClient;
    private final PaymentRepository paymentRepository;

    @PostConstruct
    public void init() {
        iamportClient = new IamportClient(apiKey, apiSecret);
    }

    public Long completePayment(String impUid) throws IamportResponseException, IOException {
        // 아임포트 API를 사용하여 결제 정보를 가져옴
        IamportResponse<Payment> iamportResponse = iamportClient.paymentByImpUid(impUid);

        //결제 정보를 가져옴
        String merchantUid = iamportResponse.getResponse().getMerchantUid();
        Long amount = (iamportResponse.getResponse().getAmount()).longValue();
        String name = iamportResponse.getResponse().getName();
        String status = iamportResponse.getResponse().getStatus();
        LocalDateTime paidAt = iamportResponse.getResponse().getPaidAt()
                .toInstant()
                .atZone(java.time.ZoneId.systemDefault())
                .toLocalDateTime();
        String payMethod = iamportResponse.getResponse().getPayMethod();

        //중복 결제 및 결제 완료 여부 확인
        checkDuplicatePayment(impUid);
        checkPayStatus(status);

        //결제 정보 저장
        com.lovecloud.payment.domain.Payment payment = createPayment(impUid, merchantUid, amount, name, status, paidAt, payMethod);
        com.lovecloud.payment.domain.Payment savedPayment = paymentRepository.save(payment);

        cancelPayment(impUid);
        return savedPayment.getId();

    }

    public Long cancelPayment(String impUid) throws IamportResponseException, IOException {
        //결제 내역을 가져옴
        com.lovecloud.payment.domain.Payment payment = paymentRepository.findByImpUidOrThrow(impUid);

        //iamport 결제 취소
        IamportResponse<Payment> iamportResponse = iamportClient.cancelPaymentByImpUid(new CancelData(impUid, true));

        validateCancellationResponse(iamportResponse);

        // 결제 정보 업데이트
        payment.cancel();

        return payment.getId();
    }

    private static void validateCancellationResponse(IamportResponse<Payment> iamportResponse) {
        //code가 0이 아니면 결제 취소 실패
        if(iamportResponse.getCode() != 0) {
            throw new PaymentCancellationFailedException();
        }
    }

    private static com.lovecloud.payment.domain.Payment createPayment(String impUid, String merchantUid, Long amount, String name, String status, LocalDateTime paidAt, String payMethod) {
        return com.lovecloud.payment.domain.Payment.builder()
                .impUid(impUid)
                .merchantUid(merchantUid)
                .amount(amount)
                .name(name)
                .paymentStatus(PaymentStatus.fromString(status))
                .paidAt(paidAt)
                .payMethod(payMethod)
                .build();
    }

    private void checkPayStatus(String status) {
        if (!status.equals("paid")) {
            throw new PaymentNotCompletedException();
        }
    }


    private void checkDuplicatePayment(String impUid) {
        if(paymentRepository.existsByImpUid(impUid)) {
            throw new DuplicatePaymentException();
        }
    }
}
