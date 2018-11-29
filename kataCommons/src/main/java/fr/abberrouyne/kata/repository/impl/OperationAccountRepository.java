package fr.abberrouyne.kata.repository.impl;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.abberrouyne.kata.entity.Account;
import fr.abberrouyne.kata.entity.OperationAccount;

/**
 * Created by abberrouyne on 30/11/2018.
 */
@Repository
public interface OperationAccountRepository extends JpaRepository<OperationAccount, Long> {

    List<OperationAccount> findByAccount(Account account);
}
