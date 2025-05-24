//package com.demo.POJO;
//
//import jakarta.persistence.*;
//import lombok.Data;
//import org.hibernate.annotations.DynamicInsert;
//import org.hibernate.annotations.DynamicUpdate;
//
//import java.io.Serializable;
//
//@NamedQuery(name="User.findByTableNumber",query = "select u from TableInfo u where u.tableNumber=:tableNumber")
//@Data
//@Entity
//@DynamicUpdate
//@DynamicInsert
//@Table(name="my_table")
//public class TableLogin implements Serializable {
//    private static final long serialVersionUID = 1L;
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//
//    @Column(name = "id")
//    private  Integer id;
//
//
//    @Column(name = "tableNumber")
//    private String tableNumber;
//
//    @Column(name="seat")
//    private String seat;
//
//    @Column(name="password")
//    private String password;
//
////    @Column(name="status")
////    private String status;
//
//
//
//}


package com.demo.POJO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NamedQuery(name="TableLogin.findByTableNumber", query = "select t from TableLogin t where t.tableNumber = :tableNumber")
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "my_table")
public class TableLogin implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "tableNumber")
    private String tableNumber;

    @Column(name = "seat")
    private String seat;

    @Column(name = "password")
    private String password;

    @Column(nullable = false)
    private String status = "Available";

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "shop_id", nullable = false)
//    @JsonManagedReference
    private Shop shop;







}
