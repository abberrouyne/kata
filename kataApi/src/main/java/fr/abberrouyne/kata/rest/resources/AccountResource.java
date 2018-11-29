package fr.abberrouyne.kata.rest.resources;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class AccountResource implements Serializable {

    private static final long serialVersionUID = 1L;

    private String accountNumber;

    private long balance;

    private CustomerResource customer;

    private List<OperataionAccountResource> operations;
}
