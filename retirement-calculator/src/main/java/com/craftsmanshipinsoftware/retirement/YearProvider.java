package com.craftsmanshipinsoftware.retirement;

import java.time.Year;

@FunctionalInterface
interface YearProvider {

    Year currentYear();
}
