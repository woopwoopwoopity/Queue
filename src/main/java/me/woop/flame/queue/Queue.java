package me.woop.flame.queue;

import lombok.Getter;
import lombok.Setter;
import me.woop.flame.Flame;
import me.woop.flame.player.QueuePlayer;
import me.woop.flame.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class Queue {

    private final List<QueuePlayer> queuePlayerList = new LinkedList<>();
    private final String name;
    private final String server;

    private boolean paused;

    public Queue(String name, String server) {
        this.name = name;
        this.server = server;

        Flame.getInstance().getQueueHandler().getQueues().add(this);
    }

    public void move() {
        if (paused) return;
        if (queuePlayerList.isEmpty()) return;

        QueuePlayer queuePlayer = queuePlayerList.get(0);
        Player player = Bukkit.getPlayer(queuePlayer.getUuid());

        queuePlayerList.remove(queuePlayer);
        Flame.getInstance().getBungeeChannelApi().connect(player, server);

        player.sendMessage(CC.translate("&aSending you to " + server + "...."));
    }

    public void remove(QueuePlayer queuePlayer) {
        queuePlayerList.remove(queuePlayer);
        queuePlayer.setQueue(null);
    }

    public void add(QueuePlayer queuePlayer) {
        queuePlayerList.add(queuePlayer);

        queuePlayerList.sort(new Comparator<QueuePlayer>() {
            @Override
            public int compare(QueuePlayer o1, QueuePlayer o2) {
                return o1.getPriority() - o2.getPriority();
            }
        });

        queuePlayer.setQueue(this);
    }

    public void delete() {
        queuePlayerList.forEach(queuePlayer ->
            queuePlayer.setQueue(null));
        queuePlayerList.clear();
        Flame.getInstance().getQueueHandler().getQueues().remove(this);
    }

    public int getPosition(QueuePlayer queuePlayer) {
        return queuePlayerList.indexOf(queuePlayer);
    }

}
