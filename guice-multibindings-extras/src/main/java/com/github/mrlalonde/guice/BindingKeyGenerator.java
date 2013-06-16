package com.github.mrlalonde.guice;

import com.google.inject.Key;
import com.google.inject.Module;
import com.google.inject.name.Names;

/**
 * Class to generate Binding Keys.
 * 
 * @author mrlalonde
 * 
 * @param <K>
 *            Interface type for which the binding key will be created.
 */
final class BindingKeyGenerator<T> {
    private final Module module;

    private int counter = 0;

    BindingKeyGenerator(Module module) {
	this.module = module;
    }

    /**
     * Each call to this method generates a unique binding key based on the
     * module fully qualified name and a counter.
     * 
     * @param implementationType
     * @return unique Binding key
     */
    <I extends T> Key<I> generateKey(Class<I> implementationType) {
	return Key.get(implementationType, Names.named(module.getClass().toString() + counter++));
    }

}
