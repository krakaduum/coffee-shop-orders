package coffeeshoporders.controller;

import coffeeshoporders.dto.OrderCreatedEventStateDto;
import coffeeshoporders.model.Order;
import coffeeshoporders.model.enums.OrderStatus;
import coffeeshoporders.model.order_events.*;
import coffeeshoporders.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping(path = "/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderEvent createOrder(@RequestBody OrderCreatedEventStateDto orderCreatedEventStateDto) {
        String orderId = UUID.randomUUID().toString();
        Long version = 0L;
        LocalDateTime createdAt = LocalDateTime.now();

        var orderCreatedEventState = new OrderCreatedEventState(
                orderId,
                orderCreatedEventStateDto.clientId(),
                orderCreatedEventStateDto.employeeId(),
                orderCreatedEventStateDto.expectedDeliveryTime(),
                orderCreatedEventStateDto.productId(),
                orderCreatedEventStateDto.productCost(),
                createdAt
        );

        var orderEvent = new OrderEvent(
                null,
                orderId,
                version,
                OrderStatus.CREATED,
                orderCreatedEventState
        );

        orderService.publishEvent(orderEvent);

        return orderEvent;
    }

    @PostMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateOrder(@PathVariable String orderId,
                            @RequestParam String type,
                            @RequestParam String employeeId,
                            @RequestParam(required = false) String cancellationReason) {
        Long version = orderService.getLastOrderEventVersion(orderId) + 1;
        LocalDateTime createdAt = LocalDateTime.now();
        OrderEventState orderEventState = null;
        OrderStatus orderStatus = OrderStatus.valueOf(type.toUpperCase());

        switch (orderStatus) {
            case CANCELLED:
                orderEventState = new OrderCancelledEventState(
                        orderId,
                        employeeId,
                        cancellationReason,
                        createdAt
                );
                break;

            case IN_PROGRESS:
                orderEventState = new OrderInProgressEventState(
                        orderId,
                        employeeId,
                        createdAt
                );
                break;

            case READY:
                orderEventState = new OrderReadyEventState(
                        orderId,
                        employeeId,
                        createdAt
                );
                break;

            case DELIVERED:
                orderEventState = new OrderDeliveredEventState(
                        orderId,
                        employeeId,
                        createdAt
                );
                break;
        }

        var orderEvent = new OrderEvent(
                null,
                orderId,
                version,
                orderStatus,
                orderEventState
        );

        orderService.publishEvent(orderEvent);
    }

    @GetMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public Order getOrder(@PathVariable String orderId) {
        return orderService.findOrder(orderId);
    }

}
