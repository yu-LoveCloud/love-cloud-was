package com.lovecloud.ordermanagement.query.response;

import com.lovecloud.ordermanagement.domain.Order;
import com.lovecloud.ordermanagement.domain.OrderDetails;
import com.lovecloud.productmanagement.domain.MainImage;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record OrderDetailResponse(
        Long orderId,

        String orderNumber,

        LocalDateTime orderDateTime,

        List<orderProduct> orderProducts,
        String receiverName,
        String receiverPhoneNumber,
        String deliveryName,
        String receiverZipCode,
        String receiverAddress,
        String receiverAddressDetail,
        String deliveryMemo,
        String deliveryStatus
) {

    @Builder
    public record orderProduct(
            Long productId,
            String productName,
            Long price,
            List<String> mainImages,
            String modelName

    ) {
    }
    public static OrderDetailResponse from(Order order, List<OrderDetails> orderDetails) {
        return OrderDetailResponse.builder()
                .orderId(order.getId())
                .orderNumber(order.getOrderNumber())
                .orderDateTime(order.getOrderDateTime())
                .orderProducts(orderDetails.stream().map(orderDetail -> orderProduct.builder()
                        .productId(orderDetail.getFunding().getProductOptions().getId())
                        .productName(orderDetail.getFunding().getProductOptions().getProduct().getProductName())
                        .price(orderDetail.getFunding().getProductOptions().getPrice())
                        .mainImages(orderDetail.getFunding().getProductOptions().getMainImages().stream().map(MainImage::getMainImageName).toList())
                        .modelName(orderDetail.getFunding().getProductOptions().getModelName())
                        .build())
                        .toList())
                .receiverName(order.getDelivery().getReceiverName())
                .receiverPhoneNumber(order.getDelivery().getReceiverPhoneNumber())
                .deliveryName(order.getDelivery().getDeliveryName())
                .receiverZipCode(order.getDelivery().getZipCode())
                .receiverAddress(order.getDelivery().getAddress())
                .receiverAddressDetail(order.getDelivery().getDetailAddress())
                .deliveryMemo(order.getDelivery().getDeliveryMemo())
                .deliveryStatus(order.getDelivery().getDeliveryStatus().name())
                .build();

    }

}
