//package com.demo.POJO;
//
//import jakarta.persistence.*;
//import lombok.Data;
//import org.hibernate.annotations.DynamicInsert;
//import org.hibernate.annotations.DynamicUpdate;
//
//import java.io.Serializable;
//
//@NamedQuery(
//        name = "Product.getAllProduct",
//        query = "SELECT new com.demo.wrapper.ProductWrapper(p.id, p.name, p.description, p.price, p.status, p.category.id, p.category.name, p.image) FROM Product p"
//)
//
//
//
//
//
//@NamedQuery(name = "Product.updateProductStatus", query = "update Product p set p.status=:status where p.id=:id")
//
//@NamedQuery(name = "Product.getProductByCategory", query = "select new com.demo.wrapper.ProductWrapper(p.id,p.name) from Product p where p.category.id=:id and p.status='true'")
//
//@NamedQuery(name = "Product.getProductById", query = "select new com.demo.wrapper.ProductWrapper(p.id,p.name,p.description,p.price,p.image) from Product p where p.id=:id")
//
//@Data
//@Entity
//@DynamicUpdate
//@DynamicInsert
//@Table(name="product")
//public class Product implements Serializable {
//
//    public static final long serialVersionUid = 1L;
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//
//    @Column(name = "id")
//    private Integer id;
//
//    @Column(name = "name")
//    private String name;
//
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "category_id", nullable = false)
//    private Category category;
//
//    public Category getCategory() {
//        return category;
//    }
//
//    @Column(name = "description")
//    private String description;
//
//    @Column(name = "price")
//    private Integer price;
//
//    @Column(name = "status")
//    private String status;
//
//    @Column(name = "image")
//    private String image;
//
//
//    public String getImage() {
//        return image.substring(image.lastIndexOf("/") + 1);  // Extracts only filename
//    }
//}
//

package com.demo.POJO;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
@NamedQuery(
        name = "Product.getAllProduct",
        query = "SELECT new com.demo.wrapper.ProductWrapper(" +
                "p.id, " +
                "p.name, " +
                "p.description, " +
                "p.price, " +
                "p.status, " +
                "c.id, " +
                "c.name, " +
                "p.image, " +
                "s.id, " +
                "s.name) " +
                "FROM Product p " +
                "JOIN p.category c " +  // Use the mapped relationship for the category
                "JOIN p.shop s"  // Use the mapped relationship for the shop
)



@NamedQuery(
        name = "Product.getAllProductByShopId",
        query = "SELECT new com.demo.wrapper.ProductWrapper(p.id, p.name, p.description, p.price, p.status, p.category.id, p.category.name, p.image) " +
                "FROM Product p WHERE p.shop.id = :shopId"
)

@NamedQuery(name = "Product.updateProductStatus", query = "UPDATE Product p SET p.status = :status WHERE p.id = :id")

@NamedQuery(name = "Product.getProductByCategory", query = "SELECT new com.demo.wrapper.ProductWrapper(p.id, p.name) FROM Product p WHERE p.category.id = :id AND p.status = 'true'")

@NamedQuery(name = "Product.getProductById", query = "SELECT new com.demo.wrapper.ProductWrapper(p.id, p.name, p.description, p.price, p.image) FROM Product p WHERE p.id = :id")

@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "product")
public class Product implements Serializable {

    public static final long serialVersionUid = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "shop_id", referencedColumnName = "id")
    private Shop shop;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Integer price;

    @Column(name = "status")
    private String status = "Available";


    @Column(name = "image")
    private String image;

    public String getImage() {
        return image.substring(image.lastIndexOf("/") + 1);  // Extracts only filename
    }
}
