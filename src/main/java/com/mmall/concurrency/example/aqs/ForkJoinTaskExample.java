package com.mmall.concurrency.example.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

@Slf4j
public class ForkJoinTaskExample extends RecursiveTask<Integer> {

    public static final int threashold = 2;
    private int start;
    private int end;


    public ForkJoinTaskExample(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum = 0;

        boolean canCompute = (end - start) <= threashold;
        if (canCompute) {
            for (int i = start; i <= end; i++) {
                sum += i;
            }
        } else {
            int middle = (start + end) / 2;
            ForkJoinTaskExample leftTask = new ForkJoinTaskExample(start,middle);
            ForkJoinTaskExample rightTask = new ForkJoinTaskExample(middle + 1, end);

            leftTask.fork();
            rightTask.fork();

            int leftResult = leftTask.join();
            int rightResult = rightTask.join();

            sum = leftResult + rightResult;
        }

        return sum;
    }

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        //生成一个计算任务， 计算1+2+3+++
        ForkJoinTaskExample taskExample = new ForkJoinTaskExample(1, 100);
        //执行一个任务
        Future<Integer> result = forkJoinPool.submit(taskExample);

        try {
            log.info("reuslt:{}", result.get());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
