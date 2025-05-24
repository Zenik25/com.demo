//package com.demo.rest;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Map;
//
//@RequestMapping(path = "/table")
//public interface TableLoginRest {
//
//    @PostMapping(path = "/add_table")
//    public ResponseEntity<String> add_table(@RequestBody(required = true)Map<String, String> requestMap);
//
//
//}
//
//

//package com.demo.rest;
//
//import com.demo.POJO.TableLogin;
//import org.springframework.http.ResponseEntity;
//
//public interface TableLoginRest {
//
//    // Endpoint for adding table data
//    ResponseEntity<TableLogin> addTableData(String tableNumber, String seat, String password);
//
//    // Endpoint for auto login
//    ResponseEntity<TableLogin> autoLogin(String tableNumber);
//}
package com.demo.rest;

import com.demo.POJO.TableLogin;
import com.demo.dto.ShopTablesResponse;
import com.demo.dto.TableLoginRequest;
import com.demo.service.TableLoginService;
import com.demo.wrapper.TableLoginWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

//@CrossOrigin(origins = "*") // Allow all origins
@RestController
@RequestMapping("/table-login") // Base URL
public interface TableLoginRest {



//    @PostMapping("/add")
//    ResponseEntity<TableLogin> addTableData(
//            @RequestParam String tableNumber,
//            @RequestParam String seat,
//            @RequestParam String password
//    );
//@PostMapping("/add")
//ResponseEntity<TableLogin> addTable(@RequestBody TableLoginRequest tableLoginRequest);


    // API for auto-login by table number
    @GetMapping("/auto-login") // Ensure it matches query parameters
    ResponseEntity<TableLogin> autoLogin(
            @RequestParam("tableNumber") String tableNumber,
            @RequestParam("seat") String seat,
            @RequestParam("password") String password
    );

    //    @PutMapping("/edit/{id}")
    //    ResponseEntity<TableLogin> editTable(@PathVariable Long id, @RequestBody TableLoginRequest tableLoginRequest);

    @GetMapping("/all")
    ResponseEntity<List<TableLogin>> getAllTables();

    @PutMapping("/status/{tableNumber}")
    ResponseEntity<String> updateTableStatus(@PathVariable int tableNumber, @RequestParam String status);

    @PutMapping("/update-status/{id}")
    ResponseEntity<?> updateTableStatus(@PathVariable Long id, @RequestBody Map<String, String> request);

//    @GetMapping("/shop/{shopId}")
//    ResponseEntity<List<TableLoginWrapper>> getTablesByShop(@PathVariable Integer shopId);
@GetMapping("/shop/{shopId}")
ResponseEntity<ShopTablesResponse> getTablesByShop(@PathVariable Integer shopId);

//    @GetMapping("/get/by-shop")
//    ResponseEntity<List<TableLoginWrapper>> getTablesByShop(@RequestParam String shopName);


}




