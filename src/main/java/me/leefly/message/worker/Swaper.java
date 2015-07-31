package me.leefly.message.worker;

import java.util.concurrent.BlockingQueue;

/**
 * Created by Administrator on 2015/7/30.
 * <p/>
 * 将个队列的内容放到另一个队列
 * <p/>
 * @author lifei
 * @version 1.0
 */
public interface Swaper<S, T> extends Doer {

    /**
     * 将源队列的信息放到目标队列
     *
     * @param source
     * @param target
     */
    void swap(BlockingQueue<S> source, BlockingQueue<T> target) throws InterruptedException;

}
