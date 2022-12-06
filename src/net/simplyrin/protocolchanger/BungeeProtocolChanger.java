package net.simplyrin.protocolchanger;

import java.io.File;
import java.io.IOException;

import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import net.md_5.bungee.event.EventHandler;

public class BungeeProtocolChanger extends Plugin implements Listener {
	private String protocol;

	@Override
	public void onEnable() {
		try {
			configuration();
		} catch (IOException e) {
			e.printStackTrace();
		}

		getProxy().getPluginManager().registerListener(this, this);
	}

	private void configuration() throws IOException {
		final File dataFolder = getDataFolder();

		if (!dataFolder.exists()) {
			dataFolder.mkdir();
		}

		final File file = new File(dataFolder, "config.yml");

		if (!file.exists()) {
			file.createNewFile();
		}

		final ConfigurationProvider provider = ConfigurationProvider.getProvider(YamlConfiguration.class);
		final Configuration config = provider.load(file);

		if (config.get("protocol") == null) {
			config.set("protocol", "BungeeCord " + getProxy().getGameVersion());

			provider.save(config, file);
		}

		this.protocol = config.getString("protocol");
	}

	@EventHandler
	public void onPing(final ProxyPingEvent event) throws IOException {
		final ServerPing ping = event.getResponse();

		if (ping == null) {
			return;
		}

		ping.getVersion().setName(protocol);
	}
}
