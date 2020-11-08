package com.upstoxassignment.upstoxassignment.pojo;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * OHCL -> OpenPrice, HighPrice,ClosePrice, LowPrice
 */
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OHLC ohlc = (OHLC) o;
        return Double.compare(ohlc.open, open) == 0 &&
                Double.compare(ohlc.high, high) == 0 &&
                Double.compare(ohlc.low, low) == 0 &&
                Double.compare(ohlc.closePrice, closePrice) == 0 &&
                Double.compare(ohlc.volume, volume) == 0 &&
                barNum == ohlc.barNum &&
                symbol.equals(ohlc.symbol) &&
                event.equals(ohlc.event);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol, open, high, low, closePrice, volume, event, barNum);
    }
}
