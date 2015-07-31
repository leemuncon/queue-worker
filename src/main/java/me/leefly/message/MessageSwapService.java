package me.leefly.message;

import java.util.concurrent.BlockingQueue;

/**
 * Created by Administrator on 2015/7/30.
 * <p/>
 * 转换队列信息
 *
 * @author lifei
 * @version 1.0
 * @param <S> 待处理信息类型
 * @param <T> 处理完成的信息类型
 */
public interface MessageSwapService<S, T> extends MessageTaskService{

    /**
     * 获取源队列
     * @return
     */
    BlockingQueue<S> getResource();

    /**
     * 获取目标队列
     * @return
     */
    BlockingQueue<T> getTarget();

}
