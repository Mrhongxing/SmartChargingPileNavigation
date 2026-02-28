package cyou.tianshu.charging.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cyou.tianshu.charging.entity.UserInfo;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

public interface UserInfoMapper extends BaseMapper<UserInfo> {
    
    /**
     * 方式1：注解SQL（简单查询）
     */
    @Select("SELECT * FROM smart_charging_user WHERE id = #{userId} ORDER BY created_at DESC")
    List<UserInfo> findByUserId(@Param("userId") Long userId);
    @Select("SELECT * FROM smart_charging_user WHERE email = #{email} ORDER BY created_at DESC")
    UserInfo findByEmail(@Param("email") String email);
    @Select("SELECT * FROM smart_charging_user WHERE phone = #{phone} ORDER BY created_at DESC")
    UserInfo findByPhone(@Param("phone") String phone);
    @Select("SELECT COUNT(*) FROM smart_charging_user WHERE email = #{email}")
    boolean existsByEmail(@Param("email") String email);
    @Select("SELECT COUNT(*) FROM smart_charging_user WHERE phone = #{phone}")
    boolean existsByPhone(@Param("phone") String phone);
    @Insert("INSERT INTO smart_charging_user (email, password, phone) VALUES (#{email}, #{password}, #{phone})")
    void save(UserInfo userInfo);
    /**
     * 方式2：注解SQL带条件
     */
/*     @Select("SELECT " +
            "   DATE(start_time) as date, " +
            "   COUNT(*) as totalCount, " +
            "   SUM(consumption) as totalConsumption, " +
            "   SUM(amount) as totalAmount " +
            "FROM charging_record " +
            "WHERE start_time BETWEEN #{startDate} AND #{endDate} " +
            "GROUP BY DATE(start_time)")
    List<ChargingStatisticsVO> statisticsByDate(@Param("startDate") String startDate, 
                                                @Param("endDate") String endDate); */
}