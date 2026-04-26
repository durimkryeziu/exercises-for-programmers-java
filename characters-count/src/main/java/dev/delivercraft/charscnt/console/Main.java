package dev.delivercraft.charscnt.console;

import dev.delivercraft.io.InputStreamLineReader;
import dev.delivercraft.io.LineReader;
import dev.delivercraft.io.LineWriter;
import dev.delivercraft.io.OutputStreamLineWriter;

public final class Main {

    void main() {
        LineReader lineReader = new InputStreamLineReader(System.in);
        LineWriter lineWriter = new OutputStreamLineWriter(System.out);
        CharactersCounter charactersCounter = new CharactersCounter(lineReader, lineWriter);
        charactersCounter.displayCharactersCount();
    }

}
