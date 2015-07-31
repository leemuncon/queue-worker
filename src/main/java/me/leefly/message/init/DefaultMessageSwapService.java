package me.leefly.message.init;

import com.ultrapower.rw.message.AbstractMessageSwapService;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;

/**
 * Created by Administrator on 2015/7/30.
 */
public class DefaultMessageSwapService<S, T> extends AbstractMessageSwapService<S, T> {

    /**
     * 构造转换服务
     * @param source 源队列
     * @param target 咪表队列
     * @param executor 线程池
     */
    public DefaultMessageSwapService(BlockingQueue<S> source, BlockingQueue<T> target, ExecutorService executor) {
        super(source, target, executor);
    }

    @Override
    public BlockingQueue<S> getResouce() {
        return source;
    }

    @Override
    public BlockingQueue<T> getTarget() {
        return target;
    }
}
