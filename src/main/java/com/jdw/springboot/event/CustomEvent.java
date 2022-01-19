package com.jdw.springboot.event;

import org.springframework.context.ApplicationEvent;

import java.time.Clock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


/**
 * @author ListJiang
 * @since : 2022/1/18
 */
public class CustomEvent extends ApplicationEvent {


    public CustomEvent(Object source) {
        super(source);
    }

    public CustomEvent(Object source, Clock clock) {
        super(source, clock);
        ReadWriteLock lock = new ReentrantReadWriteLock();
    }
}