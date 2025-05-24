////////package com.demo.servicelmpl;
////////
////////import com.demo.POJO.TableLogin;
////////import com.demo.constents.ResConstants;
////////import com.demo.dao.TableLoginDao;
////////
////////import com.demo.service.TableLoginService;
////////
////////import com.demo.utils.ResUtils;
////////import lombok.extern.slf4j.Slf4j;
////////import org.springframework.beans.factory.annotation.Autowired;
////////import org.springframework.http.HttpStatus;
////////import org.springframework.http.ResponseEntity;
////////import org.springframework.stereotype.Service;
////////
////////import java.util.Map;
////////import java.util.Objects;
////////
////////@Slf4j
////////@Service
////////public class TableLoginServiceImpl implements TableLoginService {
////////    @Autowired
////////    TableLoginDao tableDao;
////////
////////    @Override
////////    public ResponseEntity<String> add_table(Map<String, String> requestMap) {
////////        log.info("Inside add_table {}", requestMap);
////////        try {
////////
////////
////////            if (validateAddTableMap(requestMap)) {
////////                TableLogin tableInfo = tableDao.findByTableNumber(requestMap.get("tableNumber"));
////////                if (Objects.isNull(tableInfo)) {
////////                    tableDao.save(getTableFromMap(requestMap));
////////                    return ResUtils.getResponseEntity("Successful Add Table", HttpStatus.OK);
////////                } else {
////////                    return ResUtils.getResponseEntity("Table already exits.", HttpStatus.BAD_REQUEST);
////////                }
////////            } else {
////////                return ResUtils.getResponseEntity(ResConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
////////            }
////////        } catch (Exception ex) {
////////            ex.printStackTrace();
////////        }
////////        return ResUtils.getResponseEntity(ResConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
////////
////////    }
////////
////////    private boolean validateAddTableMap(Map<String, String> requestMap){
////////        if (requestMap.containsKey("tableNumber")
////////                && requestMap.containsKey("seat") && requestMap.containsKey("password"))
////////        {
////////            return true;
////////        }
////////        return false;
////////    }
////////
////////    private TableLogin getTableFromMap(Map<String , String> requestMap){
////////        TableLogin tableInfo = new TableLogin();
////////        tableInfo.setTableNumber(requestMap.get("tableNumber"));
////////        tableInfo.setSeat(requestMap.get("seat"));
////////        tableInfo.setPassword(requestMap.get("password"));
////////        return tableInfo;
////////    }
////////
////////}
//////
//////package com.demo.servicelmpl;
//////
////////import com.demo.POJO.TableLogin;
////////import com.demo.dao.TableLoginDao;
////////import com.demo.service.TableLoginService;
////////import org.springframework.beans.factory.annotation.Autowired;
////////import org.springframework.stereotype.Service;
////////
////////import java.util.Optional;
////////
////////@Service
////////public class TableLoginServiceImpl implements TableLoginService {
////////
////////    @Autowired
////////    private TableLoginDao tableLoginDao;
////////
////////    @Override
////////    public TableLogin autoLogin(String tableNumber) {
////////        Optional<TableLogin> tableLoginOpt = tableLoginDao.findByTableNumber(tableNumber);
////////        if (tableLoginOpt.isPresent()) {
////////            return tableLoginOpt.get();
////////        }
////////        throw new RuntimeException("Table not found");
////////    }
////////}
//////
//////import com.demo.POJO.TableLogin;
//////import com.demo.dao.TableLoginDao;
//////import com.demo.service.TableLoginService;
//////import org.springframework.beans.factory.annotation.Autowired;
//////import org.springframework.stereotype.Service;
//////
//////import java.util.Optional;
//////
//////@Service
//////public class TableLoginServiceImpl implements TableLoginService {
//////
//////    @Autowired
//////    private TableLoginDao tableLoginDao;
//////
//////    @Override
//////    public TableLogin autoLogin(String tableNumber) {
//////        Optional<TableLogin> tableLoginOpt = tableLoginDao.findByTableNumber(tableNumber);
//////        if (tableLoginOpt.isPresent()) {
//////            return tableLoginOpt.get();
//////        }
//////        throw new RuntimeException("Table not found");
//////    }
//////
//////    @Override
//////    public TableLogin addTableData(String tableNumber, String seat, String password) {
//////        TableLogin newTable = new TableLogin();
//////        newTable.setTableNumber(tableNumber);
//////        newTable.setSeat(seat);
//////        newTable.setPassword(password);
//////        return tableLoginDao.save(newTable); // This saves the new TableLogin entity to the database
//////    }
//////}
//////
////
////
////package com.demo.servicelmpl;
////
////import com.demo.POJO.TableLogin;
////import com.demo.dao.TableLoginDao;
////import com.demo.service.TableLoginService;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.stereotype.Service;
////
////import java.util.Optional;
////
////@Service
////public class TableLoginServiceImpl implements TableLoginService {
////
////    @Autowired
////    private TableLoginDao tableLoginDao;
////
////    @Override
////    public TableLogin autoLogin(String tableNumber) {
////        Optional<TableLogin> tableLoginOpt = tableLoginDao.findByTableNumber(tableNumber);
////        if (tableLoginOpt.isPresent()) {
////            return tableLoginOpt.get();
////        }
////        throw new RuntimeException("Table not found");
////    }
////
////    @Override
////    public TableLogin addTableData(String tableNumber, String seat, String password) {
////        TableLogin newTable = new TableLogin();
////        newTable.setTableNumber(tableNumber);  // Lombok will generate this method automatically
////        newTable.setSeat(seat);  // Lombok will generate this method automatically
////        newTable.setPassword(password);  // Lombok will generate this method automatically
////        return tableLoginDao.save(newTable); // This saves the new TableLogin entity to the database
////    }
////}
////
//
//package com.demo.servicelmpl;
//
//import com.demo.POJO.TableLogin;
//import com.demo.dao.TableLoginDao;
//import com.demo.servicelmpl.QRCodeServiceImpl;
//import com.demo.service.TableLoginService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class TableLoginServiceImpl implements TableLoginService {
//
//    @Autowired
//    private TableLoginDao tableLoginDao;
//
//    @Autowired
//    private QRCodeServiceImpl qrCodeServiceImpl;  // Injecting QRCodeService to generate QR codes
//
//    @Override
//    public TableLogin addTableData(String tableNumber, String seat, String password) {
//        // Create a new TableLogin entity
//        TableLogin newTable = new TableLogin();
//        newTable.setTableNumber(tableNumber);
//        newTable.setSeat(seat);
//        newTable.setPassword(password);
//
//        // Save the table data to the database
//        TableLogin savedTable = tableLoginDao.save(newTable);
//
//        // Generate QR code for the newly added table (after saving to DB)
//        try {
//            qrCodeServiceImpl.generateQRCode(tableNumber);  // Generate the QR code
//        } catch (Exception e) {
//            // Handle error if QR code generation fails
//            System.err.println("Error generating QR code for table " + tableNumber);
//            e.printStackTrace();
//        }
//
//        return savedTable;  // Return the saved table entity
//    }
//}
//

