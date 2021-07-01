package me.woop.flame.player;

import lombok.AllArgsConstructor;
import me.woop.flame.queue.Queue;
import me.woop.flame.queue.QueueHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@AllArgsConstructor
public class QueuePlayerListener implements Listener {

    private final QueueHandler queueHandler;

    @EventHandler //We cannot use the AsyncPlayerJoinEvent as, we need to check for permissions and on that event the player
    //Still did not log in yet.
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (queueHandler.isCreated(player.getUniqueId())) return;

        for (int i = 0; i < 10; i++) {
            String permission = "queue.position." + i;

            if (!player.hasPermission(permission)) return;

            new QueuePlayer(player.getUniqueId(), i, null);
            break;
        }
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        QueuePlayer queuePlayer = queueHandler.getPlayer(player.getUniqueId());
        Queue queue = queuePlayer.getQueue();

        if (queuePlayer == null) return;

        if (queue != null)
            queue.remove(queuePlayer);

        queueHandler.getQueuePlayerMap().remove(player.getUniqueId());
    }

}
