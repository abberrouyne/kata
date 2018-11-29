package fr.abberrouyne.kata.service.impl;

import java.time.LocalDate;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.abberrouyne.kata.bean.Amount;
import fr.abberrouyne.kata.entity.Account;
import fr.abberrouyne.kata.entity.OperationAccount;
import fr.abberrouyne.kata.entity.type.OperationType;
import fr.abberrouyne.kata.exception.KataCommonsException;
import fr.abberrouyne.kata.repository.impl.AccountRepository;
import fr.abberrouyne.kata.repository.impl.OperationAccountRepository;
import fr.abberrouyne.kata.service.AccountService;

/**
 * Created by abberrouyne on 30/11/2018.
 */
@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    private OperationAccountRepository operationAccountRepository;

    @Inject
    public AccountServiceImpl(AccountRepository accountRepository, OperationAccountRepository operationAccountRepository) {
        this.accountRepository = accountRepository;
        this.operationAccountRepository = operationAccountRepository;

    }

    private Optional<Account> fetchByAccountNumero(String accountNumero) {
        return Optional.of(accountRepository.findByAccountNumber(accountNumero));
    }

    @Transactional
    public Account updateSolde(Amount amount, String accountNumber, OperationType operationType) throws KataCommonsException {
        Account account = fetchByAccountNumero(accountNumber).orElseThrow(() -> new KataCommonsException("Compte non trouvé, veuillez vérifier votre numero de compte"));
        switch (operationType) {
        case DEPOSIT:
            account = accountRepository.save(Account.builder()
                                                 .balance(account.getBalance() + amount.getValue())
                                                 .accountNumber(account.getAccountNumber())
                                                 .customer(account.getCustomer())
                                                 .build());
            addOperationAccount(amount, account, operationType);
            return account;
        case WITHDRAWL:
            if (amount.getValue() > account.getBalance()) {
                throw new KataCommonsException("Votre solde est insuffisant, l'opération est annulée.");
            }
            account = accountRepository.save(Account.builder()
                                                 .accountNumber(account.getAccountNumber())
                                                 .balance(account.getBalance() - amount.getValue())
                                                 .customer(account.getCustomer())
                                                 .build());
            addOperationAccount(amount, account, operationType);
            return account;
        default:
            throw new IllegalArgumentException("Le type d'opération donné ne correspondant à aucun type.");
        }
    }

    private void addOperationAccount(Amount amount, Account account, OperationType operationType) {
        operationAccountRepository.save(OperationAccount.builder()
                .account(account)
                .amount(amount.getValue())
                .operationType(operationType)
                .date(LocalDate.now())
                .build());
    }
}