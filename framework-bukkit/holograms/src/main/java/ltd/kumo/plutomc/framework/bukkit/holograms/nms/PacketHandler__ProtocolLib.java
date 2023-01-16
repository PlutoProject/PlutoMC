package ltd.kumo.plutomc.framework.bukkit.holograms.nms;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import ltd.kumo.plutomc.framework.bukkit.holograms.PlutoHologramsAPI;
import org.bukkit.entity.Player;

public class PacketHandler__ProtocolLib {

    public PacketHandler__ProtocolLib() {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        protocolManager.addPacketListener(new PacketAdapter(PacketAdapter.params(PlutoHologramsAPI.get().getPlugin(), PacketType.Play.Client.USE_ENTITY).optionAsync().listenerPriority(ListenerPriority.NORMAL)) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                if (event.getPacketType().equals(PacketType.Play.Client.USE_ENTITY)) {
                    PacketContainer packetContainer = event.getPacket();
                    Object packet = packetContainer.getHandle();
                    Player player = event.getPlayer();
                    if (PacketHandlerCommon.handlePacket(packet, player)) {
                        event.setCancelled(true);
                    }
                }
            }
        });
    }

}
