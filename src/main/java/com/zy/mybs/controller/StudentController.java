package com.zy.mybs.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy.mybs.config.AddData;
import com.zy.mybs.pojo.Clazz;
import com.zy.mybs.pojo.Score;
import com.zy.mybs.pojo.Student;
import com.zy.mybs.service.ClazzService;
import com.zy.mybs.service.ScoreService;
import com.zy.mybs.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.misc.Request;

import java.util.List;

@RestController
@RequestMapping("/student")  //前端页面路径
public class StudentController {
    @Autowired
    StudentService studentService;
    @Autowired
    ScoreService scoreService;
    @Autowired
    ClazzService clazzService;

    //查询所有数据
    @GetMapping("/")
    public List<Student> findAll() {
        return studentService.list();
    }

    //删除单个数据
    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        //将成绩表中对应的学生删除
        Student student = studentService.getById(id);
        Score score = scoreService.getScoreSno(student);
        scoreService.removeById(score.getId());

        return studentService.removeById(id);
    }

    //分页查询  (defaultValue = "" 默认值为空)
    @GetMapping("/page")
    public IPage<Student> findPage(@RequestParam Integer pageNum,
                                   @RequestParam Integer pageSize,
                                   @RequestParam(defaultValue = "") String name,
                                   @RequestParam(defaultValue = "") String clazzName,
                                   @RequestParam(defaultValue = "") String gradeName
                                   ) {
        IPage<Student> page = new Page<>(pageNum,pageSize);
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();

        //若都为空则返回全部
        if(gradeName.equals("") && clazzName.equals("") && name.equals("")) {
            return studentService.page(page);
        }

        //若年级不为空,班级为空
        if(!gradeName.equals("") && clazzName.equals("")) {
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
        return studentService.page(page, queryWrapper);
    }

    //新增和修改
    @PostMapping("")
    public boolean save(@RequestBody Student student) {


        //修改学生表时对相应成绩表进行修改
        scoreService.saveOrUpdateByStudent(student);

        //默认密码为123456
        student.setPassword("123456");

        return studentService.saveOrUpdate(student);
    }

//    //数据添加
//    @PostMapping("")
//    public boolean save() {
//        Student student = AddData.addStudent();
//        student.setPassword("123456");
//        scoreService.saveOrUpdateByStudent(student);
//        return studentService.saveOrUpdate(student);
//    }

    //批量删除
    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids) {
        //将成绩表中对应的学生删除
        for (int i = 0; i < ids.size(); i++) {
            Student student = studentService.getById(ids.get(i));
            Score score = scoreService.getScoreSno(student);
            scoreService.removeById(score.getId());
        }
        return studentService.removeBatchByIds(ids);
    }




//    @GetMapping("/page")
//    public Map<String,Object> findPage(@RequestParam Integer pageNum,@RequestParam Integer pageSize) {
//        pageNum = (pageNum - 1) * pageSize;
//        List<Student> data = studentMapper.selectPage(pageNum, pageSize);
//        Integer total = studentMapper.selectTotal();
//        Map<String,Object> res = new HashMap<>();
//        res.put("data",data);
//        res.put("total",total);
//        return res;
//    }





}
