package com.upstoxassignment.upstoxassignment.service;

public interface IObserver {
    public String getSymbolName();

    public void update(String s);
}
