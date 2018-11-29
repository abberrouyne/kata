package fr.abberrouyne.kata.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import fr.abberrouyne.kata.entity.Account;
import fr.abberrouyne.kata.entity.OperationAccount;
import fr.abberrouyne.kata.repository.impl.AccountRepository;
import fr.abberrouyne.kata.repository.impl.OperationAccountRepository;
import fr.abberrouyne.kata.service.OperationAccountService;

@Service
public class OperationAccountServiceImpl implements OperationAccountService {

    private OperationAccountRepository operationAccountRepository;

    private AccountRepository accountRepository;

    @Inject
    public OperationAccountServiceImpl(OperationAccountRepository operationAccountRepository, AccountRepository accountRepository) {
        this.operationAccountRepository = operationAccountRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public List<OperationAccount> getOperationsByAccountNumber(String accountNumero) {
        Account account = accountRepository.findByAccountNumber(accountNumero);
        return operationAccountRepository.findByAccount(account);
    }
}