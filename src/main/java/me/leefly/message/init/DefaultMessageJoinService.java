package me.leefly.message.init;

import com.ultrapower.rw.message.AbstractMessageJoinService;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;

/**
 * Created by Administrator on 2015/7/13.
 * <p/>
 * 默认队列服务
 * @author lifei
 * @version 1.0
 */
public class DefaultMessageJoinService<T> extends AbstractMessageJoinService<T> {

    /**
     * 构造函数
     * @param queue 队列
     * @param executor 线程池
     */
    public DefaultMessageJoinService(BlockingQueue<T> queue, ExecutorService executor) {
        super(queue, executor);
    }

    @Override
    public boolean add(T msg) {
        return queue.offer(msg);
    }

}
