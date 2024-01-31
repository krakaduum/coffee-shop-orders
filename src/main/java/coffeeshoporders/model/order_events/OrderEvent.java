package coffeeshoporders.model.order_events;

import coffeeshoporders.model.enums.OrderStatus;
import coffeeshoporders.utility.JpaConverterJson;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Entity
@NoArgsConstructor
@Table(name = "events")
public class OrderEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "order_id")
    String orderId;

    @Column(name = "version")
    Long version;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    OrderStatus status;

    @Convert(converter = JpaConverterJson.class)
    @Column(name = "state")
    OrderEventState state;

}
