package fr.abberrouyne.kata.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doReturn;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import fr.abberrouyne.kata.bean.Amount;
import fr.abberrouyne.kata.entity.Account;
import fr.abberrouyne.kata.entity.Customer;
import fr.abberrouyne.kata.entity.type.OperationType;
import fr.abberrouyne.kata.exception.KataCommonsException;
import fr.abberrouyne.kata.repository.impl.AccountRepository;
import fr.abberrouyne.kata.repository.impl.OperationAccountRepository;
import fr.abberrouyne.kata.service.impl.AccountServiceImpl;

/**
 * Created by abberrouyne on 30/11/2018.
 */
@RunWith(MockitoJUnitRunner.Silent.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository mockAccountRepository;

    @Mock
    private OperationAccountRepository mockOperationAccountRepository;

    private AccountServiceImpl accountService;

    private Customer custom;

    private Account account;

    @Before
    public void setUp() throws Exception {

        accountService = new AccountServiceImpl(mockAccountRepository, mockOperationAccountRepository);

        custom = Customer.builder()
                         .id(1L)
                         .build();
        account = Account.builder()
                                 .accountNumber("1000555")
                .customer(custom)
                                 .balance(1000L)
                                 .build();
    }

    @Test
    public void should_update_account_balance_deposit_case() throws KataCommonsException {

        Account updatedAccount = Account.builder()
                                        .accountNumber("1000555")
                .customer(custom)
                                        .balance(1100L)
                                        .build();
        Amount amount = new Amount(100L);
        doReturn(account).when(mockAccountRepository)
                         .findByAccountNumber("1000555");
        doReturn(updatedAccount).when(mockAccountRepository)
                                .save(updatedAccount);

        Account result = accountService.updateSolde(amount, "1000555", OperationType.DEPOSIT);

        assertThat(result).isEqualToComparingFieldByField(updatedAccount);
    }

    @Test
    public void should_update_account_balance_withdrawl_case() throws KataCommonsException {
        Account updatedAccount = Account.builder()
                                        .accountNumber("1000555")
                .customer(custom)
                                        .balance(900L)
                                        .build();
        Amount amount = new Amount(100L);
        doReturn(account).when(mockAccountRepository)
                         .findByAccountNumber("1000555");
        doReturn(updatedAccount).when(mockAccountRepository)
                                .save(updatedAccount);

        Account result = accountService.updateSolde(amount, "1000555", OperationType.WITHDRAWL);

        assertThat(result).isEqualToComparingFieldByField(updatedAccount);
    }

    @Test
    public void should_not_authorize_customer_to_make_withdraw_if_his_balance_is_less_then_amount() {
        Customer custom = Customer.builder().id(1L).build();
        Account account = Account.builder()
                                 .accountNumber("1000555")
                .customer(custom)
                                 .balance(1000L)
                                 .build();
        doReturn(account).when(mockAccountRepository)
                         .findByAccountNumber("1000555");

        assertThatThrownBy(() -> accountService.updateSolde(new Amount(1100L), "1000555", OperationType.WITHDRAWL)).isInstanceOf(KataCommonsException.class);
    }
}
