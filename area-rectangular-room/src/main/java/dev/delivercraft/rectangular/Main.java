package dev.delivercraft.rectangular;

public final class Main {

    void main() {
        LineReader lineReader = new InputStreamLineReader(System.in);
        LineWriter lineWriter = new OutputStreamLineWriter(System.out);
        AreaRectangularRoomCalculator calculator = new AreaRectangularRoomCalculator(lineReader, lineWriter);
        calculator.calculateArea();
    }
}
