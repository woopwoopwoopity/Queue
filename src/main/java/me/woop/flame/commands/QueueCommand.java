package me.woop.flame.commands;

import lombok.AllArgsConstructor;
import me.vaperion.blade.command.annotation.Command;
import me.vaperion.blade.command.annotation.Name;
import me.vaperion.blade.command.annotation.Permission;
import me.vaperion.blade.command.annotation.Sender;
import me.woop.flame.Flame;
import me.woop.flame.player.QueuePlayer;
import me.woop.flame.queue.Queue;
import me.woop.flame.util.CC;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
public class QueueCommand {

    private final Flame flame;

    private final List<String> helpMessageAdmin = Arrays.asList(
            CC.HALF_BAR + CC.translate("&a&lQueue Help Commands") + CC.HALF_BAR,
            "&a/queue join <queue> &7- Joins a queue",
            "&a/queue create <queue> <server> &7- Creates a queue server",
            "&a/queue delete <queue> &7- Deletes a queue",
            "&a/queue leave &7- Leaves a queue",
            "&a/queue toggle <queue> &7- Toggles a queue",
            CC.HALF_BAR + CC.translate("&a&lQueue Help Commands") + CC.HALF_BAR
            );

    private final List<String> helpMessage = Arrays.asList(
            CC.HALF_BAR + CC.translate("&a&lQueue Help Commands") + CC.HALF_BAR,
            "&a/queue join <queue> &7- Joins a queue",
            "&a/queue leave &7- Leaves a queue",
            CC.HALF_BAR + CC.translate("&a&lQueue Help Commands") + CC.HALF_BAR
    );

    @Command(value = "queue", description = "Queue Command", async = true, quoted = false)
    public void queueCommand(@Sender Player player) {
        if (!player.hasPermission("queue.admin")) {
            helpMessage.forEach(message -> player.sendMessage(CC.translate(message)));
            return;
        }

        helpMessageAdmin.forEach(message -> player.sendMessage(CC.translate(message)));
    }

    @Command(value = "queue join", description = "Queue Join Command", async = true, quoted = false)
    public void queueJoinCommand(@Sender Player player, @Name("queue") Queue queue) {
        QueuePlayer queuePlayer = flame.getQueueHandler().getPlayer(player.getUniqueId());

        if (queuePlayer == null) {
            player.sendMessage(CC.translate("&cYou are not setup to leave or join a queue!"));
            return;
        }

        if (queuePlayer.getQueue() != null) {
            player.sendMessage(CC.translate("&cYou are already inside of a queue!"));
            return;
        }

        queue.add(queuePlayer);
        player.sendMessage(CC.translate("&aYou have joined the queue " + queue.getName()));
    }

    @Command(value = "queue leave", description = "Queue Leave Command", async = true, quoted = false)
    public void queueLeaveCommand(@Sender Player player) {
        QueuePlayer queuePlayer = flame.getQueueHandler().getPlayer(player.getUniqueId());

        if (queuePlayer == null) {
            player.sendMessage(CC.translate("&cYou are not setup to leave or join a queue!"));
            return;
        }

        Queue queue = queuePlayer.getQueue();

        if (queue == null) {
            player.sendMessage(CC.translate("&cYou are not inside of a queue!"));
            return;
        }

        queue.remove(queuePlayer);
        player.sendMessage(CC.translate("&aYou have left the queue " + queue.getName()));
    }

    @Command(value = "queue list", description = "Queue List Command", async = true, quoted = false)
    public void queueListCommand(@Sender Player player) {
        List<Queue> queues = flame.getQueueHandler().getQueues();

        if (queues.isEmpty()) {
            player.sendMessage(CC.translate("&cThere are no available queues!"));
            return;
        }

        player.sendMessage(CC.HALF_BAR + CC.translate("&a&lRegistered Queues") + CC.HALF_BAR);
                queues.forEach(queue -> {
            player.sendMessage(CC.translate("&7- &a" + queue.getName() + "&7(" + queue.getServer() + ")"));
        });
        player.sendMessage(CC.HALF_BAR + CC.translate("&a&lRegistered Queues") + CC.HALF_BAR);
    }

    @Command(value = "queue create", description = "Queue Create Command", async = true, quoted = false)
    @Permission("queue.admin")
    public void queueCreateCommand(@Sender Player player, @Name("name") String name, @Name("server") String server) {

        if (flame.getQueueHandler().getQueue(name) != null) {
            player.sendMessage(CC.translate("&cA queue with that name already exists!"));
            return;
        }

        new Queue(name, server);
        player.sendMessage(CC.translate("&aCreated a queue with the name " + name));
    }

    @Command(value = "queue delete", description = "Queue Delete Command", async = true, quoted = false)
    @Permission("queue.admin")
    public void queueDeleteCommand(@Sender Player player, @Name("queue") Queue queue) {
        queue.delete();
        player.sendMessage(CC.translate("&aDeleted the queue " + queue.getName()));
    }

    @Command(value = "queue toggle", description = "Queue Toggle Command", async = true, quoted = false)
    @Permission("queue.admin")
    public void queueToggleCommand(@Sender Player player, @Name("queue") Queue queue) {
        if (queue.isPaused())
            queue.setPaused(false);
        else queue.setPaused(true);

        String status = queue.isPaused() ? "paused" : "un-paused";
        player.sendMessage(CC.translate("&aThe queue " + queue.getName() + " is now " + status + "!"));
    }

}
