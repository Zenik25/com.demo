//package com.demo.wrapper;
//
//import com.demo.POJO.TableLogin;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.TypedQuery;
//import java.util.List;
//
//@Component
//public class TableLoginWrapper {
//
//    @Autowired
//    private EntityManager entityManager;
//
//    public List<TableLogin> findByTableNumber(String tableNumber) {
//        TypedQuery<TableLogin> query = entityManager.createNamedQuery("TableLogin.findByTableNumber", TableLogin.class);
//        query.setParameter("tableNumber", tableNumber);
//        return query.getResultList();
//    }
//}

package com.demo.wrapper;

import com.demo.POJO.TableLogin;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TableLoginWrapper {

    private Integer id;
    private String tableNumber;
    private String seat;
    private String password;
    private String status;
//    private String shopName;
    private Integer shopId;


    public TableLoginWrapper(Integer id, String tableNumber, String seat, String password) {
        this.id = id;
        this.tableNumber = tableNumber;
        this.seat = seat;
        this.password = password;


    }
    public TableLoginWrapper(TableLogin table) {
        this.id = table.getId();
        this.tableNumber = table.getTableNumber();
        this.seat = table.getSeat();
        this.status = table.getStatus();

//        this.shopName = table.getShopName();

    }
}

