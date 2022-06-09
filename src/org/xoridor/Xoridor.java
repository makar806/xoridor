package org.xoridor;
import org.xoridor.ui.Controller;

public class Xoridor {
    public static void main(String[] args) {
        new Xoridor();
    }

    public Xoridor() {
        new Controller().start();
    }
}