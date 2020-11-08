package com.upstoxassignment.upstoxassignment.workers;


import com.upstoxassignment.upstoxassignment.pojo.OHCLWithStartAndEndTime;
import com.upstoxassignment.upstoxassignment.pojo.OHLC;
import com.upstoxassignment.upstoxassignment.pojo.Trade;
import com.upstoxassignment.upstoxassignment.service.IOHLCService;
import com.upstoxassignment.upstoxassignment.service.SharedDataService;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Map;
import java.util.TimeZone;

public class Worker2OHLCCalculator extends Thread {

    int intervalSec;
    IOHLCService iohlcService;
    SharedDataService sharedDataService;

//    ExecutorService executorService = Executors.newFixedThreadPool(1);

    public Worker2OHLCCalculator(IOHLCService iohlcService, SharedDataService sharedDataService, String intervalSec) {
        this.iohlcService = iohlcService;
        this.sharedDataService = sharedDataService;
        this.intervalSec = Integer.parseInt(intervalSec);
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("Worker 2 running ");
            Trade trade = iohlcService.getTrade();
            if (trade != null) {
                Map map = sharedDataService.getMap();
                if (map.containsKey(trade.getSym())) {
                    addExistingTrade(trade);
                } else {
                    createTrade(trade);
                }
                //tried executor
//                Runnable task = getTradeTask(trade);
//                executorService.execute(task);
            }
        }
    }

    private Runnable getTradeTask(Trade trade) {
        return new Runnable() {
            @Override
            public void run() {
                Map map = sharedDataService.getMap();
                //tried to optimize if we have subscriber then only we will calculate OHLC
//                if (sharedDataService.getSubjectMap().containsKey(trade.getSym())) {
                if (map.containsKey(trade.getSym())) {
                    addExistingTrade(trade);
                } else {
                    createTrade(trade);
                }
            }
//            }
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
                if (!sharedDataService.getSubscriberDataMap().containsKey(ohcl.getSymbol())) {
                    sharedDataService.getSubscriberDataMap().put(ohcl.getSymbol(), new LinkedList<>());
                }
                sharedDataService.getSubscriberDataMap().get(ohcl.getSymbol()).addLast((OHLC) ohcl.clone());
            } catch (Exception e) {
                e.printStackTrace();
            }
            map.remove(trade.getSym());

            LocalDateTime newEndTime = ohcl.getEndTime().plusSeconds(intervalSec);
            while (triggerTime.compareTo(newEndTime) > 0) {
                sharedDataService.getSubscriberDataMap().get(ohcl.getSymbol()).addLast(getBlankResponse());
                newEndTime = newEndTime.plusSeconds(intervalSec);
            }
            createTrade(trade);
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
        double price = Double.parseDouble(trade.getP());
        ohcl.setSymbol(trade.getSym());
        ohcl.setClosePrice(price);
        ohcl.setHigh(price);
        ohcl.setLow(price);
        ohcl.setEvent("ohlc_notify");
        ohcl.setVolume(price);
        ohcl.setOpen(price);

        LocalDateTime triggerTime = getLocalDateTime(trade.getTS2());
        ohcl.setStartTime(triggerTime);
        ohcl.setEndTime(triggerTime.plusSeconds(intervalSec));

        ohcl.setClosePrice(Double.parseDouble(trade.getP()));
        sharedDataService.getMap().put(ohcl.getSymbol(), ohcl);
    }

    public LocalDateTime getLocalDateTime(String timestamp) {
        long time = Long.parseLong(timestamp) / 1000000;
        LocalDateTime triggerTime =
                LocalDateTime.ofInstant(Instant.ofEpochMilli(time),
                        TimeZone.getDefault().toZoneId());
        return triggerTime;
    }
}
