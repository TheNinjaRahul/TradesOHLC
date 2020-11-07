package com.upstoxassignment.upstoxassignment.pojo;

import java.util.concurrent.atomic.AtomicInteger;

public class OHLC implements Cloneable {
    private String symbol;
    private double open;
    private double high;
    private double low;
    private double closePrice;
    private double volume;
    private String event = "ohlc_notify";
    private long barNum;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(double closePrice) {
        this.closePrice = closePrice;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public long getBarNum() {
        return barNum;
    }

    public void setBarNum(long barNum) {
        this.barNum = barNum;
    }

    public Object clone() throws
            CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "OHLC{" +
                "symbol='" + symbol + '\'' +
                ", open=" + open +
                ", high=" + high +
                ", low=" + low +
                ", closePrice=" + closePrice +
                ", volume=" + volume +
                ", event='" + event + '\'' +
                ", barNum=" + barNum +
                '}';
    }
}
