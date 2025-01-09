package cn.javaweb.schooldormitory.service;

import cn.javaweb.library.BaseService;
import cn.javaweb.library.Config;
import cn.javaweb.schooldormitory.entity.BedInfo;
import cn.javaweb.schooldormitory.entity.DormitoryInfo;
import cn.javaweb.schooldormitory.entity.PageInfo;
import cn.javaweb.schooldormitory.entity.Student;
import cn.javaweb.schooldormitory.model.BedModel;
import cn.javaweb.schooldormitory.model.CollegeModel;
import cn.javaweb.schooldormitory.model.DormitoryModel;
import cn.javaweb.schooldormitory.model.StudentModel;

import java.sql.SQLException;

public class StudentService extends BaseService {
    private StudentModel studentModel;
    private DormitoryModel dormitoryModel;
    private BedModel bedModel;
    private CollegeModel collegeModel;

    public StudentService(Config config) {
        super(config);
        studentModel = new StudentModel(this.getConnection());
        dormitoryModel = new DormitoryModel(this.getConnection());
        bedModel = new BedModel(this.getConnection());
        collegeModel = new CollegeModel(this.getConnection());
    }

    public PageInfo<Student> getStudentList(int page, int limit, String studentName) {
        try {
            PageInfo<Student> studentPageInfo = studentModel.getStudentList(page, limit, studentName);
            for (Student student : studentPageInfo.getList()) {
                BedInfo bedInfo = bedModel.getBedByUserId(student.getId());
                if (bedInfo == null) continue;
                String[] strings = bedInfo.getBedInfo().split("-");
                bedInfo.setCollegeDormitoryBedNumber(
                        collegeModel.getCollegeNameByCollegeId(Integer.parseInt(strings[0]))+
                                dormitoryModel.getDormitoryNameBydDormitoryId(Integer.parseInt(strings[1]))+strings[2]+"床位"
                );
                DormitoryInfo dormitoryInfo = dormitoryModel.getDormitoryBydDormitoryId(bedInfo.getDormitoryId());
                dormitoryInfo.setBed(bedInfo);
                student.setDormitoryInfo(dormitoryInfo);
                student.setCollegeDormitoryBedNumber(bedInfo.getCollegeDormitoryBedNumber());
                student.setNowBedId(bedInfo.getId());
                student.setPrevBedId(bedInfo.getId());
            }
            this.commitTransaction();
            return studentPageInfo;
        } catch (SQLException e) {
            try {
                this.rollbackTransaction();
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
            e.printStackTrace();
            return null;
        }
    }
}
