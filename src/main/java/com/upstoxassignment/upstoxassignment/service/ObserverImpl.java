package com.upstoxassignment.upstoxassignment.service;

import java.util.Objects;

public class ObserverImpl implements IObserver {
    public ObserverImpl(String observerName, String symbol) {
        this.observerName = observerName;
        this.symbol = symbol;
    }

    private String observerName;
    private String symbol;

    @Override
    public String getSymbolName() {
        return symbol;
    }

    @Override
    public void update(String s) {
        System.out.println(observerName + " Received  Data:" + s);
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObserverImpl observer = (ObserverImpl) o;
        return Objects.equals(observerName, observer.observerName) &&
                Objects.equals(symbol, observer.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(observerName, symbol);
    }
}
