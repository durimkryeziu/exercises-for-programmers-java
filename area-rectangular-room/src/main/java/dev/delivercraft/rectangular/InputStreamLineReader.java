package dev.delivercraft.rectangular;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;

final class InputStreamLineReader implements LineReader {

    private final BufferedReader bufferedReader;

    InputStreamLineReader(InputStream inputStream) {
        this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    }

    @Override
    public String readLine() {
        try {
            return this.bufferedReader.readLine();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
