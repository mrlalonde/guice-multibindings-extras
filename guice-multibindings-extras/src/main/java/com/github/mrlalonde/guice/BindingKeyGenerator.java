package com.github.mrlalonde.guice;

import com.google.inject.Key;
import com.google.inject.Module;
import com.google.inject.name.Names;

final class BindingKeyGenerator<K> {
    private final Module module;

    private int counter = 0;

    BindingKeyGenerator(Module module) {
	this.module = module;
    }
    
    <I extends K> Key<I> generateKey(Class<I> type)
    {
	return Key.get(type, Names.named(module.getClass().toString() + counter++));
    }
    
    
}
