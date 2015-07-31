package me.leefly.message.exception;

/**
 * Created by Administrator on 2015/7/29.
 * 验证工作器失败异常
 * @author lifei
 * @version 1.0
 */
public class IllegalWorkerException extends RuntimeException {

    public IllegalWorkerException(){
        super();
    }

    public IllegalWorkerException(String msg){
        super(msg);
    }

}
