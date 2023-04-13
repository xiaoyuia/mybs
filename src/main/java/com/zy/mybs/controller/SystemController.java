package com.zy.mybs.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.zy.mybs.pojo.Admin;
import com.zy.mybs.pojo.LoginForm;
import com.zy.mybs.pojo.Student;
import com.zy.mybs.pojo.Teacher;
import com.zy.mybs.service.AdminService;
import com.zy.mybs.service.StudentService;
import com.zy.mybs.service.TeacherService;
import com.zy.mybs.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/system")
public class SystemController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private TeacherService teacherService;

    @Value("${files.upload.path}")
    private String fileUploadPath;


    //登录
    @PostMapping("/login")
    public Result login(@RequestBody LoginForm loginForm, HttpServletRequest request) {

        //准备一个map用户存放响应的数据
        Map<String,Object> map = new LinkedHashMap<>();
        //分用户类型进行校验
        switch (loginForm.getUserType()) {
            case 1 :
                try {
                    Admin admin = adminService.login(loginForm);
                    if (null != admin) {
                        String userType = "管理员：";
                        map.put("userType",userType);
                        map.put("typeNum",loginForm.getUserType());
                        map.put("user",admin);
                        request.getSession().setAttribute("user",admin.getId());
                    } else {
                        throw new RuntimeException("用户名或密码有误");
                    }
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }
            case 2 :
                try {
                    Teacher teacher = teacherService.login(loginForm);
                    if (null != teacher) {
                        String userType = "教师：";
                        map.put("userType",userType);
                        map.put("typeNum",loginForm.getUserType());
                        map.put("user",teacher);
                        request.getSession().setAttribute("user",teacher.getId());
                    } else {
                        throw new RuntimeException("用户名或密码有误");
                    }
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }
            case 3 :
                try {
                    Student student = studentService.login(loginForm);
                    if (null != student) {
                        String userType = "学生：";
                        map.put("userType",userType);
                        map.put("typeNum",loginForm.getUserType());
                        map.put("user",student);
                        request.getSession().setAttribute("user",student.getId());
                    } else {
                        throw new RuntimeException("用户名或密码有误");
                    }
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }
        }
        return Result.fail().message("查无此用户");
    }



    //头像上传
    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);
        //先存储到磁盘
        File uploadParentFile = new File(fileUploadPath);
        //判断配置的文件目录是否存在，若不存在则创建一个新的文件目录
        if(!uploadParentFile.exists()) {
            uploadParentFile.mkdirs();
        }
        //定义一个文件唯一的标识码
        String uuid = IdUtil.fastSimpleUUID();
        String fileName = uuid + StrUtil.DOT + type;
        File uploadFile = new File(fileUploadPath + fileName);
        //把获取到的文件存储到磁盘目录
        file.transferTo(uploadFile);

//        String path = "http://localhost:8085/system/upload" + fileName;
        String path = "http://localhost:8085/upload/" + fileName;
        return path;
    }



//    @GetMapping("")
//    public Result getInfoByToken(@RequestHeader("token") String token) {
//        //判断token是否过期
//        boolean expiration = JwtHelper.isExpiration(token);
//        if (expiration) {
//            return Result.build(null, ResultCodeEnum.TOKEN_ERROR);
//        }
//        //从token中解析出 用户id 和 用户类型
//        Long userId = JwtHelper.getUserId(token);
//        Integer userType = JwtHelper.getUserType(token);
//
//        Map<String,Object> map = new LinkedHashMap<>();
//        switch (userType) {
//            case 1:
//                Admin admin = adminService.getAdminById(userId);
//                map.put("userType",1);
//                map.put("user",admin);
//                break;
//            case 2:
//                break;
//            case 3:
//                break;
//
//        }
//
//    }

}
