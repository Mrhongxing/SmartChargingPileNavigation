package cyou.tianshu.charging.mapper;

import cyou.tianshu.charging.entity.UserInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    
    // 1. 使用 @Select 注解
    @Select("SELECT * FROM user_info WHERE user_id = #{userId}")
    UserInfo selectById(@Param("userId") Integer userId);
    
    // 2. 查询所有
    @Select("SELECT * FROM user_info")
    List<UserInfo> selectAll();
    
    // 3. 条件查询
    @Select("SELECT * FROM user_info WHERE email = #{email}")
    UserInfo selectByEmail(@Param("email") String email);
    
    @Select("SELECT * FROM user_info WHERE phone = #{phone}")
    @Results({
        @Result(property = "user_id", column = "user_id"),
        @Result(property = "email", column = "email"),
        @Result(property = "password", column = "password_hash"),
        @Result(property = "phone", column = "phone")
    })
    UserInfo selectByPhone(@Param("phone") String phone);
    
    // 4. 插入（返回自增ID）
    @Insert("INSERT INTO user_info(email, password_hash, phone, pet_name, user_role) " +
            "VALUES(#{email}, #{password}, #{phone}, #{petName}, #{userRole})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int insert(UserInfo user);
    
    // 5. 更新
    @Update("UPDATE user_info SET " +
            "email = #{email}, " +
            "password_hash = #{password}, " +
            "phone = #{phone}, " +
            "pet_name = #{petName}, " +
            "user_img = #{userImg}, " +
            "car_vin = #{carVin}, " +
            "car_type = #{carType}, " +
            "user_role = #{userRole} " +
            "WHERE user_id = #{userId}")
    int update(UserInfo user);
    
    // 6. 删除
    @Delete("DELETE FROM user_info WHERE user_id = #{userId}")
    int deleteById(@Param("userId") Integer userId);
    
    // 7. 检查存在性
    @Select("SELECT COUNT(*) > 0 FROM user_info WHERE email = #{email}")
    boolean existsByEmail(@Param("email") String email);
    
    @Select("SELECT COUNT(*) > 0 FROM user_info WHERE phone = #{phone}")
    boolean existsByPhone(@Param("phone") String phone);
}
