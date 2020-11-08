package com.upstoxassignment.upstoxassignment.service;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SubjectImpl implements ISubject {

    Object o = new Object();
    private Map<ObserverImpl, Object> subscriberMap = new ConcurrentHashMap<>();

    @Override
    public void add(ObserverImpl observer) {
        subscriberMap.put(observer, o);
    }

    @Override
    public void remove(ObserverImpl observer) {
        subscriberMap.remove(observer);
    }

    @Override
    public void notify(String msg) {
        for (ObserverImpl observer : subscriberMap.keySet()) {
            observer.update(msg);
        }
    }


}
