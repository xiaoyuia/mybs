package com.zy.mybs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy.mybs.pojo.Clazz;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface ClazzMapper extends BaseMapper<Clazz> {
}
