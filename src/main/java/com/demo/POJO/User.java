package com.demo.POJO;

import jakarta.persistence.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NamedQuery;

import java.io.Serializable;

@NamedQuery(name = "User.findByEmailId",query = "select u from User u where u.email=:email")
//@NamedQuery(name = "User.getAllUser", query = "select new com.demo.wrapper.UserWrapper(u.id,u.name,u.contactNumber,u.email,u.status, u.password) from User u where u.role='user'")
@NamedQuery(
        name = "User.getAllUser",
        query = "select new com.demo.wrapper.UserWrapper(u.id, u.name, u.contactNumber, u.email, u.status, u.password) from User u where u.role='user'"
)

//@NamedQuery(name = "User.getAllAdmin", query = "select u.id, u.name, u.email, u.role, u.password from User u where u.role='admin'")
@NamedQuery(name = "User.getAllAdmin",
        query = "select new com.demo.wrapper.UserWrapper(u.id, u.name, u.email, u.role, u.password) from User u where u.role = 'admin'")

@NamedQuery(name ="User.updateStatus", query = "update User u set u.status=:status where u.id=:id")



@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name="user")
public class User  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id")
    private  Integer id;

    @Column(name="name")
    private String name;

    @Column(name = "contactNumber")
    private String contactNumber;

    @Column(name ="email")
    private  String email;

    @Column(name="password")
    private String password;

    @Column(name="status")
    private String status;

    @Column(name="role")
    private String role;

}
