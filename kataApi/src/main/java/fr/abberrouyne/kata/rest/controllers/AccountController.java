package fr.abberrouyne.kata.rest.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fr.abberrouyne.kata.bean.Amount;
import fr.abberrouyne.kata.entity.OperationAccount;
import fr.abberrouyne.kata.entity.type.OperationType;
import fr.abberrouyne.kata.exception.KataCommonsException;
import fr.abberrouyne.kata.rest.resources.AccountResource;
import fr.abberrouyne.kata.rest.resources.OperataionAccountResource;
import fr.abberrouyne.kata.service.AccountService;
import fr.abberrouyne.kata.service.OperationAccountService;
import fr.abberrouyne.kata.util.CommonUtils;
import fr.abberrouyne.kata.utils.ApiUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * Created by abberrouyne on 30/11/2018.
 */
@RestController
@RequestMapping(value = ApiUtils.REST_PATH)
@Api(tags = { "accounts" })
public class AccountController {

    @Inject
    private AccountService accountService;

    @Inject
    private OperationAccountService operationService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Updates a account.
     * 
     * @param accountNumero
     *            the numero of the account to update
     * @param amount
     *            the new value of amount attributes
     * @param request
     * @param response
     * @return the updated resource
     */
    @RequestMapping(
            value = ApiUtils.ACCOUNT_PATH + "/{accountNumero}/operations/{operationType}", method = RequestMethod.PUT, consumes = ApiUtils.MEDIA_TYPE_JSON_UTF_8,
            produces = ApiUtils.MEDIA_TYPE_JSON_UTF_8)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Updates a account resource.", notes = "You have to provide a valid account number in the URL and in the payload. The account number attribute can not be updated.")
    public AccountResource update(@PathVariable String accountNumero, @PathVariable OperationType operationType, @RequestBody Amount amount) throws KataCommonsException {
        return CommonUtils.MODEL_MAPPER.map(accountService.updateSolde(amount, accountNumero, operationType), AccountResource.class);
    }


    @RequestMapping(value = ApiUtils.ACCOUNT_PATH + "/{accountNumero}/history", method = RequestMethod.GET, produces = ApiUtils.MEDIA_TYPE_JSON_UTF_8)
    @ResponseStatus(HttpStatus.OK)
    public List<OperataionAccountResource> getAccountHistory(@ApiParam(value = "The account number of the account.", required = true) @PathVariable String accountNumero) {
        List<OperationAccount> operationsAccount = operationService.getOperationsByAccountNumber(accountNumero);
        List<OperataionAccountResource> operationsAccountResources = new ArrayList<OperataionAccountResource>();
        operationsAccountResources = operationsAccount.stream()
                                                      .map(operationAccount -> CommonUtils.MODEL_MAPPER.map(operationAccount, OperataionAccountResource.class))
                                                      .collect(Collectors.toList());
        return operationsAccountResources;
    }
}
