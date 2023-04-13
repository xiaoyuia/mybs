package com.zy.mybs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy.mybs.pojo.Student;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentMapper extends BaseMapper<Student> {

//    @Select("select * from tb_student limit #{pageNum},#{pageSize}")
//    List<Student> selectPage(Integer pageNum,Integer pageSize);
//
//    @Select("select count(*) from tb_student")
//    Integer selectTotal();


}
