package fr.abberrouyne.kata.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by abberrouyne on 30/11/2018.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@ToString

@Entity
@Table(name = "ACCOUNT")
public class Account {

    @Id
    @Column(name = "ACCOUNT_NUMBER")
    private final String accountNumber;

    @Column(name = "ACCOUNT_BALNACE")
    private final long balance;

    @OneToOne(fetch = FetchType.LAZY)
    private final Customer customer;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "OPERATION_ACCOUNT_ID", foreignKey = @ForeignKey(name = "FK_ACOUNT_OPERATION_ID"))
    private final List<OperationAccount> operations;
}
