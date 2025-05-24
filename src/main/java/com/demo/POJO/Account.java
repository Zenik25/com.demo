package com.demo.POJO;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    private String role; // "admin" or "employee"

    @ManyToOne
    @JoinColumn(name = "shop_id", referencedColumnName = "id")
    private Shop shop;

    // Getters and Setters
}

