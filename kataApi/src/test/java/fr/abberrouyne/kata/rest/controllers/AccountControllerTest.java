package fr.abberrouyne.kata.rest.controllers;

import static com.google.common.collect.ImmutableList.of;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.abberrouyne.kata.bean.Amount;
import fr.abberrouyne.kata.entity.Account;
import fr.abberrouyne.kata.entity.Customer;
import fr.abberrouyne.kata.entity.OperationAccount;
import fr.abberrouyne.kata.entity.type.OperationType;
import fr.abberrouyne.kata.exceptions.KataExceptionHandler;
import fr.abberrouyne.kata.service.AccountService;
import fr.abberrouyne.kata.service.OperationAccountService;

/**
 * Created by abberrouyne on 30/11/2018.
 */
@EnableWebSecurity
@RunWith(MockitoJUnitRunner.class)
public class AccountControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AccountService accountService;

    @Mock
    private OperationAccountService operationService;

    @InjectMocks
    private AccountController accountController;

    private ObjectMapper mapper = new ObjectMapper();
    
    private Amount amount;

    private Account accountWithdraw;

    private Account accountDeposit;

    private OperationAccount depot;

    private OperationAccount retrait;

    @Before
    public void init() {

        mockMvc = MockMvcBuilders.standaloneSetup(accountController)
                                 .setControllerAdvice(new KataExceptionHandler())
                                 .build();

        MockitoAnnotations.initMocks(this);
        // We would need this line if we would not use MockitoJUnitRunner
        // MockitoAnnotations.initMocks(this);
        // Initializes the JacksonTester
        JacksonTester.initFields(this, new ObjectMapper());

        amount = new Amount(50L);
        accountWithdraw = Account.builder()
                .customer(Customer.builder()
                        .id(1L).build()
                )
                                 .balance(1050)
                                 .accountNumber("1000236")
                                 .build();
        accountDeposit = Account.builder()
                .customer(Customer.builder()
                        .id(1L).build()
                )
                                .balance(1100)
                                .accountNumber("1000236")
                                .build();
        depot = OperationAccount.builder()
                .id(1L)
                .amount(50L)
                                .date(LocalDate.now()
                                               .minusDays(1))
                .account(accountWithdraw)
                                .operationType(OperationType.DEPOSIT)
                                .build();
        retrait = OperationAccount.builder()
                .id(2L)
                .amount(300L)
                                  .date(LocalDate.now())
                .account(accountWithdraw)
                                  .operationType(OperationType.WITHDRAWL)
                                  .build();
    }

    @Test
    public void should_make_deposit_by_account_accountNumber() throws Exception {
        doReturn(accountDeposit).when(accountService)
                                .updateSolde(amount, "1000236", OperationType.DEPOSIT);

        mockMvc.perform(put("/rest/resources/accounts/1000236/operations/DEPOSIT/")
                .content(mapper.writeValueAsString(amount))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
               .andExpect(jsonPath("$").isNotEmpty())
               .andExpect(jsonPath("$.accountNumber").value(accountDeposit.getAccountNumber()))
               .andExpect(jsonPath("$.balance").value(accountDeposit.getBalance()))
               .andExpect(jsonPath("$.customer.id").value(accountDeposit.getCustomer()
                                                                        .getId()));
    }

    @Test
    public void should_make_withdraw_by_account_accountNumber() throws Exception {
        doReturn(accountWithdraw).when(accountService)
                                 .updateSolde(amount, "1000236", OperationType.WITHDRAWL);

        mockMvc.perform(put("/rest/resources/accounts/1000236/operations/WITHDRAWL/")
                .content(mapper.writeValueAsString(amount))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
               .andExpect(jsonPath("$").isNotEmpty())
               .andExpect(jsonPath("$.accountNumber").value(accountWithdraw.getAccountNumber()))
               .andExpect(jsonPath("$.balance").value(accountWithdraw.getBalance()))
               .andExpect(jsonPath("$.customer.id").value(accountWithdraw.getCustomer()
                                                                         .getId()));
    }

    @Test
    public void should_fetch_all_account_history() throws Exception {
        given(operationService.getOperationsByAccountNumber("1000236")).willReturn(of(depot, retrait));

        mockMvc.perform(get("/rest/resources/accounts/1000236/history/").contentType(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$").isNotEmpty())
               .andExpect(content().json("[{'id':1,'date':" + LocalDate.now()
                                                                        .minusDays(1)
                       + ",'amount':50,'operationType':'DEPOSIT','account':{'accountNumber':'1000236','balance':1050,'customer':{'id':1,'lastName':null,'firstName':null,'account':null},'operations':null}},"
                       + "{'id':2,'date':" + LocalDate.now()
                       + ",'amount':300,'operationType':'WITHDRAWL','account':{'accountNumber':'1000236','balance':1050,'customer':{'id':1,'lastName':null,'firstName':null,'account':null},'operations':null}}]"));
    }
}
