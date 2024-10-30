package com.oastore.mapper;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ReservationMapper {
    @Select("select count(*) from reserved where start_time < #{end} and end_time> #{start}")
    int searchUsable(LocalDateTime start, LocalDateTime end);

    @Select("insert into reserved(start_time,end_time,reserved_by)" +
            "values (#{start},#{end},#{name})")
    void updateTime(String name, LocalDateTime start, LocalDateTime end);
}
