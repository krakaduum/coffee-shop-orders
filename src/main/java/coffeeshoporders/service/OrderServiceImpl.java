package coffeeshoporders.service;

import coffeeshoporders.exception.InvalidEventStateException;
import coffeeshoporders.model.Order;
import coffeeshoporders.model.enums.OrderStatus;
import coffeeshoporders.model.order_events.OrderEvent;
import coffeeshoporders.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final EventRepository eventRepository;

    @Override
    public void publishEvent(OrderEvent event) {
        Collection<OrderEvent> orderEvents = eventRepository.findAllByOrderId(event.getOrderId());
        if (!event.getStatus().equals(OrderStatus.CREATED)) {
            if (orderEvents.stream().noneMatch(x -> x.getStatus().equals(OrderStatus.CREATED))) {
                throw new InvalidEventStateException("Заказ не был зарегистрирован");
            }
            if (orderEvents.stream().anyMatch(y -> y.getStatus().equals(OrderStatus.CANCELLED)
                    || y.getStatus().equals(OrderStatus.DELIVERED))) {
                throw new InvalidEventStateException("Заказ был отменен или уже доставлен");
            }
            eventRepository.save(event);
        } else {
            eventRepository.save(event);
        }
    }

    @Override
    public Order findOrder(String id) {
        Order order = new Order();
        order.setId(id);
        var orderEvents = eventRepository.findAllByOrderId(id);
        if (orderEvents.size() == 0)
            throw new NoSuchElementException("Заказ не найден");
        order.setOrderEvents(orderEvents);
        order.setCurrentState(eventRepository.findFirstByOrderIdOrderByVersionDesc(id).get().getState());

        return order;
    }

    @Override
    public Long getLastOrderEventVersion(String id) {
        Optional<OrderEvent> orderEvent = eventRepository
                .findFirstByOrderIdOrderByVersionDesc(id);

        if (orderEvent.isPresent()) {
            return orderEvent.get().getVersion();
        } else {
            throw new NoSuchElementException();
        }
    }

}
