package me.leefly.message.worker;

import java.util.concurrent.BlockingQueue;

/**
 * Created by Administrator on 2015/7/29.
 * <p/>
 * 工作接口，使用队列任务需继承
 * @author lifei
 * @version 1.0
 */
public interface Worker<T> extends Doer {


    /**
     * 从队列读取的工作方法
     * @param queue 要处理的队列
     * @throws InterruptedException
     */
    void work(BlockingQueue<T> queue) throws InterruptedException;

}
