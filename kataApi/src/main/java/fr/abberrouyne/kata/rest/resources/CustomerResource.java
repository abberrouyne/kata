package fr.abberrouyne.kata.rest.resources;

import java.io.Serializable;

import fr.abberrouyne.kata.entity.Account;
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

public class CustomerResource implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String lastName;

    private String firstName;

    private Account account;

}