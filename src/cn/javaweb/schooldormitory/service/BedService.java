package cn.javaweb.schooldormitory.service;

import cn.javaweb.library.BaseService;
import cn.javaweb.library.Config;
import cn.javaweb.schooldormitory.entity.BedInfo;
import cn.javaweb.schooldormitory.model.BedModel;
import cn.javaweb.schooldormitory.model.CollegeModel;
import cn.javaweb.schooldormitory.model.DormitoryModel;

import java.sql.SQLException;
import java.util.List;

public class BedService extends BaseService {
    private CollegeModel collegeModel;
    private DormitoryModel dormitoryModel;
    private BedModel bedModel;

    public BedService(Config config) {
        super(config);
        dormitoryModel = new DormitoryModel(this.getConnection());
        bedModel = new BedModel(this.getConnection());
        collegeModel = new CollegeModel(this.getConnection());
    }

    public List<BedInfo> getNoUseAll() throws SQLException {
        try {
            List<BedInfo> bedInfos = bedModel.getNoUseAll();
            for (BedInfo bedInfo : bedInfos) {
                String[] strings = bedInfo.getBedInfo().split("-");
                bedInfo.setCollegeDormitoryBedNumber(
                        collegeModel.getCollegeNameByCollegeId(Integer.parseInt(strings[0])) +
                                dormitoryModel.getDormitoryNameBydDormitoryId(Integer.parseInt(strings[1])) + strings[2] + "床位"
                );
            }
            this.commitTransaction();
            return bedInfos;
        } catch (SQLException e) {
            this.rollbackTransaction();
            throw e;
        }
    }

    public void updateStatus(Integer userId, Integer bedId, String available) throws SQLException {
        try {
            bedModel.updateStatus(userId, bedId, available);
            dormitoryModel.updateOccupied(bedModel.getBedByBedId(bedId), available);
            this.commitTransaction();
        } catch (SQLException e) {
            this.rollbackTransaction();
            throw e;
        }
    }

    public Void del(Integer bedId, String available) throws SQLException {
        try {
            bedModel.del(bedId);
            dormitoryModel.updateOccupied(bedModel.getBedByBedId(bedId), available);
            this.commitTransaction();
        } catch (SQLException e) {
            this.rollbackTransaction();
            throw e;
        }
        return null;
    }
}