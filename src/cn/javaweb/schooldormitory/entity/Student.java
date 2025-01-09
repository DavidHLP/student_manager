package cn.javaweb.schooldormitory.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student {
    private Integer id; // 学生ID
    private String username; // 学号
    private String realname; // 姓名
    private Integer grade; // 年级
    private String className; // 班级
    private String status; // 状态 ("DISABLED" 或 "ACTIVE")
    private Integer depId; // 学院ID
    private LocalDateTime createdAt; // 创建时间
    private LocalDateTime updatedAt; // 更新时间
    private DormitoryInfo dormitoryInfo;
    private String collegeDormitoryBedNumber;
    private Integer nowBedId;
    private Integer prevBedId;
}
