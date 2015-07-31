package me.leefly.message.init;

import com.ultrapower.rw.message.MessageAllotService;
import com.ultrapower.rw.message.MessageJoinService;
import com.ultrapower.rw.message.MessageSwapService;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2015/7/13.
 * <p/>
 * 创建默认服务的工厂
 * @author lifei
 * @version 1.0
 */
public class MessageServiceFactory {

    /**
     * 创建默认的消息处理队列
     * @param queue 队列
     * @param <T> 类型
     * @return
     */
    public static <T> MessageJoinService<T> initDefaultMessageJoinService(BlockingQueue queue) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        DefaultMessageJoinService<T> service = new DefaultMessageJoinService(queue, executor);
        return service;
    }

    /**
     * 创建默认的消息结果添加队列
     * @param queue 目标队列
     * @param <S> 源类型
     * @param <T> 目标类型
     * @return
     */
    public static <S, T> MessageAllotService<S> initDefaultMessageAllotService(BlockingQueue<T> queue) {
        ExecutorService executor = Executors.newCachedThreadPool();
        DefaultMessageAllotService<S, T> service = new DefaultMessageAllotService(queue, executor);
        return service;
    }

    /**
     * 创建默认的队列转换服务
     * @param source 源队列
     * @param target 目标队列
     * @param <S> 源类型
     * @param <T> 目标类型
     * @return
     */
    public static <S, T> MessageSwapService<S, T> initDefaultMessageSwapService(BlockingQueue<S> source, BlockingQueue<T> target) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        DefaultMessageSwapService service = new DefaultMessageSwapService(source, target, executor);
        return service;
    }

}
