package cyou.tianshu.charging.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cyou.tianshu.charging.entity.UserInfo;
import cyou.tianshu.charging.mapper.UserInfoMapper;

import java.io.Serializable;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class UserInfoService extends ServiceImpl<UserInfoMapper, UserInfo> {

    /**
     * 根据 ID 查询：
     * 第一次查询 MySQL，后续从 Redis 获取
     */
    @Cacheable(
            value = "userInfo",
            key = "'id:' + #id",
            unless = "#result == null"
    )
    public UserInfo findByUserId(Long id) {
        return baseMapper.findByUserId(id).get(0);
    }

    /**
     * 根据邮箱查询
     */
    @Cacheable(
            value = "userInfo",
            key = "'email:' + #email",
            unless = "#result == null"
    )
    public UserInfo findByEmail(String email) {
        return baseMapper.findByEmail(email);
    }

    /**
     * 根据手机号查询
     */
    @Cacheable(
            value = "userInfo",
            key = "'phone:' + #phone",
            unless = "#result == null"
    )
    public UserInfo findByPhone(String phone) {
        return baseMapper.findByPhone(phone);
    }

    /**
     * 更新数据库后删除缓存
     */
    @CacheEvict(
            value = "userInfo",
            key = "'id:' + #user.id"
    )
    @Override
    public boolean updateById(UserInfo user) {
        return super.updateById(user);
    }

    /**
     * 删除用户时删除缓存
     */
     @CacheEvict(
            value = "userInfo",
            key = "'id:' + #id"
    )
    @Override 
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    } 
}