package com.github.mrlalonde.guice;

import java.util.HashSet;
import java.util.Set;

import com.google.inject.AbstractModule;
import com.google.inject.Binder;
import com.google.inject.Key;
import com.google.inject.PrivateModule;
import com.google.inject.multibindings.Multibinder;

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
     * @author mrlalonde
     * 
     */
    public abstract class PrivateMultiBindingsModule extends PrivateModule {
	private final Set<Key<? extends T>> multiBindingKeys = new HashSet<Key<? extends T>>();
	private final Class<T> interfaceClass;
	private final BindingKeyGenerator<T> bindingKeyGenerator;
	
	protected PrivateMultiBindingsModule(Class<T> interfaceClass) {
	    super();
	    this.interfaceClass = interfaceClass;
	    bindingKeyGenerator = new BindingKeyGenerator<T>(this);
	}

	private <I extends T> void setUpMultiBindingFor(Class<I> implementationClass) {
	    Key<I> key = bindingKeyGenerator.generateKey(implementationClass);
	    bind(key).to(implementationClass);
	    setUpMultiBindingFor(key);
	}
	
	private <I extends T> void setUpMultiBindingFor(Key<I> key) {
	    expose(key);
	    multiBindingKeys.add(key);
	}

	protected final void registerMultiBindings(Binder binder) {
	    Multibinder<T> multiBinder = Multibinder.newSetBinder(binder, interfaceClass);
	    for (Key<? extends T> bindingKey : multiBindingKeys) {
		multiBinder.addBinding().to(bindingKey);
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
     * @return Your own implementation that will configure implementations that
     *         you want to contribute as multi-bindings
     */
    protected abstract PrivateMultiBindingsModule newPrivateMultiBindingsModule();
}
