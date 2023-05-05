package com.gmail.uprial.respawnlimiter.trackers;

import org.bukkit.scheduler.BukkitRunnable;

public class TrackerTask<T extends Runnable> extends BukkitRunnable {
    private final T tracker;

    TrackerTask(final T tracker) {
        this.tracker = tracker;
    }

    @Override
    public void run() {
        tracker.run();
    }
}