package com.github.mrlalonde.guice.examples.handler.combiner;

import com.google.inject.AbstractModule;

public final class HandlerCombinerModule extends AbstractModule
{

    @Override
    protected void configure() {
	bind(HandlerCombiner.class).to(HandlerCombinerImpl.class);
    }

}
