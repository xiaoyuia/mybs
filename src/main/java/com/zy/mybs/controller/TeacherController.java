package com.zy.mybs.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy.mybs.pojo.Clazz;
import com.zy.mybs.pojo.Teacher;
import com.zy.mybs.service.ClazzService;
import com.zy.mybs.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teacher")  //前端页面路径
public class TeacherController {
    @Autowired
    TeacherService teacherService;
    @Autowired
    ClazzService clazzService;

    //查询所有数据
    @GetMapping("/")
    public List<Teacher> findAll() {
        return teacherService.list();
    }

    //删除单个数据
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id) {
        return teacherService.removeById(id);
    }

    //分页查询 (defaultValue = "" 默认值为空)
    @GetMapping("/page")
    public IPage<Teacher> findPage(@RequestParam Integer pageNum,
                                   @RequestParam Integer pageSize,
                                   @RequestParam(defaultValue = "") String name,
                                   @RequestParam(defaultValue = "") String clazzName,
                                   @RequestParam(defaultValue = "") String gradeName) {
        IPage<Teacher> page = new Page<>(pageNum,pageSize);
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();

        //若都为空则返回全部
        if(gradeName.equals("") && clazzName.equals("") && name.equals("")) {
            return teacherService.page(page);
        }

        //若年级不为空
        if(!gradeName.equals("") && clazzName.equals("") ) {
            LambdaQueryWrapper<Clazz> clazzLambdaQueryWrapper = new LambdaQueryWrapper<>();
            clazzLambdaQueryWrapper.eq(Clazz::getGradeName,gradeName);
            List<Clazz> list = clazzService.list(clazzLambdaQueryWrapper);
            for (int i = 0; i < list.size(); i++) {
                Clazz clazz = list.get(i);
                queryWrapper.eq("clazz_name",clazz.getName());
            }
        }
        //班级不为空
        if (!clazzName.equals("")) {
            queryWrapper.eq("clazz_name",clazzName);
        }

        queryWrapper.like("name",name);
        return teacherService.page(page,queryWrapper);
    }

    //新增和修改
    @PostMapping("")
    public boolean save(@RequestBody Teacher teacher) {
        teacher.setPassword("123456");
        return teacherService.saveOrUpdate(teacher);
    }

    //批量删除
    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids) {
        return teacherService.removeBatchByIds(ids);
    }



}
