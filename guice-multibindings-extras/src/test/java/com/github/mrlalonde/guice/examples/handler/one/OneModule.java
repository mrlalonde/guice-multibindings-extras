package com.github.mrlalonde.guice.examples.handler.one;

import com.github.mrlalonde.guice.AbstractMultiBindingsModule;
import com.github.mrlalonde.guice.examples.handler.Builder;
import com.github.mrlalonde.guice.examples.handler.Handler;

public final class OneModule extends AbstractMultiBindingsModule<Handler> {

    @Override
    protected AbstractMultiBindingsModule<Handler>.PrivateMultiBindingsModule newPrivateMultiBindingsModule() {
	return new PrivateMultiBindingsModule(Handler.class) {

	    @Override
	    protected void configure() {
		bind(Builder.class).to(BuilderOne.class);
		exposeMultiBinding().to(OneHandler.class);
	    }
	};
    }

}
