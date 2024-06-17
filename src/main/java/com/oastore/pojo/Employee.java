package com.oastore.pojo;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    private Integer id;
    private String username;
    @Email
    private String email;
    private String gender;
    private String department;
    private String birth;
}
