package dev.delivercraft.hello;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class GreetPrinterTests {

    @ParameterizedTest(name = "Given name: {0}")
    @ValueSource(strings = {"Durim", "John Doe"})
    void sayHello_GivenNameIsProvided_ShouldGreetProperly(String name) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        GreetPrinter greetPrinter = new GreetPrinter(new ByteArrayInputStream(name.getBytes()),
                new PrintStream(outputStream));

        greetPrinter.sayHello();

        assertThat(outputStream)
                .hasToString("Hello, %s, nice to meet you!%n", name);
    }

}
