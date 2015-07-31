package me.leefly.message;

import com.ultrapower.rw.message.exception.IllegalWorkerException;
import com.ultrapower.rw.message.task.MessageWorkerTask;
import com.ultrapower.rw.message.worker.Doer;
import com.ultrapower.rw.message.worker.Worker;
import org.apache.log4j.Logger;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * Created by Administrator on 2015/7/28.
 * <p/>
 * 消息队列service, 实现MessageService，MessageTaskService
 *
 * @author lifei
 * @version 1.0
 * @param <T> 处理的信息类型
 */
public abstract class AbstractMessageJoinService<T> implements MessageJoinService<T>, MessageTaskService {

    protected static Logger logger = Logger.getLogger(AbstractMessageJoinService.class);

    protected BlockingQueue<T> queue;

    protected ExecutorService executor;

    protected Future<?> future; // 主线程执行结果

    public AbstractMessageJoinService(final BlockingQueue<T> queue, ExecutorService executor) {
        this.queue = queue;
        this.executor = executor;
    }


    @Override
    public void init(Doer doer) {
        if (isAlive()) {
            if (doer instanceof Worker) {
                Worker<T> worker = (Worker<T>) doer;
                MessageWorkerTask<T> task = new MessageWorkerTask<T>(queue, worker);
                future = executor.submit(task);
            }else{
                throw new IllegalWorkerException("Class was not allowed, the param Type mast be " + Worker.class);
            }
        }
    }

    @Override
    public boolean isAlive() {
        if (future == null || future.isDone() || future.isCancelled())
            return false;
        return true;
    }

    @Override
    public void destroy() {
        future.cancel(true);
    }
}
