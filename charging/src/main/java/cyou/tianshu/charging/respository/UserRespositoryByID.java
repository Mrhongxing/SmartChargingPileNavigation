package cyou.tianshu.charging.respository;

import cyou.tianshu.charging.entity.UserInfo;

import org. springframework.data.jpa.repository.JpaRepository;

public interface UserRespositoryByID extends JpaRepository<UserInfo, Integer> {
    
}
