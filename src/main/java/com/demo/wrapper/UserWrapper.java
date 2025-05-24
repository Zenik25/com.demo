package com.demo.wrapper;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserWrapper {
//    UserWrapper user = new com.demo.wrapper.UserWrapper(1,"abc","abc@gmail.com","5646","false");
    private Integer id;
    private String name;
    private String email;

    private String contactNumber;
    private String status;
    private String password;
    private String role;// ✅ Add this field

//    public UserWrapper(Integer id, String name, String email, String contactNumber, String status, String password) {
//        this.id = id;
//        this.name = name;
//        this.email = email;
//        this.contactNumber = contactNumber;
//        this.status = status;
//        this.password = password; // ✅ Assign it
public UserWrapper(Integer id, String name, String contactNumber, String email, String status, String password) {
    this.id = id;
    this.name = name;
    this.contactNumber = contactNumber;
    this.email = email;
    this.status = status;
    this.password = password;

}
    public UserWrapper(Integer id, String name, String email, String role, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.password = password;
    }
}
