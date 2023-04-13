package com.zy.mybs.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy.mybs.config.AddData;
import com.zy.mybs.mapper.ScoreMapper;
import com.zy.mybs.pojo.Score;
import com.zy.mybs.pojo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ScoreService extends ServiceImpl<ScoreMapper, Score> {

    //将成绩表中对应学生表学号的信息获取返回给用户， 在删除学生的时候删除成绩表中对应的学生
    @Transactional
    public Score getScoreSno(Student student) {
        QueryWrapper<Score> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sno",student.getSno());
        Score score = baseMapper.selectOne(queryWrapper);
        return score;
    }

    //当添加或修改学生表时，对成绩表进行相应修改
    @Transactional
    public void saveOrUpdateByStudent(Student student) {
        QueryWrapper<Score> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sno",student.getSno());
        Score score = baseMapper.selectOne(queryWrapper);
        //判断是否存在
        if (score == null) {
            Score score1 = new Score();
            score1.setSno(student.getSno());
            score1.setName(student.getName());
            score1.setClazzName(student.getClazzName());
            saveOrUpdate(score1);
        }else {
            score.setSno(student.getSno());
            score.setName(student.getName());
            score.setClazzName(student.getClazzName());
            saveOrUpdate(score);
        }
    }

}
