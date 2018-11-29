package fr.abberrouyne.kata.bean;

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

/**
 * Created by abberrouyne on 30/11/2018.
 */
@Value
@Builder
@NoArgsConstructor(force = true)
public class Amount {
    private long value;

    public Amount(long value) {
        this.value = validate(value);
    }

    private long validate(long value) {
        if(value > 0) {
            return value;
        }
        throw new IllegalArgumentException("Le montant doit être positif");
    }
}
