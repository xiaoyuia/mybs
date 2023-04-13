package com.zy.mybs.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy.mybs.pojo.Grade;
import com.zy.mybs.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grade")  //前端页面路径
public class GradeController {
    @Autowired
    GradeService gradeService;

    //查询所有数据
    @GetMapping("/")
    public List<Grade> findAll() {
        return gradeService.list();
    }

    //删除单个数据
    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return gradeService.removeById(id);
    }

    //分页查询
    @GetMapping("/page")
    public IPage<Grade> findPage(@RequestParam Integer pageNum,
                                 @RequestParam Integer pageSize,
                                 @RequestParam(defaultValue = "") String name) {
        IPage<Grade> page = new Page<>(pageNum,pageSize);
        QueryWrapper<Grade> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name",name);
        return gradeService.page(page,queryWrapper);
    }

    //新增和修改
    @PostMapping("")
    public boolean save(@RequestBody Grade grade) {
        return gradeService.saveOrUpdate(grade);
    }

    //批量删除
    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids) {
        return gradeService.removeBatchByIds(ids);
    }
}
