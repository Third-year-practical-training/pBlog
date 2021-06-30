package com.pblogteam.pblog.mapper;

import com.pblogteam.pblog.entity.UserFollowerRela;
import com.pblogteam.pblog.entity.UserFollowerRelaExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserFollowerRelaMapper {

    @Select("SELECT user_id FROM user_follower_rela WHERE follower_id=#{id}")
    List<Integer> selectByFollowerId(Integer id);

    long countByExample(UserFollowerRelaExample example);

    int deleteByExample(UserFollowerRelaExample example);

    int insert(UserFollowerRela record);

    int insertSelective(UserFollowerRela record);

    List<UserFollowerRela> selectByExample(UserFollowerRelaExample example);

    int updateByExampleSelective(@Param("record") UserFollowerRela record, @Param("example") UserFollowerRelaExample example);

    int updateByExample(@Param("record") UserFollowerRela record, @Param("example") UserFollowerRelaExample example);
}