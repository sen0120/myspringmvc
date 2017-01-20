package com.hus.biz.elasticsearch;

/**
 * Created by fanyun on 17/1/18.
 */
public class CountAndMaxIdResult {
    private long count;
    private long maxId;

    public CountAndMaxIdResult(long count, long maxId) {
        this.count = count;
        this.maxId = maxId;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public long getMaxId() {
        return maxId;
    }

    public void setMaxId(long maxId) {
        this.maxId = maxId;
    }
}
