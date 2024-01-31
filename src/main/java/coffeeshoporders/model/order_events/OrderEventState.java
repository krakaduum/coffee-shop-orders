package coffeeshoporders.model.order_events;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.WRAPPER_OBJECT)
@JsonSubTypes({
        @JsonSubTypes.Type(value = OrderCreatedEventState.class),
        @JsonSubTypes.Type(value = OrderCancelledEventState.class),
        @JsonSubTypes.Type(value = OrderInProgressEventState.class),
        @JsonSubTypes.Type(value = OrderReadyEventState.class),
        @JsonSubTypes.Type(value = OrderDeliveredEventState.class)
})
public interface OrderEventState extends Serializable {
}
