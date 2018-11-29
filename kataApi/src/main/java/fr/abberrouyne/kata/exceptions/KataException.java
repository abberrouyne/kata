package fr.abberrouyne.kata.exceptions;

import fr.abberrouyne.kata.rest.resources.ErrorResource;

public class KataException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final ErrorResource errorResource;

    public KataException(ErrorResource errorResource) {
        super();
        this.errorResource = errorResource;
    }

    public ErrorResource getErrorResource() {
        return errorResource;
    }
}
