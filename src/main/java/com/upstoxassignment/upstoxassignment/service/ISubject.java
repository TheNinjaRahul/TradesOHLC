package com.upstoxassignment.upstoxassignment.service;

public interface ISubject {
    public void add(ObserverImpl observer);

    public void remove(ObserverImpl observer);

    public void notify(String msg);
}
