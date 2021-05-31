package com.finalproject.shelter.presentation.controller.restApi.userApi;

import com.finalproject.shelter.domain.model.entity.userDomain.Account;
import com.finalproject.shelter.domain.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
//@Slf4j
class UserApiController {

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/users")
    List<Account> all() {
        return accountRepository.findAll();
    }

    @PostMapping("/Accounts")
    Account newAccount(@RequestBody Account account) {
        return accountRepository.save(account);
    }

    @GetMapping("/Accounts/{id}")
    Account findById(@PathVariable Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    @PutMapping("/Accounts/{id}")
    Account replaceAccount(@RequestBody Account account, @PathVariable Long id) {
        return accountRepository.findById(id)
                .map(Account -> accountRepository.save(Account))
                .orElseGet(() -> {
                    account.setId(id);
                    return accountRepository.save(account);
                });
    }

    @DeleteMapping("/user/{id}")
    void deleteUser(@PathVariable Long id) {
        accountRepository.deleteById(id);
    }
}
