package net.simplyrin.protocolchanger;

import java.io.File;
import java.io.IOException;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import net.md_5.bungee.event.EventHandler;

public class BungeeProtocolChanger extends Plugin implements Listener {

	private BungeeProtocolChanger plugin;
	private File file_config;

	public void onEnable() {
		plugin = this;

		try {
			configuration();
		} catch (IOException e) {
			e.printStackTrace();
		}

		BungeeCord.getInstance().getPluginManager().registerListener(this, this);
	}

	private void configuration() throws IOException {
		if (!plugin.getDataFolder().exists()) {
			plugin.getDataFolder().mkdir();
		}

		File file = new File(getDataFolder(), "config.yml");
		this.file_config = file;

		if(!file.exists()) {
			file.createNewFile();

			Configuration config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);

			config.set("protocol", "BungeeCord " + BungeeCord.getInstance().getGameVersion());

			ConfigurationProvider.getProvider( YamlConfiguration.class).save(config, file);
		}
	}

	@EventHandler
	public void onPing(ProxyPingEvent event) throws IOException {
		ServerPing ping = event.getResponse();

		Configuration config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file_config);
		ping.getVersion().setName(config.getString("protocol"));
	}
}

