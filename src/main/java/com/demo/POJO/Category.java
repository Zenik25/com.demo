package com.demo.POJO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.util.List;

@NamedQueries({
        @NamedQuery(
                name = "Category.getAllCategory",
                query = "SELECT c FROM Category c WHERE c.id IN (SELECT p.category.id FROM Product p WHERE p.status = 'true')"
        )
})

@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name="category")


public class Category implements Serializable {


    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

//    @Column(name = "image")
//    private String image;
//    public String getName() {
//        return name;
//    }
//
//    public String getImage() {
//        return image.substring(image.lastIndexOf("/") + 1);  // Extracts only filename
//    }
//    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<Product> product; // Establish relationship with Product
@JsonIgnore  // Prevents infinite recursion
@OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
private List<Product> products;
}
