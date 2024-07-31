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

    @Query("SELECT u FROM User u WHERE LOWER(u.phoneNumber) = LOWER(:phoneNumber)")
    Optional<User> findByPhoneNumber(@Param("phoneNumber") String phoneNumber);



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
    @Query(nativeQuery = true , value = "INSERT INTO users_roles (user_id, roles_id ) values ( :userId, :roleId) ")
    public void setRolesToUsersById(@Param("userId") Long userId, @Param("roleId") Long roleId);


    @Modifying
    @Query("UPDATE User u SET u.email = :email WHERE u.phoneNumber = :phoneNumber")
    void updateEmailByPhoneNumber(String phoneNumber, String email);

    @Modifying
    @Query("UPDATE User u SET u.phoneNumber = :newPhoneNumber WHERE u.phoneNumber = :currentPhoneNumber")
    void updatePhoneNumberByPhoneNumber(String currentPhoneNumber, String newPhoneNumber);

    @Modifying
    @Query("UPDATE User u SET u.profilePicture = :profilePicture WHERE u.phoneNumber = :phoneNumber")
    void updateProfilePictureByPhoneNumber(String phoneNumber, String profilePicture);


}
