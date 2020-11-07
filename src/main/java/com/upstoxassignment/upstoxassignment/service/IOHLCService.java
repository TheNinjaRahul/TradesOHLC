package com.upstoxassignment.upstoxassignment.service;

import com.upstoxassignment.upstoxassignment.pojo.Trade;

public interface IOHLCService {

    public void insertTrade(Trade trade);

    public Trade getTrade();
}
