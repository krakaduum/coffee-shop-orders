package coffeeshoporders.dto;

import java.time.LocalDateTime;

public record OrderCreatedEventStateDto(String orderId,
                                        String clientId,
                                        String employeeId,
                                        Long expectedDeliveryTime,
                                        String productId,
                                        Long productCost,
                                        LocalDateTime createdAt) {
}
