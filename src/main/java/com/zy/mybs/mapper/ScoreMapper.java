package com.zy.mybs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy.mybs.pojo.Score;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoreMapper extends BaseMapper<Score> {

    @Select("select chinese+math+english+geography+history+politics+physics+chemistry+biology as gross_score from tb_score where id = #{id};")
    Integer sum(Score score);
}
