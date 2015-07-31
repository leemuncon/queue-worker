package me.leefly.message.init;

import com.ultrapower.rw.message.AbstractMessageAllotService;
import com.ultrapower.rw.message.task.MessageCallerTask;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;

/**
 * Created by Administrator on 2015/7/13.
 * <p/>
 * 默认队列服务
 * @author lifei
 * @version 1.0
 */
public class DefaultMessageAllotService<S, T> extends AbstractMessageAllotService<S, T> {

    /**
     * 构造方法
     * @param queue 目标队列
     * @param executor 线程池
     */
    public DefaultMessageAllotService(BlockingQueue<T> queue, ExecutorService executor) {
        super(queue, executor);
    }

    @Override
    public void allot(S msg) {
        MessageCallerTask<S, T> task = new MessageCallerTask<S, T>(queue, caller,  msg);
        executor.execute(task);
    }
}
