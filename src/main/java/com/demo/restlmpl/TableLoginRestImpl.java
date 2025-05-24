//////package com.demo.restlmpl;
//////
//////import com.demo.constents.ResConstants;
//////import com.demo.rest.TableLoginRest;
//////import com.demo.service.TableLoginService;
//////import com.demo.utils.ResUtils;
//////import org.springframework.beans.factory.annotation.Autowired;
//////import org.springframework.http.HttpStatus;
//////import org.springframework.http.ResponseEntity;
//////import org.springframework.web.bind.annotation.RestController;
//////
//////import java.util.Map;
//////
//////
//////@RestController
//////public class TableLoginRestImpl implements TableLoginRest {
//////    @Autowired
//////    TableLoginService tableService;
//////
//////
//////    @Override
//////    public ResponseEntity<String> add_table(Map<String, String> requestMap) {
//////        try{
//////            return tableService.add_table(requestMap);
//////        }catch(Exception ex){
//////            ex.printStackTrace();
//////        }
//////        return ResUtils.getResponseEntity(ResConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
//////    }
//////}
////
////
////package com.demo.restlmpl;
////
////import com.demo.POJO.TableLogin;
////import com.demo.rest.TableLoginRest;
////import com.demo.service.TableLoginService;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.web.bind.annotation.*;
////
////@RestController
////@RequestMapping("/qr")
////public class TableLoginRestImpl implements TableLoginRest {
////
////    @Autowired
////    private TableLoginService tableLoginService;
////
////    @PostMapping("/login")
////    @Override
////    public TableLogin autoLogin(@RequestParam String tableNumber) {
////        return tableLoginService.autoLogin(tableNumber);
////    }
////}
//
//
////package com.demo.restlmpl;
////
////import com.demo.POJO.TableLogin;
////import com.demo.rest.TableLoginRest;
////import com.demo.service.TableLoginService;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.web.bind.annotation.*;
////
////@RestController
////@RequestMapping("/qr")
////public class TableLoginRestImpl implements TableLoginRest {
////
////    @Autowired
////    private TableLoginService tableLoginService;
////
////    @PostMapping("/login")
////    @Override
////    public TableLogin autoLogin(@RequestParam String tableNumber) {
////        return tableLoginService.autoLogin(tableNumber);
////    }
////
////    @PostMapping("/add")
////    public TableLogin addTableData(@RequestParam String tableNumber, @RequestParam String seat, @RequestParam String password) {
////        return tableLoginService.addTableData(tableNumber, seat, password);
////    }
////}
//package com.demo.restlmpl;
//
//import com.demo.POJO.TableLogin;
//import com.demo.rest.TableLoginRest;
//import com.demo.service.TableLoginService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class TableLoginRestImpl implements TableLoginRest {
//
//    @Autowired
//    private TableLoginService tableLoginService;
//
//    // Use @RequestBody for JSON data
//    @Override
//    public ResponseEntity<TableLogin> addTableData(String tableNumber, String seat, String password) {
//        TableLogin newTable = tableLoginService.addTableData(tableNumber, seat, password);
//        return new ResponseEntity<>(newTable, HttpStatus.CREATED);
//    }
////
//
//    @Override
//    public ResponseEntity<TableLogin> autoLogin(String tableNumber) {
//        TableLogin tableLogin = tableLoginService.autoLogin(tableNumber);
//        return new ResponseEntity<>(tableLogin, HttpStatus.OK);
//    }
//}
//
//
//

package com.demo.restlmpl;

import com.demo.POJO.Shop;
import com.demo.POJO.TableLogin;
import com.demo.dao.TableLoginDao;
import com.demo.dto.ShopTablesResponse;
import com.demo.dto.TableLoginRequest;
import com.demo.rest.TableLoginRest;
import com.demo.service.ShopService;
import com.demo.service.TableLoginService;
import com.demo.wrapper.ShopTableWrapper;
import com.demo.wrapper.TableLoginWrapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


//@CrossOrigin(origins = "*") // Allow all origins
@RestController
public class TableLoginRestImpl implements TableLoginRest {

    @Autowired
    private TableLoginService tableLoginService;

    @Autowired
    private ShopService shopService;

