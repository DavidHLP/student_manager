package cn.javaweb.schooldormitory.service;

import cn.javaweb.base.model.UserModel;
import cn.javaweb.library.BaseService;
import cn.javaweb.library.Config;
import cn.javaweb.schooldormitory.entity.BedInfo;
import cn.javaweb.schooldormitory.entity.DormitoryInfo;
import cn.javaweb.schooldormitory.entity.PageInfo;
import cn.javaweb.schooldormitory.model.BedModel;
import cn.javaweb.schooldormitory.model.DormitoryModel;

import java.sql.SQLException;
import java.util.List;

public class DormitoryService extends BaseService {
    private DormitoryModel dormitoryModel;
    private BedModel bedModel;
    private UserModel userModel;

    public DormitoryService(Config config) {
        super(config);
        dormitoryModel = new DormitoryModel(this.getConnection());
        bedModel = new BedModel(this.getConnection());
        userModel = new UserModel(this.getConnection());
    }

    public PageInfo<DormitoryInfo> getDormitoryInfo(int page, int limit, String dormitoryName) throws SQLException {
        try {
            PageInfo<DormitoryInfo> pageInfos = dormitoryModel.getPaginatedList(page, limit, dormitoryName);
            for (DormitoryInfo dormitory : pageInfos.getList()) {
                int dormitoryId = dormitory.getId();
                List<BedInfo> bedInfos = bedModel.getBedByDormitoryId(dormitoryId);
                for (BedInfo bedInfo : bedInfos) {
                    if (bedInfo.getUserId() != null)
                        bedInfo.setUser(userModel.getUserByUserId(bedInfo.getUserId()));
                }
                dormitory.setBeds(bedInfos);
            }
            this.commitTransaction();
            return pageInfos;
        } catch (SQLException e) {
            this.rollbackTransaction();
            throw e;
        }
    }

    public Void del(Integer id) throws SQLException {
        try {
            bedModel.delByDormitoryId(id);
            dormitoryModel.del(id);
            this.commitTransaction();
        } catch (SQLException e) {
            this.rollbackTransaction();
            throw e;
        }
        return null;
    }

    public Void add(DormitoryInfo dormitoryInfo) throws SQLException {
        try {
            dormitoryModel.add(dormitoryInfo);
            dormitoryInfo.setId(dormitoryModel.getDormitoryId(dormitoryInfo.getName()));
            bedModel.initBeds(dormitoryInfo.getId(), dormitoryInfo.getCapacity(), dormitoryInfo.getCollegeId());
            this.commitTransaction();
        } catch (SQLException e) {
            this.rollbackTransaction();
            throw e;
        }
        return null;
    }

    public Void updateDormitory(DormitoryInfo dormitory) throws SQLException {
        try {
            dormitoryModel.updateDormitory(dormitory);
            String partition = "-";
            for (BedInfo bedInfo : bedModel.getBedByDormitoryId(dormitory.getId())) {
                bedModel.updateBedInfo(
                        bedInfo.getId(),
                        dormitory.getCollegeId() + partition + dormitory.getId() + partition + bedInfo.getBedNumber()
                );
            }
            this.commitTransaction();
        } catch (SQLException e) {
            this.rollbackTransaction();
            throw e;
        }
        return null;
    }
}
