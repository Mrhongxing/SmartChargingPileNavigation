package cyou.tianshu.charging.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor  // 添加无参构造函数
@AllArgsConstructor // 添加全参构造函数
@TableName("favorite_list")
public class FavoriteList {
    @TableId
    private Long id;
    @TableField("user_id")
    private Long userId;
    @TableField("Latitude")
    private double latitude;
    @TableField("Longitude")
    private double longitude;
    @TableField("address")
    private String address;

}
