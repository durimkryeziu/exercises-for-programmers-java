package dev.delivercraft.paint;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;

class PaintEstimateTest {

    @Test
    void area_GivenTrailingZeros_ShouldStripZeros() {
        PaintEstimate estimate = new PaintEstimate(new BigDecimal("360.00"), BigInteger.ONE);

        assertThat(estimate.area()).isEqualTo("360");
    }

    @Test
    void gallonWord_GivenOneGallon_ShouldReturnSingularWord() {
        PaintEstimate estimate = new PaintEstimate(new BigDecimal("100"), BigInteger.ONE);

        assertThat(estimate.gallonWord()).isEqualTo("gallon");
    }

    @Test
    void gallonWord_GivenMultipleGallons_ShouldReturnPluralWord() {
        PaintEstimate estimate = new PaintEstimate(new BigDecimal("360"), BigInteger.valueOf(2));

        assertThat(estimate.gallonWord()).isEqualTo("gallons");
    }
}
