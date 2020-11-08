package com.upstoxassignment.upstoxassignment.pojo;

import java.time.LocalDateTime;

/**
 *Wrpper of OHLC to use start and end time.
 */
public class OHCLWithStartAndEndTime extends OHLC implements Cloneable {
    LocalDateTime startTime;
    LocalDateTime endTime;

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Object clone() throws
            CloneNotSupportedException {
        return super.clone();
    }
}
