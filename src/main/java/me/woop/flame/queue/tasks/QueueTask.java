package me.woop.flame.queue.tasks;

import lombok.AllArgsConstructor;
import me.woop.flame.queue.QueueHandler;
import me.woop.flame.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

@AllArgsConstructor
public class QueueTask extends BukkitRunnable {

    private final QueueHandler queueHandler;

    @Override
    public void run() {
        queueHandler.getQueues().forEach(queue -> {
            queue.move();

            queue.getQueuePlayerList().forEach(queuePlayer -> {
                Player player = Bukkit.getPlayer(queuePlayer.getUuid());

                if (player == null) return;

                player.sendMessage(" ");
                player.sendMessage(CC.translate("&aYou are currently position &7" +
                        (queue.getPosition(queuePlayer) + 1) + " &aof &7" + queue.getQueuePlayerList().size() + " &aplayers inside the queue &7" + queue.getName() + "&a!"));
            });
        });
    }
}
