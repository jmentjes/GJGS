package de.github.GSGJ.com;

/**
 * Created by Kojy on 17.06.2017.
 */
public interface Server {
    void start();

    void stop();

    int getPort();

    String getAddress();
}
