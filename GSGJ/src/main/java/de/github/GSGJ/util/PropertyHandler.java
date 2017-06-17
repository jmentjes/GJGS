package de.github.GSGJ.util;

/**
 * Created by Kojy on 17.06.2017.
 */
public interface PropertyHandler {
    String read(String key);

    boolean write(String key, String value);
}
