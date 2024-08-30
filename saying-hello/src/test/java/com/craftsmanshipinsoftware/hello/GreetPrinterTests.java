package com.craftsmanshipinsoftware.hello;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

class GreetPrinterTests {

  @Test
  void sayHello_GivenNameIsProvided_ShouldGreetProperly() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    GreetPrinter greetPrinter = new GreetPrinter(new ByteArrayInputStream("Durim".getBytes()), new PrintStream(outputStream));

    greetPrinter.sayHello();

    assertThat(outputStream)
        .hasToString("Hello, Durim, nice to meet you!\n");
  }

  @Test
  void sayHello_GivenNameHasSpaces_ShouldGreetProperly() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    GreetPrinter greetPrinter = new GreetPrinter(new ByteArrayInputStream("John Doe".getBytes()), new PrintStream(outputStream));

    greetPrinter.sayHello();

    assertThat(outputStream)
        .hasToString("Hello, John Doe, nice to meet you!\n");
  }

}
