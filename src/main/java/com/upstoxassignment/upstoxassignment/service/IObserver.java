package com.upstoxassignment.upstoxassignment.service;

/**
 * This is oberser Interface
 * Consider it as Observer DesignPatter
 */
public interface IObserver {
    public String getSymbolName();

    public void update(String s);
}
