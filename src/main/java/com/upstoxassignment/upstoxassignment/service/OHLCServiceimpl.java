package com.upstoxassignment.upstoxassignment.service;

import com.upstoxassignment.upstoxassignment.pojo.Trade;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

@Service
public class OHLCServiceimpl implements IOHLCService {

    BlockingDeque<Trade> trades = new LinkedBlockingDeque<>();

    @Override
    public void insertTrade(Trade trade) {
        trades.add(trade);
    }

    @Override
    public Trade getTrade() {
        return trades.poll();
    }

    public BlockingDeque<Trade> getTrades() {
        return trades;
    }

    public void setTrades(BlockingDeque<Trade> trades) {
        this.trades = trades;
    }
}
