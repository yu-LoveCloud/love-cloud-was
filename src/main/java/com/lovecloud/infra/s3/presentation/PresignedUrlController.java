package com.lovecloud.infra.s3.presentation;

import com.lovecloud.infra.s3.CreatePresignedUrlResponse;
import com.lovecloud.infra.s3.PresignedUrlService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/storage/presigned-urls")
public class PresignedUrlController {

    private final PresignedUrlService presignedUrlService;

    @PostMapping
    public ResponseEntity<CreatePresignedUrlResponse> createPresignedUrl(
            @Valid @RequestBody CreatePresignedUrlRequest request
    ) {
        CreatePresignedUrlResponse response = presignedUrlService.create(request.imageExtension());
        return ResponseEntity.ok(response);
    }
}
