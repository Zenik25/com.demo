////package com.demo.service;
////
////import org.springframework.http.ResponseEntity;
////
////import java.util.Map;
////
////public interface TableLoginService {
////
////    ResponseEntity<String> add_table(Map<String, String> requestMap);
////}
//
//package com.demo.service;
//
//import com.demo.POJO.TableLogin;
//
//public interface TableLoginService {
//    TableLogin autoLogin(String tableNumber);
//    TableLogin addTableData(String tableNumber, String seat, String password);
//}
//
package com.demo.service;

import com.demo.POJO.TableLogin;
import com.demo.dto.TableLoginRequest;
import com.demo.wrapper.ShopTableWrapper;
import com.demo.wrapper.TableLoginWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TableLoginService {

    // Add a new table and generate QR code
    TableLogin addTableData(String tableNumber, String seat, String password, Integer shopId);

    TableLogin editTableData(Long id, TableLoginRequest tableLoginRequest);
//    List<TableLoginWrapper> getTablesByShopName(String shopName);
List<TableLoginWrapper> getTablesByShopId(Integer shopId);

    List<TableLogin> getAllTables();
    // Perform auto-login based on the table number
    TableLogin autoLogin(String tableNumber,String seat, String password);
    ResponseEntity<String> updateTableStatus(int tableNumber, String status);
    List<ShopTableWrapper> getTablesGroupedByShop();

}

