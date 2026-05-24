package dev.delivercraft.paint;

import dev.delivercraft.io.LineReader;
import dev.delivercraft.io.LineWriter;

import java.util.Objects;

final class PaintCalculator {

    private final LineReader lineReader;

    private final LineWriter lineWriter;

    PaintCalculator(LineReader lineReader, LineWriter lineWriter) {
        this.lineReader = Objects.requireNonNull(lineReader, "lineReader must not be null");
        this.lineWriter = Objects.requireNonNull(lineWriter, "lineWriter must not be null");
    }

    void calculatePaint() {
        RoomDimension length = readDimension("What is the length of the room in feet? ");
        RoomDimension width = readDimension("What is the width of the room in feet? ");

        PaintEstimate estimate = PaintEstimator.estimate(new RectangularRoom(length, width));

        this.lineWriter.writeLine("You will need to purchase %s %s of paint to cover %s square feet."
                .formatted(estimate.gallons(), estimate.gallonWord(), estimate.area()));
    }

    private RoomDimension readDimension(String prompt) {
        this.lineWriter.write(prompt);
        return RoomDimension.of(this.lineReader.readLine());
    }
}
