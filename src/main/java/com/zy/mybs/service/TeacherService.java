package com.zy.mybs.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy.mybs.mapper.TeacherMapper;
import com.zy.mybs.pojo.LoginForm;
import com.zy.mybs.pojo.Teacher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TeacherService extends ServiceImpl<TeacherMapper, Teacher> {
//
//    public boolean login(LoginForm loginForm) {
//        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("name",loginForm.getUsername());
//        queryWrapper.eq("password",loginForm.getPassword());
//        try {
//            Teacher one = getOne(queryWrapper);
//            return one != null;
//        }catch (Exception e) {
//            return false;
//        }
//    }

    @Transactional
    public Teacher login(LoginForm loginForm) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",loginForm.getUsername());
        queryWrapper.eq("password",loginForm.getPassword());
        Teacher teacher = baseMapper.selectOne(queryWrapper);
        return teacher;
    }
}
