package com.zy.mybs.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy.mybs.mapper.StudentMapper;
import com.zy.mybs.pojo.LoginForm;
import com.zy.mybs.pojo.Student;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentService extends ServiceImpl<StudentMapper, Student> {
//    public boolean login(LoginForm loginForm) {
//        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("name",loginForm.getUsername());
//        queryWrapper.eq("password",loginForm.getPassword());
//        Student one = getOne(queryWrapper);
//        return one != null;
//
//    }
    @Transactional
    public Student login(LoginForm loginForm) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",loginForm.getUsername());
        queryWrapper.eq("password",loginForm.getPassword());
        Student student = baseMapper.selectOne(queryWrapper);
        return student;
    }
}
