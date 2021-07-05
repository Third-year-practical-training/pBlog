package com.pblogteam.pblog.mapper;

import com.pblogteam.pblog.entity.AdminBan;
import com.pblogteam.pblog.entity.AdminBanExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AdminBanMapper {
    long countByExample(AdminBanExample example);

    int deleteByExample(AdminBanExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AdminBan record);

    int insertSelective(AdminBan record);

    List<AdminBan> selectByExample(AdminBanExample example);

    AdminBan selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AdminBan record, @Param("example") AdminBanExample example);

    int updateByExample(@Param("record") AdminBan record, @Param("example") AdminBanExample example);

    int updateByPrimaryKeySelective(AdminBan record);

    int updateByPrimaryKey(AdminBan record);
}