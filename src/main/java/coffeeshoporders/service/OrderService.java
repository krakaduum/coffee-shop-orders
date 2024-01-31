package coffeeshoporders.service;

import coffeeshoporders.model.Order;
import coffeeshoporders.model.order_events.OrderEvent;

public interface OrderService {

    void publishEvent(OrderEvent event);

    Order findOrder(String id);

    Long getLastOrderEventVersion(String id);

}
