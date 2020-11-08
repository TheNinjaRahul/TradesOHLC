package com.upstoxassignment.upstoxassignment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SubjectImpl implements ISubject {

    @Autowired
    SharedDataService sharedDataService;

    Object o = new Object();
    private Map<ObserverImpl, Object> subscriberMap = new ConcurrentHashMap<>();

    @Override
    public void add(ObserverImpl observer) {
        if (!sharedDataService.getSubjectMap().containsKey(observer.getSymbol())) {
            sharedDataService.getSubjectMap().put(observer.getSymbol(), this);
        }
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
