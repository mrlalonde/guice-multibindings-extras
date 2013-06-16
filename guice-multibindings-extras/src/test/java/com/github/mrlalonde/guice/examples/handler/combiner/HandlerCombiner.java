package com.github.mrlalonde.guice.examples.handler.combiner;

import java.util.Set;

import com.github.mrlalonde.guice.examples.handler.Handler;

public interface HandlerCombiner {

    public abstract Set<Handler> getHandlers();

}