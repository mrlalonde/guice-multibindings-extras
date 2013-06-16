package com.github.mrlalonde.guice.examples.handler.two;

import com.github.mrlalonde.guice.AbstractMultiBindingsModule;
import com.github.mrlalonde.guice.examples.handler.Builder;
import com.github.mrlalonde.guice.examples.handler.Handler;

public final class TwoModule extends AbstractMultiBindingsModule<Handler> {

	@Override
	protected AbstractMultiBindingsModule<Handler>.PrivateMultiBindingsModule newPrivateMultiBindingsModule() {
		return new PrivateMultiBindingsModule(Handler.class) {

			@Override
			protected void configure() {
				bind(Builder.class).to(BuilderTwo.class);
				exposeMultiBinding().to(TwoHandler.class);
			}
		};
	}

}
