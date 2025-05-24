package com.demo.POJO;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import com.demo.POJO.Shop;

//@NamedQuery(
//        name = "DeliveryWrapper.findByEmail",
//        query = "SELECT new com.demo.wrapper.DeliveryWrapper(d.Name, d.email, d.d.street, d.phone, d.totalAmount) FROM Delivery d WHERE d.email = :email"
//)
@NamedQuery(
        name = "DeliveryWrapper.findAllDeliveries",
        query = "SELECT new com.demo.wrapper.DeliveryWrapper(d.id, d.Name, d.email, d.street, d.phone, d.totalAmount, d.paymentMethod, d.orderDate, d.orderTime, d.orderedProducts ,d.status) FROM Delivery d"
)


@Entity
@Table(name = "deliveries")
@Data
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Name", nullable = false)
    private String Name;



    @Column(name = "email")
    private String email;

    @Column(name = "street")
    private String street;

    @Column(name = "phone")
    private String phone;

    @Column(name = "totalAmount")
    private int totalAmount;

    @Column(name = "tax")
    private Integer tax;

    @Column(name = "paymentMethod")  // Add this field
    private String paymentMethod;  // Store the payment method (Cash, KBZ Pay, etc.)

    @Column(name = "date", nullable = false, updatable = false)
    private LocalDate orderDate;

    @Column(name = "time", nullable = false, updatable = false)
    private LocalTime orderTime;

    @Column(nullable = false)
    private String status = "Confirming"; // Default value


    @OneToMany(mappedBy = "delivery", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    @JsonManagedReference  // Prevent infinite recursion
    private List<OrderedProduct> orderedProducts;

//    @Column(name = "shop_ids")
//    private Integer shopId;

    @Column(name = "deliveryFee", nullable = false)
    private Integer deliveryFee;

    @ManyToOne
    @JoinColumn(name = "shop_id", referencedColumnName = "id")
    private Shop shop;



    @Override
    public String toString() {
        return "Delivery [id=" + id + ", name=" + Name + ", email=" + email + "]";
    }

    @PrePersist
    public void prePersist() {

        this.orderDate = LocalDate.now();
        this.orderTime = LocalTime.now();
    }


}
