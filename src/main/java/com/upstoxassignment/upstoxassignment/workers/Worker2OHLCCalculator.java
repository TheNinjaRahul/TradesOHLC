package com.upstoxassignment.upstoxassignment.workers;


import com.upstoxassignment.upstoxassignment.pojo.OHCLWithStartAndEndTime;
import com.upstoxassignment.upstoxassignment.pojo.OHLC;
import com.upstoxassignment.upstoxassignment.pojo.Trade;
import com.upstoxassignment.upstoxassignment.service.IOHLCService;
import com.upstoxassignment.upstoxassignment.service.SharedDataService;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Worker2OHLCCalculator extends Thread {

    int intervalSec;
    IOHLCService iohlcService;
    SharedDataService sharedDataService;

    ExecutorService executorService = Executors.newFixedThreadPool(10);

    public Worker2OHLCCalculator(IOHLCService iohlcService, SharedDataService sharedDataService, String intervalSec) {
        this.iohlcService = iohlcService;
        this.sharedDataService = sharedDataService;
        this.intervalSec = Integer.parseInt(intervalSec);
    }

    @Override
    public void run() {
        while (true) {
            Trade trade = iohlcService.getTrade();
            if (trade != null) {
                Runnable task = getTradeTask(trade);
                executorService.execute(task);
            }
        }
    }

    private Runnable getTradeTask(Trade trade) {
        return new Runnable() {
            @Override
            public void run() {
                Map map = sharedDataService.getMap();
                //tried to optimize if we have subscriber then only we will calculate OHLC
                if (sharedDataService.getSubscriberDataMap().containsKey(trade.getSym())) {
                    if (map.containsKey(trade.getSym())) {
                        addExistingTrade(trade);
                    } else {
                        createTrade(trade);
                    }
                }
            }
        };
    }

    private void addExistingTrade(Trade trade) {
        Map map = sharedDataService.getMap();
        OHCLWithStartAndEndTime ohcl = (OHCLWithStartAndEndTime) map.get(trade.getSym());
        LocalDateTime triggerTime = getLocalDateTime(trade.getTS2());


        if (triggerTime.compareTo(ohcl.getEndTime()) <= 0) {
            updateMap(ohcl, trade);
        } else {
            try {
                ohcl.setBarNum(sharedDataService.getAtomicInteger().incrementAndGet());
                sharedDataService.getSubscriberDataMap().get(ohcl.getSymbol()).addLast((OHLC) ohcl.clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            map.remove(trade.getSym());

            LocalDateTime newEndTime = ohcl.getEndTime().plusSeconds(intervalSec);
            while (triggerTime.compareTo(newEndTime) > 0) {
                sharedDataService.getSubscriberDataMap().get(ohcl.getSymbol()).addLast(getBlankResponse());
                newEndTime = newEndTime.plusSeconds(intervalSec);
            }
        }
    }

    private OHLC getBlankResponse() {
        OHLC ohcl = new OHLC();
        ohcl.setBarNum(sharedDataService.getAtomicInteger().incrementAndGet());
        return ohcl;
    }

    private void updateMap(OHCLWithStartAndEndTime ohcl, Trade trade) {
        double price = Double.parseDouble(trade.getP());
        double q = Double.parseDouble(trade.getQ());
        ohcl.setHigh(Math.max(ohcl.getHigh(), price));
        ohcl.setLow(Math.min(ohcl.getLow(), price));
        ohcl.setClosePrice(price);
        ohcl.setVolume(ohcl.getVolume() + q);

    }

    private void createTrade(Trade trade) {
        OHCLWithStartAndEndTime ohcl = new OHCLWithStartAndEndTime();
        ohcl.setSymbol(trade.getSym());
        ohcl.setClosePrice(0);
        ohcl.setHigh(Double.parseDouble(trade.getP()));
        ohcl.setLow(Double.parseDouble(trade.getP()));
        ohcl.setEvent("ohlc_notify");
        ohcl.setVolume(Double.parseDouble(trade.getQ()));
        LocalDateTime triggerTime = getLocalDateTime(trade.getTS2());
        ohcl.setStartTime(triggerTime);
        ohcl.setEndTime(triggerTime.plusSeconds(intervalSec));
        ohcl.setClosePrice(Double.parseDouble(trade.getP()));
        sharedDataService.getMap().put(ohcl.getSymbol(), ohcl);
    }

    public LocalDateTime getLocalDateTime(String timestamp) {
        long time = Long.parseLong(timestamp);
        LocalDateTime triggerTime =
                LocalDateTime.ofInstant(Instant.ofEpochMilli(time),
                        TimeZone.getDefault().toZoneId());
        return triggerTime;
    }

}
