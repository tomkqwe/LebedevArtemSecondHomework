package ru.lebedev.liga.utils;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.lebedev.liga.model.Currency;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.assertj.core.api.Assertions.*;

import static org.hamcrest.MatcherAssert.assertThat;


class ParseCurrencyCsvTest {
    @Test
    public void checkMethodDeleteQuotes() {
        Assertions.assertThat("75,7527").isEqualTo(ParseCurrencyCsv.deleteQuotes("\"75,7527\""));
    }

    @Test
    public void checkMethodIsColimnPart() {
        Assertions.assertThat(false).isEqualTo(ParseCurrencyCsv.isColumnPart("1"));
    }

    @Test
    public void checkgetCurrencyMethodWithSwitchCase() {
        Assertions.assertThat(Currency.USD).isEqualTo(ParseCurrencyCsv.getCurrency("Доллар США"));
        Assertions.assertThat(Currency.EUR).isEqualTo(ParseCurrencyCsv.getCurrency("Евро"));
    }
}