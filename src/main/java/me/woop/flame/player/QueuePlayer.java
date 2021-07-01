package me.woop.flame.player;

import lombok.Getter;
import lombok.Setter;
import me.woop.flame.Flame;
import me.woop.flame.queue.Queue;

import java.util.UUID;

@Getter
@Setter
public class QueuePlayer {

    private final UUID uuid;

    private int priority;
    private Queue queue;

    public QueuePlayer(UUID uuid, int priority, Queue queue) {
        this.uuid = uuid;
        this.priority = priority;
        this.queue = queue;

        Flame.getInstance().getQueueHandler().getQueuePlayerMap().put(uuid, this);
    }

}
