package de.github.GSGJ.API.worker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Kojy on 17.06.2017.
 */
public abstract class AbstractWorker<T> implements Worker<T> {
    protected Logger logger = LoggerFactory.getLogger(AbstractWorker.class);
    protected List<T> queue = new LinkedList<T>();

    @Override
    public void processData(T obj){
        synchronized (queue){
            queue.add(obj);
            queue.notify();
        }
    }

    @Override
    public void run(){
        T obj;
        while (true){
            synchronized (queue){
                while (queue.isEmpty()){
                    try {
                        queue.wait();
                    } catch (InterruptedException e){
                        logger.error(e.getMessage(),e);
                    }
                }
                obj = queue.remove(0);
            }
            handle(obj);
        }
    }

    @Override
    public abstract void handle(T obj);
}
