package me.leefly.message;

/**
 * Created by Administrator on 2015/7/13.
 * <p/>
 * 信息入队任务服务
 *
 * @author lifei
 * @version 1.0
 * @param <T> 处理的信息类型
 */
public interface MessageJoinService<T> extends MessageTaskService{

    /**
     * 将信息放入队列
     *
     * @param msg
     * @return
     */
    boolean add(T msg);

}
