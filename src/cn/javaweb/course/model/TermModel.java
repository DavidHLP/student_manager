package cn.javaweb.course.model;

import cn.javaweb.base.entity.IDInfo;
import cn.javaweb.base.entity.PageInfo;
import cn.javaweb.course.entity.Course;
import cn.javaweb.course.entity.Term;
import cn.javaweb.library.BaseModel;
import cn.javaweb.library.Config;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TermModel extends BaseModel {
    public TermModel(Config conf){
        super(conf);
    }

    public Object parseObject(ResultSet rs){
        Term term = new Term();
        try{
            term.setId(rs.getInt("id"));
            term.setTermName(rs.getString("term_name"));
            term.setRemark(rs.getString("remark"));
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return term;
    }


    public HashMap<String,Object> getPageData(PageInfo pi, HashMap<String, Object> params){
        HashMap<String,Object> result = new HashMap<>();
        if(params.size()<1){
            result.put("total", getCount("select count(*) as cnt from term where 1", null));
            result.put("rows", selectAll("select * from term course where 1 limit " + pi.getOffset() + " , " + pi.getLimit(), null));

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
            String sql = "select count(*) as cnt from term where " + where;
            result.put("total", getCount(sql, p.toArray()));
            sql = "select * from term where " + where;
            result.put("rows", selectAll(sql, p.toArray()));
        }

        return result;
    }

    public boolean add(Term term)throws SQLException {
        String sql = "insert into term (term_name,remark) values (?, ?)";
        Object [] params = {
                term.getTermName(), term.getRemark()
        };
        return update(sql, params)>0;
    }

    public boolean edit(Term term)throws SQLException{
        String sql = "update term set term_name=?, remark=? where id=?";
        Object [] params = {
                term.getTermName(), term.getRemark(), term.getId()
        };
        return update(sql, params)>0;
    }

    public boolean del(IDInfo idInfo)throws SQLException{
        String sql = "delete from term where id=?";
        Object [] params = {idInfo.getId()};
        return update(sql, params)>0;
    }
    public int getCourseCntByTermId(int id){
        String sql = "select count(*) cnt from course where term=(select term_name from term where id=?)";
        Object[] params = {id};
        return getCount(sql, params);
    }
}
