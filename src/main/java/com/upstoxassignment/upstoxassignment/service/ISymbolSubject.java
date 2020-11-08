package com.upstoxassignment.upstoxassignment.service;

/**
 * Symbol Class
 * Consider it as Observer DesignPatter
 */
public interface ISymbolSubject {
    public void add(ClientAsObserverImpl observer);

    public void remove(ClientAsObserverImpl observer);

    public void notify(String msg);
}
