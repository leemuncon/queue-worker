package me.leefly.message.worker;

/**
 * Created by Administrator on 2015/7/29.
 * <p/>
 * 工作接口，可以返回指定参数
 * @author lifei
 * @version 1.0
 */
public interface Caller<S, T> extends Doer {

    /**
     * 将处理过的数据放入队列的执行方法
     * @param msg 处理的信息
     * @return
     * @throws InterruptedException
     */
     T call(S msg) throws InterruptedException;

}
