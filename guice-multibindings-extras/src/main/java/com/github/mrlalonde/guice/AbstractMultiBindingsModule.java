package com.github.mrlalonde.guice;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.inject.AbstractModule;
import com.google.inject.Binder;
import com.google.inject.Key;
import com.google.inject.PrivateModule;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.name.Names;

/**
 * {@link AbstractModule} that wraps a PrivateModule to expose multiple
 * implmentation via MultiBindings.
 * 
 * @author mrlalonde
 * 
 * @param <T>
 *            The interface type to which we want to contribute 1..many
 *            implementations
 */
public abstract class AbstractMultiBindingsModule<T> extends AbstractModule {

    /**
     * Support class for contributing MultiBindings via a {@link PrivateModule}.
     * 
     * @author mathieu
     * 
     */
    public abstract class PrivateMultiBindingsModule extends PrivateModule {
	private int counter = 0;
	private final Map<Key<? extends T>, Class<? extends T>> multiBindings = new HashMap<Key<? extends T>, Class<? extends T>>();
	private final Class<T> interfaceClass;

	protected PrivateMultiBindingsModule(Class<T> interfaceClass) {
	    super();
	    this.interfaceClass = interfaceClass;
	}

	private <I extends T> void setUpMultiBindingFor(Class<I> implementationClass) {
	    Key<I> key = getKey(implementationClass);
	    bind(key).to(implementationClass);
	    expose(key);
	    multiBindings.put(key, implementationClass);
	}

	private <I extends T> Key<I> getKey(Class<I> type) {
	    return Key.get(type, Names.named(getClass().toString() + counter++));
	}

	protected final void registerMultiBindings(Binder binder) {
	    Multibinder<T> multiBinder = Multibinder.newSetBinder(binder, interfaceClass);
	    for (Entry<Key<? extends T>, Class<? extends T>> multiBinding : multiBindings.entrySet()) {
		multiBinder.addBinding().to(multiBinding.getKey());
	    }
	}

	/**
	 * Starts the exposure of MultiBindings via an EDSL.
	 * @return
	 */
	protected final MultiBindingExposer exposeMultiBinding() {
	    return new MultiBindingExposer();
	}

	/**
	 * Class to help expose Multi-Bindings through an EDSL.
	 */
	public final class MultiBindingExposer {
	    /**
	     * Completes the exposure of a multibinding to the given
	     * implementation class.
	     * 
	     * @param implementationClass
	     */
	    public final <I extends T> void to(Class<I> implementationClass) {
		bind(implementationClass);
		setUpMultiBindingFor(implementationClass);
	    }
	}
    }

    @Override
    protected final void configure() {
	PrivateMultiBindingsModule privateMultiBindingModule = newPrivateMultiBindingsModule();
	install(privateMultiBindingModule);
	privateMultiBindingModule.registerMultiBindings(binder());
    }

    /**
     * 
     * @return Your own implmentation that will configure implementations that
     *         you want to contribute as multi-bindings
     */
    protected abstract PrivateMultiBindingsModule newPrivateMultiBindingsModule();
}
