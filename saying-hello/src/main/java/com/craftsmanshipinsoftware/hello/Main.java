package com.craftsmanshipinsoftware.hello;

public class Main {

  public static void main(String[] args) {
    GreetPrinter greetPrinter = new GreetPrinter(System.in, System.out);
    greetPrinter.sayHello();
  }

}
