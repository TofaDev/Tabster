package me.whereareiam.tabster.velocity;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import me.whereareiam.tabster.core.AbstractTabster;
import me.whereareiam.tabster.velocity.listener.VelocityListenerRegistrar;

import java.nio.file.Path;

public class TabsterVelocity extends AbstractTabster {
	private final ProxyServer proxyServer;
	private final Path dataPath;

	@Inject
	public TabsterVelocity(ProxyServer proxyServer, @DataDirectory Path dataPath) {
		this.proxyServer = proxyServer;
		this.dataPath = dataPath;
	}

	@Subscribe
	public void onProxyInitialization(ProxyInitializeEvent event) {
		injector = Guice.createInjector(new TabsterVelocityConfig(this, proxyServer, dataPath));
		super.onProxyInitialization();

		injector.getInstance(VelocityListenerRegistrar.class).registerListeners();
	}

	@Subscribe
	public void onProxyShutdown(ProxyShutdownEvent event) {
		super.onProxyShutdown();
	}
}