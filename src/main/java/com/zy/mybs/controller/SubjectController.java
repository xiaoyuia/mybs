package com.zy.mybs.controller;

import com.zy.mybs.pojo.Subject;
import com.zy.mybs.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/subject")
public class SubjectController {

    @Autowired
    SubjectService subjectService;

    @GetMapping("/")
    public List<Subject> findAll() {
        return subjectService.list();
    }

}
