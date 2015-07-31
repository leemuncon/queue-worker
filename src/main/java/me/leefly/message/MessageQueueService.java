package me.leefly.message;


import com.ultrapower.rw.message.worker.Caller;
import com.ultrapower.rw.message.worker.Swaper;
import com.ultrapower.rw.message.worker.Worker;

/**
 * Created by Administrator on 2015/7/31.
 * <p/>
 * 集成完全功能的队列
 * @param <S> 待处理信息类型
 * @param <T> 处理完成的信息类型
 */
public interface MessageQueueService<S, T> extends MessageJoinService<T>, MessageAllotService<T>, MessageSwapService<S, T> {

    /**
     * 开始服务
     * @param worker
     * @param caller
     * @param swaper
     */
    void start(Worker<T> worker, Caller<S, T> caller, Swaper<S, T> swaper);

    /**
     * 是否存活
     * @return
     */
    boolean isAlive();

    /**
     * 销毁服务
     */
    void destroy();

}