//package com.demo.servicelmpl;
//
//import com.demo.POJO.TableLogin;
//import com.demo.dao.TableLoginDao;
//import com.demo.service.TableLoginService;
//import com.demo.servicelmpl.QRCodeServiceImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class TableLoginServiceImpl implements TableLoginService {
//
//    @Autowired
//    private TableLoginDao tableLoginDao;
//
//    @Autowired
//    private QRCodeServiceImpl qrCodeServiceImpl;  // Inject QR code service to generate QR code
//
//    @Override
//    public TableLogin addTableData(String tableNumber, String seat, String password) {
//        // Create a new TableLogin object
//        TableLogin tableLogin = new TableLogin();
//        tableLogin.setTableNumber(tableNumber);
//        tableLogin.setSeat(seat);
//        tableLogin.setPassword(password);
//
//        // Save the table data to the database
//        tableLoginDao.save(tableLogin);
//
//        // After saving the table, generate the QR code for that table
//        try {
//            qrCodeServiceImpl.generateQRCode(tableNumber); // Generate QR code using the table number
//        } catch (Exception e) {
//            System.err.println("Error generating QR code for table " + tableNumber);
//            e.printStackTrace();
//        }
//
//        return tableLogin; // Return the saved TableLogin object
//    }
//
//    @Override
//    public TableLogin autoLogin(String tableNumber) {
//        // Check if the table exists in the database
//        return tableLoginDao.findByTableNumber(tableNumber)
//                .orElseThrow(() -> new RuntimeException("Table not found with number: " + tableNumber)); // Throw error if not found
//    }
//}

