package me.leefly.message.task;

import com.ultrapower.rw.message.worker.Swaper;
import org.apache.log4j.Logger;

import java.util.concurrent.BlockingQueue;

/**
 * Created by Administrator on 2015/7/30.
 * <p/>
 * 交换队列任务
 *
 * @param <S> 待处理信息类型
 * @param <T> 处理完成的信息类型
 * @author lifei
 * @version 1.0
 */
public class MessageSwaperTask<S, T> implements Runnable {

    private static Logger LOG = Logger.getLogger(MessageSwaperTask.class);

    private BlockingQueue<S> source;

    private BlockingQueue<T> target;

    private Swaper<S, T> swaper;

    public MessageSwaperTask(BlockingQueue<S> source, BlockingQueue<T> target, Swaper<S, T> swaper) {
        this.source = source;
        this.target = target;
        this.swaper = swaper;
    }

    @Override
    public void run() {
        while (true) {
            try {
                swaper.swap(source, target);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                LOG.error("MessageSwaperTask [" + this + "] was interrupted");
            }
        }
    }

}
