package com.upstoxassignment.upstoxassignment.service;

import com.upstoxassignment.upstoxassignment.pojo.Trade;

/***
 * IOHLCService interface
 */
public interface IOHLCService {

    /**
     * Insert trade which is used by worker 1
     *
     * @param trade
     */
    public void insertTrade(Trade trade);

    /**
     * getTrade which will use by worker 2
     *
     * @return
     */
    public Trade getTrade();
}
