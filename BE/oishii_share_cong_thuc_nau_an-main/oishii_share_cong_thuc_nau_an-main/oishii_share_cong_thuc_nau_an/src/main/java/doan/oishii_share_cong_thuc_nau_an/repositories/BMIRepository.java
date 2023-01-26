package doan.oishii_share_cong_thuc_nau_an.repositories;

import doan.oishii_share_cong_thuc_nau_an.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BMIRepository extends JpaRepository<Account, Integer> {

    @Query("select a from Account a where a.status = 1 and a.userName = :username")
    public Account getInformationBMIUser(String username);

    @Query("select a from Account a where a.status = 1 and a.userName =?1")
    Optional<Account> findByUserName(String userName);
}
