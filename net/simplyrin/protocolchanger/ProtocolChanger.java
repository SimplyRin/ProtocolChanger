package net.simplyrin.protocolchanger;

import java.util.Arrays;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerOptions;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedServerPing;

public class ProtocolChanger extends JavaPlugin implements Listener {

	private ProtocolChanger plugin;

	public void onEnable() {
		plugin = this;

		saveDefaultConfig();

		ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(this, ListenerPriority.NORMAL,
				Arrays.asList(PacketType.Status.Server.OUT_SERVER_INFO), ListenerOptions.ASYNC) {
			public void onPacketSending(PacketEvent event) {
				onPing(event.getPacket().getServerPings().read(0));
			}
		});
	}

	private void onPing(WrappedServerPing ping) {
		String name = plugin.getConfig().getString("protocol");
		ping.setVersionName(name);
	}
}
