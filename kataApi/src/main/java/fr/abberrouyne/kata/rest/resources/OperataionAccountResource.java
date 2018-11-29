package fr.abberrouyne.kata.rest.resources;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import fr.abberrouyne.kata.entity.type.OperationType;
import fr.abberrouyne.kata.utils.JsonDateDeserializer;
import fr.abberrouyne.kata.utils.JsonDateSerializer;
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

public class OperataionAccountResource implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NonNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonSerialize(using = JsonDateSerializer.class)
    @JsonDeserialize(using = JsonDateDeserializer.class)
    private LocalDate date;

    private Long amount;

    private OperationType operationType;

    private AccountResource account;

}
