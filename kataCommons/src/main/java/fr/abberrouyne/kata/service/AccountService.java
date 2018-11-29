package fr.abberrouyne.kata.service;

import fr.abberrouyne.kata.bean.Amount;
import fr.abberrouyne.kata.entity.Account;
import fr.abberrouyne.kata.entity.type.OperationType;
import fr.abberrouyne.kata.exception.KataCommonsException;

/**
 * Created by abberrouyne on 30/11/2018.
 */
public interface AccountService {

    public Account updateSolde(Amount amount, String accountNumero, OperationType operationType) throws KataCommonsException;
}
