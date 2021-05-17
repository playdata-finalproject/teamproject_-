package com.finalproject.shelter.presentationLayer.controller.restApi.userApi;

import com.finalproject.shelter.domainModelLayer.model.entity.userDomain.Account;
import com.finalproject.shelter.domainModelLayer.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
//@Slf4j
class UserApiController {
    @Autowired
    private AccountRepository repository;

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/users")
    List<Account> all() {
        return repository.findAll();
    }
    // end::get-aggregate-root[]

    @PostMapping("/Accounts")
    Account newAccount(@RequestBody Account newAccount) {
        return repository.save(newAccount);
    }

    // Single item

    @GetMapping("/Accounts/{id}")
    Account one(@PathVariable Long id) {

        return repository.findById(id).orElse(null);
    }

    @PutMapping("/Accounts/{id}")
    Account replaceAccount(@RequestBody Account newAccount, @PathVariable Long id) {

        return repository.findById(id)
                .map(Account -> {
                    return repository.save(Account);
                })
                .orElseGet(() -> {
                    newAccount.setId(id);
                    return repository.save(newAccount);
                });
    }

    @DeleteMapping("/user/{id}")
    void deleteuser(@PathVariable Long id) {
        repository.deleteById(id);
    }
}