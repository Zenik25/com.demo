package com.demo.POJO;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;

@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "email")
public class Email implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "recipient", nullable = false)
    private String recipient;

    @Column(name = "subject", nullable = false)
    private String subject;

    @Column(name = "text", nullable = false)
    private String text;

    // Default constructor
    public Email() {}

    // Constructor with fields
    public Email(String recipient, String subject, String text) {
        this.recipient = recipient;
        this.subject = subject;
        this.text = text;
    }

//    public void setBody(String emailBody) {
//    }
}
