package me.leefly.message.task;

import com.ultrapower.rw.message.worker.Worker;
import org.apache.log4j.Logger;

import java.util.concurrent.BlockingQueue;

/**
 * Created by Administrator on 2015/7/13.
 * <p/>
 * 主任务, 轮询执行
 *
 * @param <T> 处理的信息类型
 * @author lifei
 * @version 1.0
 */
public class MessageWorkerTask<T> implements Runnable {

    private static Logger LOG = Logger.getLogger(MessageWorkerTask.class);

    private BlockingQueue<T> queue;

    private Worker<T> worker;

    /**
     * 创建任务
     *
     * @param queue  队列
     * @param worker 工作器
     */
    public MessageWorkerTask(BlockingQueue<T> queue, Worker<T> worker) {
        this.queue = queue;
        this.worker = worker;
    }

    @Override
    public void run() {
        while (true) {
            try {
                worker.work(queue);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                LOG.error("MessageWorkerTask [" + this + "[ was interrupted");
            }
        }
    }

}
