package uz.project.olix.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uz.project.olix.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

//    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String username);

    Optional<User> findByPhoneNumber(String phoneNumber);

    boolean existsByPhoneNumber(String phoneNumber);

    Boolean existsByEmail(String email);

    @Transactional
    @Modifying
    @Query("update User u set u.password = ?1 where u.phoneNumber = ?2")
    void updatePasswordByPhoneNumber(String password, String phoneNumber);

    @Transactional
    @Modifying
    @Query("update User u set u.fullName = ?1 where u.phoneNumber = ?2")
    int updateFullNameByPhoneNumber(String fullName, String phoneNumber);

    @Transactional
    @Modifying
    @Query(nativeQuery = true , value = "INSERT INTO users_roles (roles_id, user_id) values ( :rId, :uId) ")
    public void setRolesToUsersById(@Param("rId") Long roleId, @Param("uId") Long userId);
}
