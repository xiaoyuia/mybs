package com.zy.mybs.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy.mybs.pojo.Clazz;
import com.zy.mybs.service.ClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clazz")  //前端页面路径
public class ClazzController {
    @Autowired
    ClazzService clazzService;


    //查询所有数据
    @GetMapping("/")
    public List<Clazz> findAll() {
        return clazzService.list();
    }

    //删除单个数据
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id) {
        return clazzService.removeById(id);
    }

    //分页查询
    @GetMapping("/page")
    public IPage<Clazz> findPage(@RequestParam Integer pageNum,
                                 @RequestParam Integer pageSize,
                                 @RequestParam(defaultValue = "") String name,
                                 @RequestParam(defaultValue = "") String gradeName,
                                 @RequestParam(defaultValue = "") String clazzName) {


        IPage<Clazz> page = new Page<>(pageNum,pageSize);
        LambdaQueryWrapper<Clazz> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        //若gradeName和clazzName都为空，则查询全部
        if(gradeName.equals("") && clazzName.equals("")) {
            return clazzService.page(page);
        }

        if (!gradeName.equals("")) {
            lambdaQueryWrapper.eq(Clazz::getGradeName,gradeName);
        }
        if (!clazzName.equals("")) {
            lambdaQueryWrapper.eq(Clazz::getName,clazzName);
        }
        return clazzService.page(page,lambdaQueryWrapper);

    }

    //新增和修改
    @PostMapping("")
    public boolean save(@RequestBody Clazz clazz) {
        return clazzService.saveOrUpdate(clazz);
    }

    //批量删除
    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids) {
        return clazzService.removeBatchByIds(ids);
    }

    //班级下拉列表（根据年级搜索班级）
    @GetMapping("/getByGradeName")
    public List<Clazz> getByGrade(@RequestParam(defaultValue = "") String gradeName) {
        //若gradeName为空，则默认查询全部
        if (gradeName.equals("")) {
            return clazzService.list();
        }

        LambdaQueryWrapper<Clazz> clazzLambdaQueryWrapper = new LambdaQueryWrapper<>();
        clazzLambdaQueryWrapper.eq(Clazz::getGradeName,gradeName);
        return clazzService.list(clazzLambdaQueryWrapper);
    }

}
