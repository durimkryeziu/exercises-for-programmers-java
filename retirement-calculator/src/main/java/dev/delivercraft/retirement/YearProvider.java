package dev.delivercraft.retirement;

import java.time.Year;

@FunctionalInterface
interface YearProvider {

    Year currentYear();
}
