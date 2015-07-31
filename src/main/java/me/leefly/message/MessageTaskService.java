package me.leefly.message;

import com.ultrapower.rw.message.worker.Doer;

/**
 * Created by Administrator on 2015/7/28.
 * <p/>
 * 服务状态的服务
 *
 * @author lifei
 * @version 1.0
 */
public interface MessageTaskService {

    /**
     * 初始化任务
     * @param doer 工作器包装
     */
    void init(Doer doer);

    /**
     * 主任务是否存活
     * @return
     */
    boolean isAlive();

    /**
     * 销毁
     */
    void destroy();

}
