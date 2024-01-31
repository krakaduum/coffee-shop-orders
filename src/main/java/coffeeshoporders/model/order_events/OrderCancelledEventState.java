package coffeeshoporders.model.order_events;

import java.time.LocalDateTime;

public record OrderCancelledEventState(String orderId,
                                       String employeeId,
                                       String cancellationReason,
                                       LocalDateTime createdAt) implements OrderEventState {
}
