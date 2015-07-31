package me.leefly.message.task;

import com.ultrapower.rw.message.worker.Runner;
import org.apache.log4j.Logger;

/**
 * Created by Administrator on 2015/7/14.
 * <p/>
 * 信息队列任务监听
 * @author lifei
 * @version 1.0
 */
public class MessageListenerTask<T> implements Runnable {

    private Logger logger = Logger.getLogger(MessageListenerTask.class);

    private Runner runner;

    /**
     * 创建监听任务
     */
    public MessageListenerTask(Runner runner) {
        this.runner = runner;
    }

    @Override
    public void run() {
        Thread.currentThread().setName("MessageService Guard Task");
        while (true) {
            try {
                runner.run();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.error("MessageService Guard Task was interrupted");
            }
        }
    }


}
