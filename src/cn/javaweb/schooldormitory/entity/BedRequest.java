package cn.javaweb.schooldormitory.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BedRequest {
    Integer userId;
    Integer nowBedId;
    Integer prevBedId;
}
