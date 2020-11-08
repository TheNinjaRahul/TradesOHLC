package com.upstoxassignment.upstoxassignment.service;

import com.upstoxassignment.upstoxassignment.pojo.Trade;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * OHLCService service classs
 */
@Service
public class OHLCServiceimpl implements IOHLCService {

    BlockingDeque<Trade> trades = new LinkedBlockingDeque<>();

    @Override
    public void insertTrade(Trade trade) {
        trades.add(trade);
    }

    @Override
    public Trade getTrade() {
        try {
            return trades.pollFirst(2, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public BlockingDeque<Trade> getTrades() {
        return trades;
    }

    public void setTrades(BlockingDeque<Trade> trades) {
        this.trades = trades;
    }
}