package com.demo.servicelmpl;

import com.demo.POJO.Account;
import com.demo.POJO.Shop;
import com.demo.POJO.TableLogin;
import com.demo.dao.AccountDao;
import com.demo.dao.ShopDao;
import com.demo.dao.TableLoginDao;
import com.demo.dto.TableLoginRequest;
import com.demo.service.TableLoginService;
import com.demo.service.QRCodeService;  // Use the interface, not the implementation class
import com.demo.wrapper.ShopTableWrapper;
import com.demo.wrapper.TableLoginWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TableLoginServiceImpl implements TableLoginService {

    @Autowired
    private TableLoginDao tableLoginDao;

    @Autowired
    private ShopDao shopDao;

    @Autowired
    private QRCodeService qrCodeService;  // Inject the interface instead of the implementation

//    @Override
//    public TableLogin addTableData(String tableNumber, String seat, String password) {
//        // Create a new TableLogin object
//        TableLogin tableLogin = new TableLogin();
//        tableLogin.setTableNumber(tableNumber);
//        tableLogin.setSeat(seat);
//        tableLogin.setPassword(password);
//
//        // Save the table data to the database
//        tableLoginDao.save(tableLogin);
//
//        // After saving the table, generate the QR code for that table
//        try {
//            qrCodeService.generateQRCode(tableNumber,seat,password); // Generate QR code using the table number
//        } catch (Exception e) {
//            System.err.println("Error generating QR code for table " + tableNumber);
//            e.printStackTrace();
//        }
//
//        return tableLogin; // Return the saved TableLogin object
//    }

    @Override
    public TableLogin addTableData(String tableNumber, String seat, String password, Integer shopId) {
        // Create a new TableLogin object
        TableLogin tableLogin = new TableLogin();
        tableLogin.setTableNumber(tableNumber);
        tableLogin.setSeat(seat);
        tableLogin.setPassword(password);

        // Fetch the Shop entity and set it
        Optional<Shop> optionalShop = shopDao.findById(shopId);
        if (optionalShop.isEmpty()) {
            throw new RuntimeException("Shop not found with ID: " + shopId);
        }
        tableLogin.setShop(optionalShop.get());

        // Save the table data to the database
        tableLoginDao.save(tableLogin);

        // Generate QR code
        try {
            qrCodeService.generateQRCode(tableNumber, seat, password, shopId);
        } catch (Exception e) {
            System.err.println("Error generating QR code for table " + tableNumber);
            e.printStackTrace();
        }

        return tableLogin;
    }


    @Override
    public List<ShopTableWrapper> getTablesGroupedByShop() {
        List<TableLogin> tables = tableLoginDao.findAll();

        // Group tables by shop
        Map<Shop, List<TableLogin>> grouped = tables.stream()
                .collect(Collectors.groupingBy(TableLogin::getShop));

        List<ShopTableWrapper> result = new ArrayList<>();

        for (Map.Entry<Shop, List<TableLogin>> entry : grouped.entrySet()) {
            Shop shop = entry.getKey();
            List<TableLogin> shopTables = entry.getValue();

            ShopTableWrapper wrapper = new ShopTableWrapper();
            wrapper.setShopId(shop.getId());
            wrapper.setShopName(shop.getName());
            wrapper.setTables(
                    shopTables.stream().map(this::convertToWrapper).collect(Collectors.toList())
            );

            result.add(wrapper);
        }

        return result;
    }


    @Override
    public List<TableLoginWrapper> getTablesByShopId(Integer shopId) {
        List<TableLogin> tables = tableLoginDao.findByShopId(shopId);
        return tables.stream().map(this::convertToWrapper).collect(Collectors.toList());
    }

    private TableLoginWrapper convertToWrapper(TableLogin table) {
        TableLoginWrapper wrapper = new TableLoginWrapper();
        wrapper.setId(table.getId());
        wrapper.setTableNumber(table.getTableNumber());
        wrapper.setSeat(table.getSeat());
        wrapper.setPassword(table.getPassword());
        wrapper.setStatus(table.getStatus());
        wrapper.setShopId(table.getShop().getId()); // assuming your wrapper includes shopId
        return wrapper;
    }



//    @Override
//    public TableLogin editTableData(Long id, TableLoginRequest tableLoginRequest) {
//        Optional<TableLogin> existingTable = tableLoginDao.findById(id.intValue());
//        if (existingTable.isPresent()) {
//            TableLogin tableLogin = existingTable.get();
//            tableLogin.setTableNumber(tableLoginRequest.getTableNumber());
//            tableLogin.setSeat(tableLoginRequest.getSeat());
//            tableLogin.setPassword(tableLoginRequest.getPassword());
//
//            if (tableLoginRequest.getStatus() != null && !tableLoginRequest.getStatus().isEmpty()) {
//                tableLogin.setStatus(tableLoginRequest.getStatus());
//            }
//            tableLoginDao.save(tableLogin);
//
//            try {
//                qrCodeService.generateQRCode(tableLoginRequest.getTableNumber(), tableLoginRequest.getSeat(), tableLoginRequest.getPassword(),tableLoginRequest.getShopId());
//            } catch (Exception e) {
//                System.err.println("Error generating QR code for table " + tableLoginRequest.getTableNumber());
//                e.printStackTrace();
//            }
//
//            return tableLogin;
//        }
//        throw new RuntimeException("Table not found with ID: " + id);
//    }

    @Override
    public TableLogin editTableData(Long id, TableLoginRequest tableLoginRequest) {
        Optional<TableLogin> existingTable = tableLoginDao.findById(id.intValue()); // Works if your ID is int

        if (existingTable.isPresent()) {
            TableLogin tableLogin = existingTable.get();

//            tableLogin.setTableNumber(tableLoginRequest.getTableNumber());
            tableLogin.setSeat(tableLoginRequest.getSeat());
            tableLogin.setPassword(tableLoginRequest.getPassword());

            if (tableLoginRequest.getStatus() != null && !tableLoginRequest.getStatus().isEmpty()) {
                tableLogin.setStatus(tableLoginRequest.getStatus());
            }

            tableLoginDao.save(tableLogin);

            // Regenerate QR Code
            try {
                qrCodeService.generateQRCode(
                        tableLoginRequest.getTableNumber(),
                        tableLoginRequest.getSeat(),
                        tableLoginRequest.getPassword(),
                        tableLoginRequest.getShopId() // May be null
                );
            } catch (Exception e) {
                System.err.println("Error generating QR code for table " + tableLoginRequest.getTableNumber());
                e.printStackTrace();
            }

            return tableLogin;
        }

        throw new RuntimeException("Table not found with ID: " + id);
    }

    @Autowired
    AccountDao accountDao;
    public List<TableLogin> getTablesForLoggedInEmployee(String email) {
        Account employee = accountDao.findByEmail(email);
        if (employee == null || employee.getShop() == null) {
            throw new RuntimeException("Employee or Shop not found.");
        }

        Integer shopId = employee.getShop().getId();
        return tableLoginDao.findByShopId(shopId); // This should be implemented in your DAO
    }


    @Override
    public List<TableLogin> getAllTables() {
        return tableLoginDao.findAll();
    }
    @Override
    public TableLogin autoLogin(String tableNumber, String seat, String password) {
        // Check if the table exists in the database
        return tableLoginDao.findByTableNumberAndSeatAndPassword(tableNumber,seat,password)
                .orElseThrow(() -> new RuntimeException("Table not found with number: " + tableNumber)); // Throw error if not found
    }
    @Override
    public ResponseEntity<String> updateTableStatus(int tableNumber, String status) {
        Optional<TableLogin> tableOptional = tableLoginDao.findById(tableNumber);

        if (tableOptional.isPresent()) {
            TableLogin table = tableOptional.get();
            table.setStatus(status);
            tableLoginDao.save(table);
            return ResponseEntity.ok("Table status updated successfully.");
        } else {
            return ResponseEntity.badRequest().body("Table not found.");
        }
    }
}

