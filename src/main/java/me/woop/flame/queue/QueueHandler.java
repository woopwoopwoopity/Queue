package me.woop.flame.queue;

import lombok.Getter;
import me.woop.flame.Flame;
import me.woop.flame.player.QueuePlayer;
import me.woop.flame.player.QueuePlayerListener;
import me.woop.flame.queue.tasks.QueueTask;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class QueueHandler {

    private final Map<UUID, QueuePlayer> queuePlayerMap = new ConcurrentHashMap<>();
    private final List<Queue> queues = new LinkedList<>();

    private final Flame flame;

    public QueueHandler(Flame flame) {
        this.flame = flame;

        flame.getServer().getPluginManager().registerEvents(new QueuePlayerListener(this), flame);

        new QueueTask(this).runTaskTimerAsynchronously(flame, 20L, 20L);
    }

    public QueuePlayer getPlayer(UUID uuid) {
        return queuePlayerMap.get(uuid);
    }

    public Queue getQueue(String name) {
        return queues.stream().filter(queue -> queue.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public boolean isCreated(UUID uuid) {
        return queuePlayerMap.containsKey(uuid);
    }

    public Queue getQueue(UUID uuid) {
        return getPlayer(uuid).getQueue();
    }

}
