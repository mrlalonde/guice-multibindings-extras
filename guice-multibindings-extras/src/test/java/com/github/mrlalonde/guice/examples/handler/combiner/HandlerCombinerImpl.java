package com.github.mrlalonde.guice.examples.handler.combiner;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import com.github.mrlalonde.guice.examples.handler.Handler;

public class HandlerCombinerImpl implements HandlerCombiner {
    private final Set<Handler> handlers;

    @Inject
    HandlerCombinerImpl(Set<Handler> handlers) {
	this.handlers = handlers;
    }
    
    /* (non-Javadoc)
     * @see com.github.mrlalonde.guice.examples.handler.combiner.HandlerCombiner#Handlers()
     */
    @Override
    public Set<Handler> getHandlers()
    {
	return new HashSet<Handler>(handlers);
    }
}
