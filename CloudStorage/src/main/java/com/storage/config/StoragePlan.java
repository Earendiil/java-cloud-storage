package com.storage.config;

public enum StoragePlan {
    BASIC(50 * 1024 * 1024), 

    PREMIUM(200 * 1024 * 1024); 

    private final long maxBytes;

    StoragePlan(long maxBytes) {
        this.maxBytes = maxBytes;
    }

    public long getMaxBytes() {
        return maxBytes;
    }
}

