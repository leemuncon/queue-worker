package me.leefly.message;

import com.ultrapower.rw.message.worker.Caller;
import com.ultrapower.rw.message.worker.Doer;
import org.apache.log4j.Logger;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2015/7/30.
 * <p/>
 * 分发服务
 *
 * @author lifei
 * @version 1.0
 * @param <S> 待处理信息类型
 * @param <T> 处理完成的信息类型
 */
public abstract class AbstractMessageAllotService<S, T> implements MessageAllotService<S>, MessageTaskService {

    protected static Logger logger = Logger.getLogger(AbstractMessageAllotService.class);

    protected BlockingQueue<T> queue;

    protected ExecutorService executor;

    protected Caller<S, T> caller;

    public AbstractMessageAllotService(BlockingQueue<T> queue, ExecutorService executor){
        this.queue = queue;
        this.executor = executor;
    }


    @Override
    public void init(Doer doer) {
        if (doer instanceof Caller){
            this.caller = (Caller<S, T>) doer;
            if (!isAlive())
                executor = Executors.newCachedThreadPool();
        }
    }

    @Override
    public boolean isAlive() {
        return executor.isShutdown();
    }

    @Override
    public void destroy() {
        executor.shutdown();
    }
}
