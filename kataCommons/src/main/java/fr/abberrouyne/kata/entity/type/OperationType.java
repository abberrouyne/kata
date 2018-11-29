package fr.abberrouyne.kata.entity.type;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by abberrouyne on 30/11/2018.
 */
public enum OperationType {
    DEPOSIT("Dépôt"),
    WITHDRAWL("Retrait");

    private final String label;

    private static Map<String, OperationType> map = new HashMap<String, OperationType>();

    OperationType(String label) {
        this.label = label;
    }

    static {
        for (OperationType accommodationType : OperationType.values()) {
            map.put(accommodationType.label, accommodationType);
        }
    }

    public static OperationType getValueOf(String accommodationType) {
        return map.get(accommodationType);
    }

    public String getLabel() {
        return label;
    }
}
