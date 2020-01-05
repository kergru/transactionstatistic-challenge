package com.kgr.challenge.transactionstatistics.model;


import java.math.BigDecimal;

import static java.math.RoundingMode.HALF_UP;

public class Statistics {

    private static final BigDecimal ZERO = BigDecimal.valueOf(0).setScale(2);
    private static final Statistics EMPTY = new Statistics(ZERO, 0);

    private long count;

    private BigDecimal sum;

    private Statistics(BigDecimal amount, long count) {
        this.sum = amount;
        this.count = count;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public BigDecimal getAvg() {
        return count <= 0L
                ? ZERO
                : sum.divide(new BigDecimal(count), 2, HALF_UP);
    }

    public static Statistics create(double amount) {
        return new Statistics(BigDecimal.valueOf(amount).setScale(2), 1);
    }

    public static Statistics empty() {
        return EMPTY;
    }

    public Statistics merge(Statistics statistics) {
        BigDecimal amount = this.sum.add(statistics.sum);
        long count = this.count + statistics.count;
        return new Statistics(amount, count);
    }

    @Override
    public String toString() {
        return "Statistics{" +
                ", count=" + count +
                ", sum=" + sum.doubleValue() +
                ", avg=" + getAvg() +
                '}';
    }
}
