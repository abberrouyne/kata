package fr.abberrouyne.kata.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import fr.abberrouyne.kata.entity.type.OperationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by abberrouyne on 30/11/2018.
 */
@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
@NoArgsConstructor(force = true)
@AllArgsConstructor

@Entity
@SequenceGenerator(name = "OPERATION_ACCOUNT_SEQ", sequenceName = "OPERATION_ACCOUNT_SEQ", initialValue = 1, allocationSize = 1)
@Table(name = "OPERATION_ACCOUNT")
public class OperationAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OPERATION_ACCOUNT_SEQ")
    @Column(name = "OPERATION_ACCOUNT_ID")
    private final Long id;

    @Column(name = "OPERATION_ACCOUNT_DATE", nullable = false)
    private final LocalDate date;

    @Column(name = "OPERATION_ACCOUNT_AMOUNT", nullable = false)
    private final Long amount;

    @Column(name = "OPERATION_ACCOUNT_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private final OperationType operationType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCOUNT_NUMBER", foreignKey = @ForeignKey(name = "FK_OPERATION_ACCOUNT_ACCOUNT_NUMBER"))
    private final Account account;
}
