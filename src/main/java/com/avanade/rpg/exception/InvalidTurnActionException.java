package com.avanade.rpg.exception;

import java.io.Serial;


public class InvalidTurnActionException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 7283074491912303615L;

    public InvalidTurnActionException( String message ) {
        super( message );
    }

    public InvalidTurnActionException( ) {
        super( "Invalid Turn Action" );
    }
}

