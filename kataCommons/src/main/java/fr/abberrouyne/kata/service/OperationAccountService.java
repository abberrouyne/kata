package fr.abberrouyne.kata.service;

import java.util.List;

import fr.abberrouyne.kata.entity.OperationAccount;

public interface OperationAccountService {
    public List<OperationAccount> getOperationsByAccountNumber(String accountNumero);
}