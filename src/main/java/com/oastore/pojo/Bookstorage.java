package com.oastore.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bookstorage {
    private String itemname;
    private Float price;
    private Integer num;
}
