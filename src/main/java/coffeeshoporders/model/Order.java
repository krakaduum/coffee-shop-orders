package coffeeshoporders.model;

import coffeeshoporders.model.order_events.OrderEvent;
import coffeeshoporders.model.order_events.OrderEventState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order {

    private String id;

    private Collection<OrderEvent> orderEvents = new ArrayList<>();

    private OrderEventState currentState;

    public Order(String id) {
        this.id = id;
    }

}
