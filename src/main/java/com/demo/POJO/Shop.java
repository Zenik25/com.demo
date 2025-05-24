package com.demo.POJO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.util.List;

@NamedQuery(name = "Shop.getAllShop", query = "SELECT s FROM Shop s")

@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name="shop")


public class Shop implements Serializable {


    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "image")
    private String image;

    @Column(name = "phone")
    private String phone;


    public String getName() {
        return name;
    }

    public String getPhone(){
        return phone;
    }
//    @OneToMany(mappedBy = "shop")
//    @JsonManagedReference
//    private List<TableLogin> my_table;

    public String getImage() {
        return image.substring(image.lastIndexOf("/") + 1);  // Extracts only filename
    }
    //    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<Product> product; // Establish relationship with Product

}