package dev.delivercraft.charscnt.gui;

final class CharactersCount {

    private final String input;

    private CharactersCount(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("input must not be blank");
        }
        this.input = input;
    }

    static CharactersCount of(String input) {
        return new CharactersCount(input);
    }

    @Override
    public String toString() {
        return "%s has %d characters.".formatted(this.input, this.input.length());
    }
}
