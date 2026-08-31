package dev.delivercraft.paint;

import dev.delivercraft.io.LineReader;
import dev.delivercraft.io.LineWriter;

import java.util.Objects;

final class PaintCalculator {

    private static final String SHAPE_MENU = "Select room shape: 1) Rectangular  2) Round  3) L-shaped";

    private static final String LENGTH_PROMPT = "What is the length of the room in feet? ";

    private static final String WIDTH_PROMPT = "What is the width of the room in feet? ";

    private static final String RADIUS_PROMPT = "What is the radius of the room in feet? ";

    private static final String OUTER_LENGTH_PROMPT = "What is the length of the outer rectangle in feet? ";

    private static final String OUTER_WIDTH_PROMPT = "What is the width of the outer rectangle in feet? ";

    private static final String CUTOUT_LENGTH_PROMPT = "What is the length of the cut-out in feet? ";

    private static final String CUTOUT_WIDTH_PROMPT = "What is the width of the cut-out in feet? ";

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
            case "3" -> readLShapedDimensions();
            case null, default -> throw new IllegalArgumentException("Please enter 1, 2 or 3");
        };

        PaintEstimate estimate = PaintEstimator.estimate(dimensions);

        this.lineWriter.writeLine("You will need to purchase %s %s of paint to cover %s square feet.".formatted(
                estimate.gallons(), estimate.gallonWord(), estimate.area()));
    }

    private RectangularRoom readRectangularDimensions() {
        RoomDimension length = readDimension(LENGTH_PROMPT);
        RoomDimension width = readDimension(WIDTH_PROMPT);
        return new RectangularRoom(length, width);
    }

    private RoundRoom readRoundDimensions() {
        RoomDimension radius = readDimension(RADIUS_PROMPT);
        return new RoundRoom(radius);
    }

    private LShapedRoom readLShapedDimensions() {
        RoomDimension outerLength = readDimension(OUTER_LENGTH_PROMPT);
        RoomDimension outerWidth = readDimension(OUTER_WIDTH_PROMPT);
        RoomDimension cutoutLength = readDimension(CUTOUT_LENGTH_PROMPT);
        RoomDimension cutoutWidth = readDimension(CUTOUT_WIDTH_PROMPT);
        return new LShapedRoom(
                new RectangularRoom(outerLength, outerWidth),
                new RectangularRoom(cutoutLength, cutoutWidth));
    }

    private RoomDimension readDimension(String prompt) {
        this.lineWriter.write(prompt);
        return RoomDimension.of(this.lineReader.readLine());
    }
}
