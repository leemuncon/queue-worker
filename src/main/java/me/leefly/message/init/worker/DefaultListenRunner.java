package me.leefly.message.init.worker;

import com.ultrapower.rw.message.MessageTaskService;
import com.ultrapower.rw.message.worker.Doer;
import com.ultrapower.rw.message.worker.Runner;
import org.apache.log4j.Logger;

import java.util.Map;

/**
 * Created by Administrator on 2015/7/31.
 * 监听工作任务
 * @author lifei
 * @version 1.0
 */
public class DefaultListenRunner implements Runner {

    private Logger logger = Logger.getLogger(DefaultListenRunner.class);

    private Map<MessageTaskService, Doer> services;

    public DefaultListenRunner(Map<MessageTaskService, Doer> services){
        this.services = services;
    }

    @Override
    public void run() {
        for (MessageTaskService key : services.keySet()){
            if (!key.isAlive()){
                logger.info("MessageService [" + key + "] restart is begin");
                Doer doer = services.get(key);
                key.init(doer);
                logger.info("MessageService [" + key + "] restart was finished");
            }
        }
    }

}