    @Autowired
    private TableLoginDao tableLoginDao;


//    @Override
//    public ResponseEntity<TableLogin> addTable(@RequestBody TableLoginRequest tableLoginRequest) {
//        TableLogin savedTable = tableLoginService.addTableData(
//                tableLoginRequest.getTableNumber(),
//                tableLoginRequest.getSeat(),
//                tableLoginRequest.getPassword()
//        );
//        return ResponseEntity.ok(savedTable);
//    }
//@PostMapping("/add")
//public ResponseEntity<TableLogin> addTable(@RequestBody TableLoginRequest request) {
//    return ResponseEntity.ok(
//            tableLoginService.addTableData(request.getTableNumber(), request.getSeat(), request.getPassword(), request.getShopId())
//    );
//}
@PostMapping("/add")
public ResponseEntity<TableLogin> addTable(
        @RequestBody TableLoginRequest request,
        @RequestHeader("shopId") Integer shopId) {
    return ResponseEntity.ok(
            tableLoginService.addTableData(
                    request.getTableNumber(),
                    request.getSeat(),
                    request.getPassword(),
                    shopId
            )
    );
}



    // Endpoint for adding a new table and generating a QR code
//    @Override
//    public ResponseEntity<TableLogin> addTableData(String tableNumber, String seat, String password) {
//        try {
//            TableLogin tableLogin = tableLoginService.addTableData(tableNumber, seat, password); // Call service to add table and generate QR code
//            return new ResponseEntity<>(tableLogin, HttpStatus.CREATED);  // Return the created tableLogin with HTTP 201 status
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);  // Handle errors and return 500 status
//        }
//    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<TableLogin> editTable(@PathVariable Long id, @RequestBody TableLoginRequest tableLoginRequest) {
        TableLogin updatedTable = tableLoginService.editTableData(id, tableLoginRequest);
        if (updatedTable != null) {
            return ResponseEntity.ok(updatedTable);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @Override
    public ResponseEntity<List<TableLogin>> getAllTables() {
        return ResponseEntity.ok(tableLoginService.getAllTables());
    }



//    @Override
//    public ResponseEntity<List<TableLoginWrapper>> getTablesByShop(@PathVariable Integer shopId) {
//        return new ResponseEntity<>(tableLoginService.getTablesByShopId(shopId), HttpStatus.OK);
//    }
@Override
public ResponseEntity<ShopTablesResponse> getTablesByShop(@PathVariable Integer shopId) {
    // Fetch the tables for the given shopId
    List<TableLoginWrapper> tables = tableLoginService.getTablesByShopId(shopId);

    // Fetch the shop using shopId from the ShopService
    Shop shop = shopService.findById(shopId); // Now this should work

    if (shop == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // If shop not found
    }

    // Create the response containing both shop name and table list
    ShopTablesResponse response = new ShopTablesResponse(shop.getName(), tables);

    return new ResponseEntity<>(response, HttpStatus.OK);
}


    @GetMapping("/grouped-by-shop")
    public ResponseEntity<List<ShopTableWrapper>> getTablesGroupedByShop() {
        return new ResponseEntity<>(tableLoginService.getTablesGroupedByShop(), HttpStatus.OK);
    }


    // Endpoint for auto login by table number
    @Override
    public ResponseEntity<TableLogin> autoLogin(String tableNumber, String seat, String password) {
//        try {
//            TableLogin tableLogin = tableLoginService.autoLogin(tableNumber); // Call service to perform auto-login
//            return new ResponseEntity<>(tableLogin, HttpStatus.OK);  // Return table data with HTTP 200 status
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);  // Return 404 if table not found
//        }
        TableLogin tableLogin = tableLoginService.autoLogin(tableNumber, seat, password);
        if (tableLogin != null) {
            return new ResponseEntity<>(tableLogin, HttpStatus.OK);  // Success, return table data
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);  // Unauthorized if data is incorrect

    }
    @Override
    public ResponseEntity<String> updateTableStatus(@PathVariable int tableNumber, @RequestParam String status) {
        return tableLoginService.updateTableStatus(tableNumber, status);
    }
    @Override
    public ResponseEntity<?> updateTableStatus(Long id, Map<String, String> request) {
        Optional<TableLogin> tableOpt = tableLoginDao.findById(id.intValue());

        if (tableOpt.isPresent()) {
            TableLogin table = tableOpt.get();
            table.setStatus(request.get("status")); // Update status
            tableLoginDao.save(table);
            return ResponseEntity.ok("Status updated successfully");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Table not found");
    }
//    @PostConstruct
//    public void init() {
//        System.out.println("TableLoginRestImpl loaded successfully!");
//    }
}
