package com.zy.mybs.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_score")
@ToString
public class Score {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String sno;
    private String clazzName;
    private String name;
    private int chinese;
    private int math;
    private int english;
    private int geography;
    private int history;
    private int politics;
    private int physics;
    private int chemistry;
    private int biology;
    private int grossScore;
}
