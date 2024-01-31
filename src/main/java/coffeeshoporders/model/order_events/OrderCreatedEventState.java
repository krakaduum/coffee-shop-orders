package coffeeshoporders.model.order_events;

import java.time.LocalDateTime;

public record OrderCreatedEventState(String orderId,
                                     String clientId,
                                     String employeeId,
                                     Long expectedDeliveryTime,
                                     String productId,
                                     Long productCost,
                                     LocalDateTime createdAt) implements OrderEventState {
}
