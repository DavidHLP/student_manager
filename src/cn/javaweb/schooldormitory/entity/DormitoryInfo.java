package cn.javaweb.schooldormitory.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
public class DormitoryInfo{
    private Integer id; // 宿舍ID
    private String name; // 宿舍名称
    private String building; // 楼栋
    private Integer collegeId; // 所属书院ID
    private Integer capacity; // 容纳人数
    private Integer occupied; // 已入住人数
    private LocalDateTime createdAt; // 创建时间
    private LocalDateTime updatedAt; // 更新时间
    private List<BedInfo> beds;
    private BedInfo bed;
}
