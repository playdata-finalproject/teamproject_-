package com.finalproject.shelter.domain.repository;

import com.finalproject.shelter.domain.model.entity.userDomain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
public interface AccountRepository extends JpaRepository<Account,Long> {
    Account findByUsername(String username);
    Account findByIdentity(String identity);
    Account findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByIdentity(String identity);
    boolean existsByEmail(String email);
}
