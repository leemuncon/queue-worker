package me.leefly.message.task;

import com.ultrapower.rw.message.worker.Caller;
import org.apache.log4j.Logger;

import java.util.concurrent.BlockingQueue;

/**
 * Created by Administrator on 2015/7/30.
 * <p/>
 * 单次任务执行，并将执行结果压入队列
 *
 * @param <S> 待处理信息类型
 * @param <T> 处理完成的信息类型
 * @author lifei
 * @version 1.0
 */
public class MessageCallerTask<S, T> implements Runnable {

    private static Logger LOG = Logger.getLogger(MessageCallerTask.class);

    private BlockingQueue<T> queue;

    private Caller<S, T> caller;

    private S msg;

    /**
     * 创建任务
     *
     * @param queue  队列
     * @param caller 执行器
     * @param msg    要处理信息
     */
    public MessageCallerTask(BlockingQueue<T> queue, Caller<S, T> caller, S msg) {
        this.queue = queue;
        this.caller = caller;
        this.msg = msg;
    }

    @Override
    public void run() {
        try {
            T rst = caller.call(msg);
            queue.put(rst);
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            LOG.error("MessageCallerTask [" + this + "] was interrupted");
        }
    }

}
