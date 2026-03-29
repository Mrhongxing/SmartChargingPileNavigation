package cyou.tianshu.charging.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cyou.tianshu.charging.entity.FavoriteList;
import cyou.tianshu.charging.entity.UserInfo;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

public interface FavoriteMapper extends BaseMapper<FavoriteList> {
    
    /**
     * 方式1：注解SQL（简单查询）
     */
    @Select("SELECT * FROM favorite_list WHERE user_id = #{userId} ORDER BY created_at DESC")
    List<FavoriteList> findByUserId(@Param("userId") Long userId);
    @Delete("DELETE FROM favorite_list WHERE id = #{id}")
    void deleteById(@Param("id") Long id);
    @Insert("INSERT INTO favorite_list (user_id, Latitude, Longitude, address) VALUES (#{userId}, #{latitude}, #{longitude}, #{address})")
    void save(FavoriteList favorite);
}