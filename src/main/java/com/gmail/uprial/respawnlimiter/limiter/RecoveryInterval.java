package com.gmail.uprial.respawnlimiter.limiter;

public enum RecoveryInterval {
    HOUR,
    DAY,
    WEEK;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
