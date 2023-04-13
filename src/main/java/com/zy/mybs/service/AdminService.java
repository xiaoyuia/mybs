package com.zy.mybs.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy.mybs.mapper.AdminMapper;
import com.zy.mybs.pojo.Admin;
import com.zy.mybs.pojo.LoginForm;
import org.springframework.stereotype.Service;

@Service
public class AdminService extends ServiceImpl<AdminMapper, Admin> {
//    public boolean login(LoginForm loginForm) {
//        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("name",loginForm.getUsername());
//        queryWrapper.eq("password",loginForm.getPassword());
//        try {
//            Admin one = getOne(queryWrapper);
//            return one != null;
//        }catch (Exception e) {
//            return false;
//        }
//    }

    public Admin login(LoginForm loginForm) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",loginForm.getUsername());
        queryWrapper.eq("password",loginForm.getPassword());
        Admin admin = baseMapper.selectOne(queryWrapper);
        return admin;
    }
}
