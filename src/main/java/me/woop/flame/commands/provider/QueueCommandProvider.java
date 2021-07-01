package me.woop.flame.commands.provider;

import lombok.AllArgsConstructor;
import me.vaperion.blade.command.argument.BladeProvider;
import me.vaperion.blade.command.container.BladeParameter;
import me.vaperion.blade.command.context.BladeContext;
import me.vaperion.blade.command.exception.BladeExitMessage;
import me.woop.flame.Flame;
import me.woop.flame.queue.Queue;
import me.woop.flame.util.CC;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@AllArgsConstructor
public class QueueCommandProvider implements BladeProvider<Queue> {

    private final Flame flame;

    @Nullable
    @Override
    public Queue provide(@NotNull BladeContext bladeContext, @NotNull BladeParameter bladeParameter, @Nullable String s) throws BladeExitMessage {
        Queue queue = flame.getQueueHandler().getQueue(s);

        if (queue == null || s == null)
            throw new BladeExitMessage(CC.translate("&cThe queue &e" + s + "&c does not exist!"));

        return queue;
    }
}
