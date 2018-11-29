package fr.abberrouyne.kata.entity;

import static javax.persistence.FetchType.LAZY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

/**
 * Created by abberrouyne on 30/11/2018.
 */
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Value
@Builder

@Entity
@SequenceGenerator(name = "CUSTOMER_SEQ", sequenceName = "CUSTOMER_SEQ", initialValue = 1, allocationSize = 1)
@Table(name = "CUSTOMER")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUSTOMER_SEQ")
    @Column(name = "CUSTOMER_ID")
    private final Long id;

    @Column(name = "LAST_NAME")
    private final String lastName;

    @Column(name = "FIRST_NAME")
    private final String firstName;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "ACCOUNT_NUMBER", foreignKey = @ForeignKey(name = "FK_CUSTOMER_ACCOUNT_NUMBER"))
    private final Account account;
}
