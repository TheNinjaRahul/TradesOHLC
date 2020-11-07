package com.upstoxassignment.upstoxassignment.workers;


import com.upstoxassignment.upstoxassignment.pojo.OHLC;
import com.upstoxassignment.upstoxassignment.service.IOHLCService;
import com.upstoxassignment.upstoxassignment.service.SharedDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class Worker3PriceSender extends Thread {

    @Autowired
    IOHLCService iohlcService;

    @Autowired
    SharedDataService sharedDataService;

    public Worker3PriceSender(IOHLCService iohlcService) {
        this.iohlcService = iohlcService;
    }

    @Scheduled(fixedDelay = 1500)
    public void sendDataToSubscriber() {
        for (String symbol : sharedDataService.getSubscriberDataMap().keySet()) {
            OHLC ohlc = sharedDataService.getSubscriberDataMap().get(symbol).removeFirst();
            sharedDataService.getSubjectMap().get(symbol).notify(ohlc.toString());
        }
    }
}
