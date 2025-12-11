package cyou.tianshu.charging.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cyou.tianshu.charging.entity.UserInfo;

@Repository
public interface UserRepositoy extends JpaRepository<UserInfo, Integer> {
    UserInfo findByEmail(String email);
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM UserInfo u WHERE u.email = :email")
    boolean existsByEmail(@Param("email") String email);
    UserInfo findByPhone(String phone);
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM UserInfo u WHERE u.phone = :phone")
    boolean existsByPhone(@Param("phone") String phone);
}
