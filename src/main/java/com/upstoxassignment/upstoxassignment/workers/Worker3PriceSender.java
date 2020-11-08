package com.upstoxassignment.upstoxassignment.workers;


import com.upstoxassignment.upstoxassignment.pojo.OHLC;
import com.upstoxassignment.upstoxassignment.service.IOHLCService;
import com.upstoxassignment.upstoxassignment.service.SharedDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Worker3PriceSender extends Thread {

    @Autowired
    IOHLCService iohlcService;

    @Autowired
    SharedDataService sharedDataService;

    public Worker3PriceSender(IOHLCService iohlcService) {
        this.iohlcService = iohlcService;
    }

    @Scheduled(fixedDelay = 1000, initialDelay = 4000)
    public void sendDataToSubscriber() {
        System.out.println("Worker 3 running ");
        for (String symbol : sharedDataService.getSubjectMap().keySet()) {
            OHLC ohlc = null;
            if (sharedDataService.getSubscriberDataMap().containsKey(symbol) &&
                    sharedDataService.getSubscriberDataMap().get(symbol).size() != 0) {
                ohlc = sharedDataService.getSubscriberDataMap().get(symbol).removeFirst();
                sharedDataService.getSubjectMap().get(symbol).notify(ohlc.toString());
            }
//            if (ohlc == null) {
//                ohlc = getBlankResponse();
//            }
//            sharedDataService.getSubjectMap().get(symbol).notify(ohlc.toString());
        }
    }

    private OHLC getBlankResponse() {
        OHLC ohcl = new OHLC();
        ohcl.setBarNum(sharedDataService.getAtomicInteger().incrementAndGet());
        return ohcl;
    }
}
