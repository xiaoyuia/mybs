package com.zy.mybs.config;

import java.util.Locale;
import java.util.Random;

import com.github.javafaker.Faker;
import com.zy.mybs.pojo.Score;
import com.zy.mybs.pojo.Student;

public class AddData {
    static int suffixInt =1;
    //添加学生
    public static Student addStudent() {
        Faker faker = new Faker(new Locale("zh-CN"));



        String name = faker.name().fullName();
        String address = "南昌";
        String phoneNumber = faker.phoneNumber().cellPhone();
        String sex = faker.options().option("男", "女");
        String email = phoneNumber + "@" + faker.options().option("gmail.com","qq.com","163.com");
        String clazzName = faker.options().option("七年一班","八年一班","九年一班");


        //sno前缀
        String prefix = "23";
        //sno后缀
        String suffix = Integer.toString(suffixInt);
        suffixInt++;
        //sno
        String sno = null;

        if (clazzName.equals("七年一班")){
            sno = prefix + "71" + suffix;
        }
        if (clazzName.equals("八年一班")){
            sno = prefix + "81" + suffix;
        }
        if (clazzName.equals("九年一班")){
            sno = prefix + "91" + suffix;
        }

        char gender = sex.charAt(0);
        Student student = new Student();
        student.setName(name);
        student.setGender(gender);
        student.setEmail(email);
        student.setClazzName(clazzName);
        student.setAddress(address);
        student.setSno(sno);
        student.setTelephone(phoneNumber);
        return student;
    }

    //添加成绩
    public static Score addScore(Score score) {
        Faker faker = new Faker(new Locale("zh-CN"));
        score.setChinese(faker.number().numberBetween(0, 120));
        score.setMath(faker.number().numberBetween(0, 120));
        score.setEnglish(faker.number().numberBetween(0, 120));
        score.setPhysics(faker.number().numberBetween(0, 85));
        score.setPolitics(faker.number().numberBetween(0, 80));
        score.setHistory(faker.number().numberBetween(0, 80));
        score.setBiology(faker.number().numberBetween(0, 55));
        score.setGeography(faker.number().numberBetween(0, 50));
        score.setChemistry(faker.number().numberBetween(0, 75));
        int grossScore = score.getChinese() + score.getMath() + score.getEnglish() + score.getPhysics() + score.getPolitics() + score.getHistory() + score.getBiology() + score.getGeography() + score.getChemistry();
        score.setGrossScore(grossScore);
        return score;
    }



    public static void main(String[] args) {
        addStudent();
    }
}
