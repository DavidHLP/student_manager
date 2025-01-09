package cn.javaweb.course.model;

import cn.javaweb.course.entity.Course;
import cn.javaweb.course.entity.Enrollment;
import cn.javaweb.library.BaseModel;
import cn.javaweb.library.Config;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class EnrollmentModel extends BaseModel {
    public EnrollmentModel(Config conf){
        super(conf);
    }

    public Object parseObject(ResultSet rs){
        Enrollment enrollment = new Enrollment();
        try{
            enrollment.setId(rs.getInt("id"));
            enrollment.setStudentId(rs.getInt("student_id"));
            enrollment.setCourseId(rs.getInt("course_id"));
            enrollment.setStatus(rs.getInt("status"));
            enrollment.setScore(rs.getFloat("score"));
            enrollment.setEnrollmentTime(rs.getDate("enrollment_time"));
            enrollment.setUsername(rs.getString("username"));
            enrollment.setRealname(rs.getString("realname"));

            return enrollment;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return enrollment;

    }

    public List<Object> getStudents(Integer courseId){
        String sql = "select enrollment.*, user.username, user.realname from enrollment left join user on user.id=enrollment.student_id where course_id=?";
        Object [] params = { courseId };

        return selectAll(sql, params);
    }

    public boolean confirm(int studentId, int courseId, int action)throws SQLException {
        String sql = "select * from enrollment where student_id=? and course_id=?";
        Object [] params = { studentId, courseId };
        Integer status = (Integer) getValue(sql, params, "status", "int");
        if( status == 1 && action == 1 ){
            sql = "update enrollment set status=2 where student_id=? and course_id=?";
            return update(sql, params)>0;
        }else if( status == 1 && action == 2 ){
            sql = "delete from enrollment where student_id=? and course_id=?";
            return update(sql, params)>0;
        }
        return false;
    }
}
