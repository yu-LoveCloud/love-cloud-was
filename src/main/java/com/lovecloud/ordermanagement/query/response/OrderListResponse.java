package com.lovecloud.ordermanagement.query.response;

import com.lovecloud.ordermanagement.domain.Order;
import com.lovecloud.ordermanagement.domain.OrderDetails;
import com.lovecloud.productmanagement.domain.MainImage;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record OrderListResponse(
        Long orderId,
        LocalDateTime orderDateTime,
        String orderStatus,
        List<OrderProduct> orderProducts
) {
    @Builder
    public static record OrderProduct(
            Long productId,
            String productName,
            Long price,
            List<String> mainImages,
            String modelName
    ) {
    }
    public static List<OrderListResponse> from(List<Order> orders) {
        return orders.stream().map(order -> OrderListResponse.builder()
                .orderId(order.getId())
                .orderDateTime(order.getOrderDateTime())
                .orderStatus(order.getOrderStatus().name())
                .orderProducts(order.getOrderDetails().stream().map(orderDetail -> OrderProduct.builder()
                        .productId(orderDetail.getFunding().getProductOptions().getId())
                        .productName(orderDetail.getFunding().getProductOptions().getProduct().getProductName())
                        .price(orderDetail.getFunding().getProductOptions().getPrice())
                        .mainImages(orderDetail.getFunding().getProductOptions().getMainImages().stream().map(MainImage::getMainImageName).toList())
                        .modelName(orderDetail.getFunding().getProductOptions().getModelName())
                        .build())
                        .toList())
                .build()).toList();
    }
}
