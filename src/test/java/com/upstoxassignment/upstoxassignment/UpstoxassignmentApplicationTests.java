package com.upstoxassignment.upstoxassignment;

import com.upstoxassignment.upstoxassignment.pojo.OHLC;
import com.upstoxassignment.upstoxassignment.service.SharedDataService;
import com.upstoxassignment.upstoxassignment.workers.Worker3PriceSender;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UpstoxassignmentApplication.class)
@TestPropertySource(properties = "scheduling.enabled=false")
class UpstoxassignmentApplicationTests {

    @Autowired
    ApplicationContext ctx;

    @Autowired
    SharedDataService sharedDataService;

//    @Test
//    public void testMain() {
//        String[] args = {"classpath:sample.json", "Bloomberg", "XXBTZUSD"};
//    }

    @Autowired
    CommandLineRunner commandLineRunner;

    @Autowired
    Worker3PriceSender worker3PriceSender;

    @Test
    public void testRun() {
        String[] args = {"classpath:sample.json", "Bloomberg", "XXBTZUSD"};
        try {
            commandLineRunner.run(args);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        System.out.println(sharedDataService.getSubscriberDataMap());

        // getFirst OHLC
        OHLC expectedOHCL = getExpectedFirstOHCL();
        OHLC ohlc = sharedDataService.getSubscriberDataMap().get("XXBTZUSD").getFirst();

        assertOHLC(expectedOHCL, ohlc);
        //Send Data to subscriber
        worker3PriceSender.sendDataToSubscriber();

        //getSecond OHLC
        OHLC expectedOHCL2 = getExpectedSecondOHCL();
        OHLC ohlc2 = sharedDataService.getSubscriberDataMap().get("XXBTZUSD").getFirst();

        assertOHLC(expectedOHCL2, ohlc2);

        System.out.println(sharedDataService.getSubscriberDataMap());
        for (Thread thread : Thread.getAllStackTraces().keySet()) {
            if (thread.getName().equals("Reader")) thread.interrupt();
            if (thread.getName().equals("Calculator")) thread.interrupt();
        }
    }

    private void assertOHLC(OHLC expectedOHCL, OHLC ohlc) {
        Assert.assertEquals(expectedOHCL.getSymbol(), ohlc.getSymbol());
        Assert.assertTrue(expectedOHCL.getClosePrice() == ohlc.getClosePrice());
        Assert.assertTrue(expectedOHCL.getOpen() == ohlc.getOpen());
        Assert.assertTrue(expectedOHCL.getLow() == ohlc.getLow());
        Assert.assertTrue(expectedOHCL.getVolume() == ohlc.getVolume());
        Assert.assertEquals(expectedOHCL.getEvent(), ohlc.getEvent());
    }

    private OHLC getExpectedSecondOHCL() {
        OHLC ohlc = new OHLC();
        ohlc.setSymbol("XXBTZUSD");
        ohlc.setOpen(6537.7);
        ohlc.setClosePrice(6537.4);
        ohlc.setLow(6537.4);
        ohlc.setVolume(6542.14344042);
        ohlc.setHigh(6537.7);
        ohlc.setEvent("ohlc_notify");
        ohlc.setBarNum(2);
        return ohlc;
    }

    private OHLC getExpectedFirstOHCL() {
        OHLC ohlc = new OHLC();
        ohlc.setSymbol("XXBTZUSD");
        ohlc.setOpen(6538.8);
        ohlc.setClosePrice(6537.7);
        ohlc.setLow(6537.7);
        ohlc.setVolume(6542.799999420001);
        ohlc.setHigh(6538.8);
        ohlc.setEvent("ohlc_notify");
        ohlc.setBarNum(1);
        return ohlc;
    }
}
