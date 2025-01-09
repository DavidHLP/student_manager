package cn.javaweb.course.model;

import cn.javaweb.base.entity.IDInfo;
import cn.javaweb.base.entity.PageInfo;
import cn.javaweb.course.entity.Course;
import cn.javaweb.library.BaseModel;
import cn.javaweb.library.Config;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseModel extends BaseModel {
    public CourseModel(Config conf){
        super(conf);
    }

    public Object parseObject(ResultSet rs){
        Course course = new Course();
        try{
            course.setId(rs.getInt("id"));
            course.setCourseName(rs.getString("course_name"));
            course.setCourseTime(rs.getString("course_time"));
            course.setStatus(rs.getInt("status"));
            course.setRemark(rs.getString("remark"));
            course.setCredit(rs.getInt("credit"));
            course.setLocation(rs.getString("location"));
            course.setCapacity(rs.getInt("capacity"));
            course.setTerm(rs.getString("term"));
            course.setTeacherId(rs.getInt("teacher_id"));
            course.setRealname(rs.getString("realname"));
            course.setEstatus(rs.getInt("estatus"));
            course.setEtime(rs.getDate("etime"));
            course.setScore(rs.getFloat("score"));

            return course;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return course;

    }


    public HashMap<String,Object> getPageData(PageInfo pi, HashMap<String, Object> params){
        HashMap<String,Object> result = new HashMap<>();
        if(params.size()<1){
            result.put("total", getCount("select count(*) as cnt from course where 1", null));
            result.put("rows", selectAll("select course.*, user.realname from course left join user on user.id=course.teacher_id where 1 limit " + pi.getOffset() + " , " + pi.getLimit(), null));

        }else{
            String where = "";
            List<Object> p = new ArrayList<>();
            for(Map.Entry<String, Object> entry: params.entrySet()){
                if(where.length()==0){
                    where = entry.getKey() + " = ? ";
                }else{
                    where += " and " + entry.getKey() + " = ? ";
                }
                p.add(entry.getValue());
            }
            String sql = "select count(*) as cnt from course where " + where;
            result.put("total", getCount(sql, p.toArray()));
            sql = "select course.*, user.realname from course left join user on user.id=course.teacher_id where " + where;
            result.put("rows", selectAll(sql, p.toArray()));
        }

        return result;
    }


    public HashMap<String,Object> getPageData2(PageInfo pi, HashMap<String, Object> params, Integer userId){
        HashMap<String,Object> result = new HashMap<>();
        if(params.size()<1){
            Object [] p = {userId};
            result.put("total", getCount("select count(*) as cnt from course where status=1", null));
            result.put("rows", selectAll("select course.*, user.realname, enrollment.status estatus, enrollment.enrollment_time etime, enrollment.score from course left join user on user.id=course.teacher_id left join enrollment on enrollment.course_id=course.id and enrollment.student_id=? where course.status=1 limit " + pi.getOffset() + " , " + pi.getLimit(), p));

        }else{
            String where = " course.status = 1 ";
            List<Object> p = new ArrayList<>();
            for(Map.Entry<String, Object> entry: params.entrySet()){
                where += " and " + entry.getKey() + " = ? ";
                p.add(entry.getValue());
            }
            String sql = "select count(*) as cnt from course where " + where;
            result.put("total", getCount(sql, p.toArray()));
            sql = "select course.*, user.realname, enrollment.status estatus, enrollment.enrollment_time etime, enrollment.score from course left join user on user.id=course.teacher_id left join enrollment on enrollment.course_id=course.id and enrollment.student_id=? where " + where;
            result.put("rows", selectAll(sql, p.toArray()));
        }

        return result;
    }

    public boolean add(Course course) throws SQLException {
        String sql = "insert into course (course_name, credit, status, remark, teacher_id, capacity, location, course_time, term) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Object [] params = {
            course.getCourseName(), course.getCredit(), course.getStatus(), course.getRemark(), course.getTeacherId(),
            course.getCapacity(), course.getLocation(), course.getCourseTime(), course.getTerm()
        };
        return update(sql, params)>0;
    }

    public boolean edit(Course course) throws SQLException {
        String sql = "update course set course_name=?, credit=?, status=?, remark=?, teacher_id=?, capacity=?, location=?, course_time=?, term=? where id=?";
        Object [] params = {
            course.getCourseName(), course.getCredit(), course.getStatus(), course.getRemark(), course.getTeacherId(),
            course.getCapacity(), course.getLocation(), course.getCourseTime(), course.getTerm(), course.getId()
        };
        return update(sql, params)>0;
    }

    public boolean del(IDInfo idInfo) throws SQLException{
        String sql = "delete from course where id=?";
        Object [] params = {idInfo.getId()};
        return update(sql, params)>0;
    }


    public List<Object> getMyCourse(Integer userId){
        String sql = "select course.*, enrollment.status estatus, enrollment.score, enrollment_time etime, realname from enrollment, course left join user on user.id=course.teacher_id where student_id=? and enrollment.course_id=course.id order by enrollment_time desc";
        Object [] params = { userId };
        List<Object> nodes = selectAll(sql, params);
        if(nodes.size()<1){
            return null;
        }

        return nodes;
    }

    public List<Object> getCourseStatus(String ids){
        List<Object> result = new ArrayList<>();
        String[] idList = ids.split(",");
        if(idList.length==0) return null;
        for(String id: idList){
            Object[] params = {id};
            String sql = "select capacity from course where id=?";
            Integer capacity = (Integer) getValue(sql, params, "capacity", "int");

            sql = "select count(*) as cnt from enrollment where course_id=?";
            Integer current = (Integer) getValue(sql, params, "cnt", "int");

            HashMap<String, Integer> one = new HashMap<>();
            one.put("id", Integer.valueOf(id));
            one.put("total", capacity);
            one.put("left", capacity - current);

            result.add(one);
        }

        return result;
    }

    /**
     *
     * @param userId
     * @param courseId
     * @param action 1 选课 2 退选
     * @return
     */
    public String chooseCourse(Integer userId, Integer courseId, Integer action) throws InterruptedException , SQLException{
        String result = "";

        String sql = "select course.*, enrollment.status estatus, enrollment.enrollment_time etime, enrollment.score from course left join enrollment on enrollment.course_id=course.id and enrollment.student_id=? where course.id=?";
        Object [] params = { userId, courseId };

        // 课程及选课信息
        Course course = (Course)selectOne(sql, params);
        System.out.println(course);
        if( action==1 ){ //选课
            // 查询1 - 该课程已选人数
            sql = "select count(*) as cnt from enrollment where course_id=?";
            Object [] p1 = { courseId };
            Integer current = (Integer) getValue(sql, p1, "cnt", "int");
            // 查询2 - 我选修的课程
            sql = "select count(*) as cnt from enrollment where student_id=?";
            Object [] p2 = { userId };
            Integer total = (Integer) getValue(sql, p2, "cnt", "int");
            // 判断1 - 该课程是否选过
            if( course.getEstatus() > 0 ){
                return "该课程已选";
            }
            // 判断2 -该课程是否可选
            if( course.getStatus() != 1 ){
                return "该课程已关闭选课";
            }
            // 判断3 - 我的选课是否超过上限
            if( total >= 3 ){
                return "选课超过上限，每名同学限选3门课";
            }
            // 判断4 - 该课程是否有名额（乐观）
            if( current >= course.getCapacity() ){
                return "该课程已选满";
            }
            // 开始选课
            sql = "insert into enrollment (student_id, course_id, enrollment_time, status, score) values ( ?, ?, NOW(), 1, 0) ";
            if(update(sql, params)>0){
                // 等待1s
                Thread.sleep(1000);
                // 查询我前面有几个选课的
                sql = "select count(*) as cnt from enrollment where course_id=? and id < (select id from enrollment where course_id=? and student_id=?) ";
                Object[] p3 = { courseId, courseId, userId };
                Integer res = (Integer) getValue(sql, p3, "cnt", "int");
                // 如果超选，我退出
                if( res >= course.getCapacity() ){
                    sql = "delete from enrollment where student_id=? and course_id=?";
                    update(sql, params);
                    return "该课程已选满";
                }
            }else{
                return "选课失败，请稍后重试";
            }

        }else if ( action==2 ){
            //退选
            sql = "select status from enrollment where student_id=? and course_id=?";
            Integer status = (Integer) getValue(sql, params, "status", "int");
            if(status==null){
                return "未选修该门课程";
            }else if(status == 2){
                return "选课信息已确认，如需退选退选请联系教师";
            }
            sql = "delete from enrollment where student_id=? and course_id=?";
            if(update(sql, params)<=0){
                return "操作失败，请稍后重试";
            }
        }


        return result;
    }
}
