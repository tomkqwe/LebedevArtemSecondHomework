package ru.lebedev.liga.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Класс описывающий модель, хранящего информацию о дате, курсе и валюте;
 */
public class CurrencyModel {
    private BigDecimal nominal;
    private LocalDate date;
    private BigDecimal value;
    private Currency currency;

    public CurrencyModel(BigDecimal nominal,LocalDate date, BigDecimal value, Currency currency) {
        this.nominal = nominal;
        this.date = date;
        this.value = value;
        this.currency = currency;
    }

    public CurrencyModel() {
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public BigDecimal getNominal() {
        return nominal;
    }

    public void setNominal(BigDecimal nominal) {
        this.nominal = nominal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CurrencyModel that)) return false;
        return Objects.equals(getNominal(), that.getNominal()) && Objects.equals(getDate(), that.getDate()) && Objects.equals(getValue(), that.getValue()) && getCurrency() == that.getCurrency();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNominal(), getDate(), getValue(), getCurrency());
    }

    @Override
    public String toString() {
        return "CurrencyModel{" +
                "nominal=" + nominal +
                ", date=" + date +
                ", value=" + value +
                ", currency=" + currency +
                '}';
    }
}

