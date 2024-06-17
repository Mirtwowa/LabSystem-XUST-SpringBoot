package com.oastore.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oastore.pojo.Bookstorage;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookStorageMapper extends BaseMapper<Bookstorage> {
    @Select("select * from `bookstorage`")
    List<Bookstorage> selectAll();

    @Insert("insert into `bookstorage` (itemname , price, num)"+
            "values (#{itemname},#{price},#{num})")
     void insertItem(Bookstorage bookstorage);
    @Delete("delete from `bookstorage` where itemname=#{itemname}")
    void delete(String employee);
}
