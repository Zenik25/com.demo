package com.demo.POJO;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


@NamedQueries({
        @NamedQuery(name = "Order.getAllOrders", query = "SELECT o FROM Order o"),
        @NamedQuery(name = "Order.getOrdersByTableNumber", query = "SELECT o FROM Order o WHERE o.tableNumber = :tableNumber")
})

@Data
@Entity
@Table(name = "orders")  // This is the database table name
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Unique identifier for the order

    @Column(name = "table_number", nullable = false)
    private int tableNumber;  // The table number where the order is placed

    @ElementCollection
    @Column(name = "ordered_product_names", nullable = false)
    private List<String> orderedProductNames;

    @ElementCollection
    @Column(name ="ordered_product_prices", nullable = false)
    private List<Integer> price;

    @ElementCollection
    @Column(name = "quantity", nullable = false)
    private List<Integer> quantity;

    @Column(name = "total_price", nullable = false)
    private int totalPrice;  // Total price of the order

    @Column(name = "date", nullable = false, updatable = false)
    private LocalDate orderDate;

    @Column(name = "time", nullable = false, updatable = false)
    private LocalTime orderTime;

    @Column(nullable = false)
    private String status = "Confirming";

    @Column(nullable = false, name = "type")
    private String orderType;

    @Column(nullable = false, name = "email")
    private String email;



    @ManyToOne
    @JoinColumn(name = "shop_id", referencedColumnName = "id")
    private Shop shop;

    // This method will be invoked before persisting the order to the database
    @PrePersist
    public void prePersist() {

        this.orderDate = LocalDate.now();
        this.orderTime = LocalTime.now();
    }

}
