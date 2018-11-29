package fr.abberrouyne.kata.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2018-11-29T01:37:12.469+0100")
@StaticMetamodel(Account.class)
public class Account_ {
	public static volatile SingularAttribute<Account, String> accountNumber;
	public static volatile SingularAttribute<Account, Long> balance;
	public static volatile SingularAttribute<Account, Customer> customer;
	public static volatile ListAttribute<Account, OperationAccount> operations;
}
