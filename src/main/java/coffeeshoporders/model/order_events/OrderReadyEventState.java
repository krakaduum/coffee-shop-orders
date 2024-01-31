package coffeeshoporders.model.order_events;

import java.time.LocalDateTime;

public record OrderReadyEventState(String orderId,
                                   String employeeId,
                                   LocalDateTime createdAt) implements OrderEventState {
}
