package com.upstoxassignment.upstoxassignment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SubjectImpl implements ISymbolSubject {

    @Autowired
    SharedDataService sharedDataService;

    Object o = new Object();
    private Map<ClientAsObserverImpl, Object> subscriberMap = new ConcurrentHashMap<>();

    @Override
    public void add(ClientAsObserverImpl observer) {
        if (!sharedDataService.getSubjectMap().containsKey(observer.getSymbol())) {
            sharedDataService.getSubjectMap().put(observer.getSymbol(), this);
        }
        subscriberMap.put(observer, o);
    }

    @Override
    public void remove(ClientAsObserverImpl observer) {
        subscriberMap.remove(observer);
    }

    @Override
    public void notify(String msg) {
        for (ClientAsObserverImpl observer : subscriberMap.keySet()) {
            observer.update(msg);
        }
    }
}
