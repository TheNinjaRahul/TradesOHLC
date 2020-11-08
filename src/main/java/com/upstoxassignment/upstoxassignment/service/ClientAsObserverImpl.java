package com.upstoxassignment.upstoxassignment.service;

import java.util.Objects;

/**
 * ObserverImpl to use as client
 */
public class ClientAsObserverImpl implements IObserver {
    private String observerName;
    private String symbol;

    public ClientAsObserverImpl(String observerName, String symbol) {
        this.observerName = observerName;
        this.symbol = symbol;
    }

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
        ClientAsObserverImpl observer = (ClientAsObserverImpl) o;
        return Objects.equals(observerName, observer.observerName) &&
                Objects.equals(symbol, observer.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(observerName, symbol);
    }
}
