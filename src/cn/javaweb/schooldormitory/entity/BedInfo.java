package cn.javaweb.schooldormitory.entity;

import cn.javaweb.base.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BedInfo {
    private int id;
    private int dormitoryId;
    private int bedNumber;
    private String bedInfo;
    private String collegeDormitoryBedNumber;
    private Integer userId;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private User user;
}
