package me.leefly.message;

import com.ultrapower.rw.message.exception.IllegalWorkerException;
import com.ultrapower.rw.message.task.MessageSwaperTask;
import com.ultrapower.rw.message.worker.Doer;
import com.ultrapower.rw.message.worker.Swaper;
import org.apache.log4j.Logger;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * Created by Administrator on 2015/7/30.
 * <p/>
 * 消息转换service
 *
 * @param <S> 待处理信息类型
 * @param <T> 处理完成的信息类型
 * @author lifei
 * @version 1.0
 */
public abstract class AbstractMessageSwapService<S, T> implements MessageSwapService<S, T>, MessageTaskService {

    protected static Logger logger = Logger.getLogger(AbstractMessageSwapService.class);

    protected BlockingQueue<S> source;

    protected BlockingQueue<T> target;

    protected ExecutorService executor;

    protected Future<?> future; // 主线程执行结果

    public AbstractMessageSwapService(BlockingQueue<S> source, BlockingQueue<T> target, ExecutorService executor){
        this.source = source;
        this.target = target;
        this.executor = executor;
    }

    @Override
    public void init(Doer doer) {
        if (isAlive()) {
            if (doer instanceof Swaper) {
                Swaper<S, T> swaper = (Swaper<S, T>) doer;
                MessageSwaperTask<S, T> task = new MessageSwaperTask<S, T>(source, target, swaper);
                future = executor.submit(task);
            }else{
                throw new IllegalWorkerException("Class was not allowed, the param Type mast be " + Swaper.class);
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
