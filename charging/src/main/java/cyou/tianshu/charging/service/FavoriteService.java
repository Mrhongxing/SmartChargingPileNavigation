package cyou.tianshu.charging.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cyou.tianshu.charging.entity.FavoriteList;
import cyou.tianshu.charging.mapper.FavoriteMapper;
import jakarta.annotation.Resource;

@Service
public class FavoriteService {
    @Resource
    private FavoriteMapper FavoriteMapper;
    public List<FavoriteList> getFavorites(Long userId) {
        List<FavoriteList> a=FavoriteMapper.findByUserId(userId);
        return a;
    }
}
