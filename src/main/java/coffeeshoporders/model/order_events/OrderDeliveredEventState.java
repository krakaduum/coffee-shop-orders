package coffeeshoporders.model.order_events;

import java.time.LocalDateTime;

public record OrderDeliveredEventState(String orderId,
                                       String employeeId,
                                       LocalDateTime createdAt) implements OrderEventState {
}
