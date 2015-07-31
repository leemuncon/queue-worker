package me.leefly.message;

import com.ultrapower.rw.message.init.MessageServiceFactory;
import com.ultrapower.rw.message.init.worker.DefaultListenRunner;
import com.ultrapower.rw.message.task.MessageListenerTask;
import com.ultrapower.rw.message.worker.*;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Administrator on 2015/7/31.
 */
public class DiMessageQueService<S, T> implements MessageQueueService<S, T> {

    protected static String JOIN_SERVICE = "JOIN_SERVICE";

    protected static String ALLOT_SERVICE = "ALLOT_SERVICE";

    protected static String SWAP_SERVICE = "SWAP_SERVICE";

    protected static Logger logger = Logger.getLogger(DiMessageQueService.class);

    protected BlockingQueue<T> main;

    protected BlockingQueue<S> cache;

    protected ExecutorService executor;

    protected Future<?> future; // 守护线程

    Map<String, MessageTaskService> servers = new HashMap<String, MessageTaskService>();

    public DiMessageQueService(BlockingQueue<T> main, BlockingQueue<S> cache) {
        this.main = main;
        this.cache = cache;
        this.executor = Executors.newSingleThreadExecutor();
        servers.put(JOIN_SERVICE, MessageServiceFactory.initDefaultMessageJoinService(main));
        servers.put(ALLOT_SERVICE, MessageServiceFactory.initDefaultMessageAllotService(cache));
        servers.put(SWAP_SERVICE, MessageServiceFactory.initDefaultMessageSwapService(cache, main));
    }


    @Override
    public void start(Worker<T> worker, Caller<S, T> caller, Swaper<S, T> swaper) {
        Map<MessageTaskService, Doer> init_server = new HashMap<MessageTaskService, Doer>();
        for (MessageTaskService server : servers.values()){
            if (server instanceof MessageJoinService){
                init_server.put(server, worker);
                server.init(worker);
            }
            if (server instanceof MessageAllotService){
                init_server.put(server, caller);
                server.init(caller);
            }
            if (server instanceof MessageSwapService){
                init_server.put(server, swaper);
                server.init(swaper);
            }
        }
        Runner guard = new DefaultListenRunner(init_server);
        init(guard);
    }

    @Override
    public void init(Doer doer) {
        if (doer instanceof Runner){
            if (future == null || future.isCancelled() || future.isDone()){
                Runner runner = (Runner) doer;
                MessageListenerTask task = new MessageListenerTask(runner);
                future = executor.submit(task);
            }
        }
    }

    @Override
    public boolean isAlive() {
        for (MessageTaskService server : servers.values()){
            if (!server.isAlive())
                return false;
        }
        return true;
    }

    @Override
    public void destroy() {
        future.cancel(true);
        for (MessageTaskService server : servers.values()){
            server.destroy();
        }
    }

    @Override
    public void allot(T msg) {
        if (servers.get(ALLOT_SERVICE) instanceof MessageAllotService){
            MessageAllotService<T> _server = (MessageAllotService<T>) servers.get(ALLOT_SERVICE);
            _server.allot(msg);
        }
    }

    @Override
    public boolean add(T msg) {
        if (servers.get(JOIN_SERVICE) instanceof MessageJoinService){
            MessageJoinService<T> _server = (MessageJoinService<T>) servers.get(JOIN_SERVICE);
            return  _server.add(msg);
        }
        return false;
    }

    @Override
    public BlockingQueue<S> getResource() {
        if (servers.get(SWAP_SERVICE) instanceof MessageSwapService){
            MessageSwapService<S, T> _server = (MessageSwapService<S, T>) servers.get(ALLOT_SERVICE);
            _server.getResource();
        }
        return cache;
    }

    @Override
    public BlockingQueue<T> getTarget() {
        if (servers.get(SWAP_SERVICE) instanceof MessageSwapService){
            MessageSwapService<S, T> _server = (MessageSwapService<S, T>) servers.get(ALLOT_SERVICE);
            _server.getTarget();
        }
        return main;
    }
}
