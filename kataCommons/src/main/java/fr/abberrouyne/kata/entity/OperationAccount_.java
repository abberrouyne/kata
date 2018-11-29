package fr.abberrouyne.kata.entity;

import fr.abberrouyne.kata.entity.type.OperationType;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2018-11-29T01:15:29.101+0100")
@StaticMetamodel(OperationAccount.class)
public class OperationAccount_ {
	public static volatile SingularAttribute<OperationAccount, Long> id;
	public static volatile SingularAttribute<OperationAccount, LocalDate> date;
	public static volatile SingularAttribute<OperationAccount, Long> amount;
	public static volatile SingularAttribute<OperationAccount, OperationType> operationType;
	public static volatile SingularAttribute<OperationAccount, Account> account;
}
