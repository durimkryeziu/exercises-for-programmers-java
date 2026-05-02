package dev.delivercraft.hello;

import dev.delivercraft.io.CapturingLineWriter;
import dev.delivercraft.io.LineReader;
import dev.delivercraft.io.LineWriter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class GreetPrinterTests {

    @Test
    void sayHello_ShouldAskForName() {
        LineReader lineReader = () -> "Durim";
        LineWriter lineWriter = new CapturingLineWriter();
        GreetPrinter greetPrinter = new GreetPrinter(lineReader, lineWriter);

        greetPrinter.sayHello();

        assertThat(lineWriter.toString())
                .containsOnlyOnce("What is your name?");
    }

    @ParameterizedTest(name = "Given name: {0}")
    @ValueSource(strings = {"Durim", "John Doe"})
    void sayHello_GivenNameIsProvided_ShouldGreetProperly(String name) {
        LineReader lineReader = () -> name;
        LineWriter lineWriter = new CapturingLineWriter();
        GreetPrinter greetPrinter = new GreetPrinter(lineReader, lineWriter);

        greetPrinter.sayHello();

        assertThat(lineWriter.toString())
                .containsOnlyOnce("Hello, %s, nice to meet you!".formatted(name) + System.lineSeparator());
    }

    @ParameterizedTest(name = "Given name: {0}")
    @ValueSource(strings = {"Durim", "John Doe"})
    void sayHello_GivenNameIsProvided_ShouldOutputPromptAndGreeting(String name) {
        LineReader lineReader = () -> name;
        LineWriter lineWriter = new CapturingLineWriter();
        GreetPrinter greetPrinter = new GreetPrinter(lineReader, lineWriter);

        greetPrinter.sayHello();

        assertThat(lineWriter)
                .hasToString("What is your name? Hello, %s, nice to meet you!%s".formatted(name,
                        System.lineSeparator()));
    }

}
