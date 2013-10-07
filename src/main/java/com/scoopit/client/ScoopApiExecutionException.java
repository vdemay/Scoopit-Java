package com.scoopit.client;

import org.scribe.model.Response;

public class ScoopApiExecutionException extends Exception {

    private static final long serialVersionUID = 1L;

    public Response response;

    public ScoopApiExecutionException(Response response, String message, Throwable t) {
        super(message, t);
        this.response = response;
    }

}
