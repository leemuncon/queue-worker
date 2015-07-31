package me.leefly.message;

/**
 * Created by Administrator on 2015/7/30.
 * 任务分配服务器
 */
public interface MessageAllotService<T> extends MessageTaskService{

    /**
     * 分发服务服务
     * @param msg 要分发的内容
     */
    void allot(T msg);

}
