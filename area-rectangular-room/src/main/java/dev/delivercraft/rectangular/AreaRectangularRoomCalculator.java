package dev.delivercraft.rectangular;

class AreaRectangularRoomCalculator {

    private static final double CONVERSION_FACTOR = 0.092_903_04;

    private final LineReader lineReader;

    private final LineWriter lineWriter;

    AreaRectangularRoomCalculator(LineReader lineReader, LineWriter lineWriter) {
        this.lineReader = lineReader;
        this.lineWriter = lineWriter;
    }

    void calculateArea() {
        this.lineWriter.write("What is the length of the room in feet? ");
        int length = toInt(readInput());
        this.lineWriter.write("What is the width of the room in feet? ");
        int width = toInt(readInput());
        int squareFeet = length * width;
        double squareMeters = toSquareMeters(squareFeet);
        String outputTemplate = """
                You entered dimensions of %d feet by %s feet.
                The area is
                %d square feet
                %.3f square meters
                """.formatted(length, width, squareFeet, squareMeters);
        this.lineWriter.writeLine(outputTemplate);
    }

    private String readInput() {
        String input = this.lineReader.readLine();
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("Input must not be empty!");
        }
        return input;
    }

    private static int toInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Please enter a numeric value! Input: %s".formatted(value), e);
        }
    }

    private static double toSquareMeters(int squareFeet) {
        return squareFeet * CONVERSION_FACTOR;
    }
}
