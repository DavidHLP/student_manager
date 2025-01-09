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
public class College {
    private Integer id;             // 书院 ID
    private String name;            // 书院名称
    private String departmentId;    // 所属部门
    private String description;     // 书院描述
    private LocalDateTime createdAt; // 创建时间
    private LocalDateTime updatedAt; // 更新时间
}