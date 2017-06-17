package de.github.GSGJ.API.worker;

/**
 * Created by Kojy on 17.06.2017.
 */
public interface Worker<T> extends Runnable {
    void processData(T obj);

    void handle(T obj);
}
