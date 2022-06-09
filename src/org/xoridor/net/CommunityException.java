package org.xoridor.net;

public class CommunityException extends Exception {
    public CommunityException(Exception source) {
        super(source);
    }

    public CommunityException(String number, String message) {
        super(message);
        this.number = number;
    }

    private String number;
}
