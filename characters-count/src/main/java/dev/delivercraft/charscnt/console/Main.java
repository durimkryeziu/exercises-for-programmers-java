package dev.delivercraft.charscnt.console;

public class Main {

    public static void main(String[] args) {
        CharactersCounter charactersCounter = new CharactersCounter(System.in, System.out);
        charactersCounter.displayCharactersCount();
    }

}
