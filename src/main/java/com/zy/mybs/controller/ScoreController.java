package com.zy.mybs.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy.mybs.mapper.ScoreMapper;
import com.zy.mybs.pojo.Clazz;
import com.zy.mybs.pojo.Score;
import com.zy.mybs.service.ClazzService;
import com.zy.mybs.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/score")
public class ScoreController {
    @Autowired
    ScoreService scoreService;
    @Autowired
    ScoreMapper scoreMapper;
    @Autowired
    ClazzService clazzService;

    //查询所有数据
    @GetMapping("/")
    public List<Score> findAll() {
        return scoreService.list();
    }

    //分页查询(defaultValue = "" 默认值为空)
    @GetMapping("/page")
    public IPage<Score> findPage(@RequestParam Integer pageNum,
                                 @RequestParam Integer pageSize,
                                 @RequestParam(defaultValue = "") String clazzName,
                                 @RequestParam(defaultValue = "") String name,
                                 @RequestParam(defaultValue = "") String subject,
                                 @RequestParam(defaultValue = "") Integer rank,
                                 @RequestParam(defaultValue = "") String gradeName) {
        IPage<Score> page = new Page<>(pageNum,pageSize);
        QueryWrapper<Score> queryWrapper = new QueryWrapper<>();

        //若年级不为空
        if(!gradeName.equals("")) {
            LambdaQueryWrapper<Clazz> clazzLambdaQueryWrapper = new LambdaQueryWrapper<>();
            clazzLambdaQueryWrapper.eq(Clazz::getGradeName,gradeName);
            List<Clazz> list = clazzService.list(clazzLambdaQueryWrapper);
            for (int i = 0; i < list.size(); i++) {
                Clazz clazz = list.get(i);
                queryWrapper.eq("clazz_name",clazz.getName());
            }
        }


        queryWrapper.like("clazz_name",clazzName);
        queryWrapper.like("name",name);
        //若科目不为空，则只查询sno,clazz_name,name,subject
        if (!subject.equals("")) {
            queryWrapper.select("sno","clazz_name","name",subject);
            queryWrapper.orderByDesc(subject);
        }else {
            queryWrapper.orderByDesc("gross_score");

        }
        //若排名不为空，则查询前rank条数据
        if(rank != null) {
            IPage<Score> page1 = new Page<>(pageNum,rank);
            IPage<Score> page2 = scoreService.page(page1,queryWrapper);
            page2.setTotal(rank);
            return page2;
        }
//        queryWrapper.isNotNull(subject);
        return scoreService.page(page,queryWrapper);
    }

    //修改
    @PostMapping("")
    public boolean update(@RequestBody Score score) {
        scoreService.saveOrUpdate(score);
        Integer result = scoreMapper.sum(score);
        score.setGrossScore(result);
        return scoreService.saveOrUpdate(score);
    }
}
