package com.finalproject.shelter.repository;

import com.finalproject.shelter.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
public interface AccountRepository extends JpaRepository<Account,Long> {
    Account findByUsername(String username);
    Account findByIdentity(String identity);
    Account findByEmail(String email);
    boolean existsByEmail(String email);
    Account findByNickname(String nickname);
    boolean existsByNickname(String nickname);


}