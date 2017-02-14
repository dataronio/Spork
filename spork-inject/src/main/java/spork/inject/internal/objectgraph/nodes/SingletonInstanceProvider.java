package spork.inject.internal.objectgraph.nodes;

import javax.annotation.Nonnull;
import javax.inject.Provider;

import spork.inject.internal.objectgraph.InjectSignature;

public class SingletonInstanceProvider<T> implements Provider<T> {
	private final InjectSignature injectSignature;
	private final ModuleMethodInvoker<T> moduleMethodInvoker;
	private final ScopedInstanceCache scopedInstanceCache;

	SingletonInstanceProvider(InjectSignature injectSignature, @Nonnull ModuleMethodInvoker<T> moduleMethodInvoker, ScopedInstanceCache scopedInstanceCache) {
		this.injectSignature = injectSignature;
		this.moduleMethodInvoker = moduleMethodInvoker;
		this.scopedInstanceCache = scopedInstanceCache;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T get() {
		synchronized (scopedInstanceCache) {
			Object instance = scopedInstanceCache.get(injectSignature);

			if (instance == null) {
				instance = moduleMethodInvoker.invoke();

				if (instance == null) {
					// TODO: throw a BindException
					throw new RuntimeException("singleton instances cannot be null");
				}

				scopedInstanceCache.put(injectSignature, instance);
			}

			return (T) instance;
		}
	}
}
