package dev.delivercraft.paint;

import dev.delivercraft.io.LineReader;
import dev.delivercraft.io.LineWriter;

import java.util.Objects;

final class PaintCalculator {

    private static final String SHAPE_MENU = "Select room shape: 1) Rectangular  2) Round";

    private static final String LENGTH_PROMPT = "What is the length of the room in feet? ";

    private static final String WIDTH_PROMPT = "What is the width of the room in feet? ";

    private static final String RADIUS_PROMPT = "What is the radius of the room in feet? ";

    private final LineReader lineReader;

    private final LineWriter lineWriter;

    PaintCalculator(LineReader lineReader, LineWriter lineWriter) {
        this.lineReader = Objects.requireNonNull(lineReader, "lineReader must not be null");
        this.lineWriter = Objects.requireNonNull(lineWriter, "lineWriter must not be null");
    }

    void calculatePaint() {
        this.lineWriter.writeLine(SHAPE_MENU);
        String shapeChoice = this.lineReader.readLine();

        RoomDimensions dimensions = switch (shapeChoice != null ? shapeChoice.trim() : null) {
            case "1" -> readRectangularDimensions();
            case "2" -> readRoundDimensions();
            case null, default -> throw new IllegalArgumentException("Please enter 1 or 2");
        };

        PaintEstimate estimate = PaintEstimator.estimate(dimensions);

        this.lineWriter.writeLine("You will need to purchase %s %s of paint to cover %s square feet."
                .formatted(estimate.gallons(), estimate.gallonWord(), estimate.area()));
    }

    private RectangularRoom readRectangularDimensions() {
        this.lineWriter.write(LENGTH_PROMPT);
        RoomDimension length = RoomDimension.of(this.lineReader.readLine());
        this.lineWriter.write(WIDTH_PROMPT);
        RoomDimension width = RoomDimension.of(this.lineReader.readLine());
        return new RectangularRoom(length, width);
    }

    private RoundRoom readRoundDimensions() {
        this.lineWriter.write(RADIUS_PROMPT);
        RoomDimension radius = RoomDimension.of(this.lineReader.readLine());
        return new RoundRoom(radius);
    }
}
