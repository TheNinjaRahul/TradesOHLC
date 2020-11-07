package com.upstoxassignment.upstoxassignment.service;

import com.upstoxassignment.upstoxassignment.pojo.OHCLWithStartAndEndTime;
import com.upstoxassignment.upstoxassignment.pojo.OHLC;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class SharedDataService {
    private Map<String, OHCLWithStartAndEndTime> map = new ConcurrentHashMap<>();
    private Map<String, Deque<OHLC>> subscriberDataMap = new ConcurrentHashMap<>();
    private Map<String, SubjectImpl> subjectMap = new ConcurrentHashMap<>();
    private AtomicInteger atomicInteger = new AtomicInteger();

    public Map<String, OHCLWithStartAndEndTime> getMap() {
        return map;
    }

    public void setMap(Map<String, OHCLWithStartAndEndTime> map) {
        this.map = map;
    }

    public Map<String, Deque<OHLC>> getSubscriberDataMap() {
        return subscriberDataMap;
    }

    public void setSubscriberDataMap(Map<String, Deque<OHLC>> subscriberDataMap) {
        this.subscriberDataMap = subscriberDataMap;
    }

    public AtomicInteger getAtomicInteger() {
        return atomicInteger;
    }

    public void setAtomicInteger(AtomicInteger atomicInteger) {
        this.atomicInteger = atomicInteger;
    }

    public Map<String, SubjectImpl> getSubjectMap() {
        return subjectMap;
    }

    public void setSubjectMap(Map<String, SubjectImpl> subjectMap) {
        this.subjectMap = subjectMap;
    }
}
