package dev.delivercraft.charscnt.console;

public final class Main {

    void main() {
        CharactersCounter charactersCounter = new CharactersCounter(System.in, System.out);
        charactersCounter.displayCharactersCount();
    }

}
