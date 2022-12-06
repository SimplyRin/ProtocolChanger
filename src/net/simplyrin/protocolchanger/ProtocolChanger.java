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
	@Override
	public void onEnable() {
		saveDefaultConfig();

		ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(this, ListenerPriority.NORMAL,
				Arrays.asList(PacketType.Status.Server.OUT_SERVER_INFO), ListenerOptions.ASYNC) {
			@Override
			public void onPacketSending(final PacketEvent event) {
				if (event.isCancelled()) {
					return;
				}

				final WrappedServerPing ping = event.getPacket().getServerPings().read(0);
				final String name = getConfig().getString("protocol");

				ping.setVersionName(name);
			}
		});
	}
}
