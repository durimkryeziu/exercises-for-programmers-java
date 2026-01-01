package dev.delivercraft.hello;

public final class Main {

    void main() {
        GreetPrinter greetPrinter = new GreetPrinter(System.in, System.out);
        greetPrinter.sayHello();
    }

}
