package com.githum.mrlalonde.guice;

import static org.junit.Assert.*;

import org.junit.Test;

import com.github.mrlalonde.guice.examples.handler.combiner.HandlerCombiner;
import com.github.mrlalonde.guice.examples.handler.combiner.HandlerCombinerModule;
import com.github.mrlalonde.guice.examples.handler.one.OneModule;
import com.github.mrlalonde.guice.examples.handler.two.TwoModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class AbstractMultiBindingsModuleTest {

    @Test
    public void multiBindingsAreContributed() {
	Injector injector = Guice.createInjector(new OneModule(), new TwoModule(), new HandlerCombinerModule());
	HandlerCombiner combiner = injector.getInstance(HandlerCombiner.class);
	
	assertNotNull(combiner);
	assertEquals(3, combiner.getHandlers().size());
    }

}
