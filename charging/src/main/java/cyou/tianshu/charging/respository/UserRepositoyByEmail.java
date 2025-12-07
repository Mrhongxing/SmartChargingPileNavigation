package cyou.tianshu.charging.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cyou.tianshu.charging.entity.UserInfo;

@Repository
public interface UserRepositoyByEmail extends JpaRepository<UserInfo, String> {
    UserInfo findByEmail(String email);
    boolean existsByEmail(String email);
}
