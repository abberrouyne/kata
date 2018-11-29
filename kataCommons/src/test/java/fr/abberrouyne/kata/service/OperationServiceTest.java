package fr.abberrouyne.kata.service;

import static com.google.common.collect.ImmutableList.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import java.time.LocalDate;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import fr.abberrouyne.kata.entity.Account;
import fr.abberrouyne.kata.entity.Customer;
import fr.abberrouyne.kata.entity.OperationAccount;
import fr.abberrouyne.kata.entity.type.OperationType;
import fr.abberrouyne.kata.repository.impl.AccountRepository;
import fr.abberrouyne.kata.repository.impl.OperationAccountRepository;
import fr.abberrouyne.kata.service.impl.OperationAccountServiceImpl;

/**
 * Created by abberrouyne on 30/11/2018.
 */
@RunWith(MockitoJUnitRunner.class)
public class OperationServiceTest {

    @Mock
    private OperationAccountRepository mockOperationRepository;

    @Mock
    private AccountRepository mockAccountRepository;

    private OperationAccountServiceImpl operationAccountService;

    private Customer customer;

    private OperationAccount depot;

    private OperationAccount retrait;

    private Account account;

    @Before
    public void init() {
        operationAccountService = new OperationAccountServiceImpl(mockOperationRepository, mockAccountRepository);
        customer = Customer.builder()
                           .firstName("TOTO")
                           .lastName("TATA")
                           .build();
        account = Account.builder()
                         .accountNumber("11005BC589")
                         .balance(500L)
                .customer(customer)
                .build();
        depot = OperationAccount.builder()
                .amount(50L)
                                .date(LocalDate.now()
                                               .minusDays(1))
                .account(account)
                                .operationType(OperationType.DEPOSIT)
                                .build();
        retrait = OperationAccount.builder()
                .amount(300L)
                                  .date(LocalDate.now())
                .account(account)
                                  .operationType(OperationType.DEPOSIT)
                                  .build();
    }

    @Test
    public void should_fetch_all_operations_by_account() throws Exception {
        doReturn(of(depot, retrait)).when(mockOperationRepository)
                                           .findByAccount(account);
        doReturn(account).when(mockAccountRepository)
                         .findByAccountNumber("11005BC589");

        List<OperationAccount> operations = operationAccountService.getOperationsByAccountNumber("11005BC589");

        assertThat(operations).isNotNull()
                .isNotEmpty()
                .hasSize(2)
                .containsExactly(depot, retrait);
    }
}