package com.zy.mybs.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy.mybs.pojo.Admin;
import com.zy.mybs.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")  //前端页面路径
public class AdminController {
    @Autowired
    AdminService adminService;

    //查询所有数据
    @GetMapping("/")
    public List<Admin> findAll() {
        return adminService.list();
    }

    //删除单个数据
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id ) {
        return adminService.removeById(id);

    }

    //分页查询
    @GetMapping("/page")
    public IPage<Admin> findPage(@RequestParam Integer pageNum,
                                 @RequestParam Integer pageSize,
                                 @RequestParam(defaultValue = "") String name) {
        IPage<Admin> page = new Page<>(pageNum,pageSize);
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name",name);
        return adminService.page(page,queryWrapper);
    }

    //新增和修改
    @PostMapping("")
    public boolean save(@RequestBody Admin admin) {
        admin.setPassword("123456");
        return adminService.saveOrUpdate(admin);
    }

    //批量删除
    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids) {
        return adminService.removeBatchByIds(ids);
    }
}
