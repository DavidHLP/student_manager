package cn.javaweb.schooldormitory.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageInfo <T> {
    private int total;
    private int pages;
    private int rows;
    private List<T> list;
}
