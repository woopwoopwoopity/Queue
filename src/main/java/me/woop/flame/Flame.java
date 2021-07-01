package me.woop.flame;

import io.github.leonardosnt.bungeechannelapi.BungeeChannelApi;
import lombok.Getter;
import me.vaperion.blade.Blade;
import me.vaperion.blade.command.bindings.impl.BukkitBindings;
import me.vaperion.blade.command.bindings.impl.DefaultBindings;
import me.vaperion.blade.command.container.impl.BukkitCommandContainer;
import me.woop.flame.commands.QueueCommand;
import me.woop.flame.commands.provider.QueueCommandProvider;
import me.woop.flame.queue.Queue;
import me.woop.flame.queue.QueueHandler;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class Flame extends JavaPlugin {

    @Getter private static Flame instance;

    private QueueHandler queueHandler;
    private BungeeChannelApi bungeeChannelApi;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        this.queueHandler = new QueueHandler(this);

        this.bungeeChannelApi = BungeeChannelApi.of(this);

        Blade.of().binding(new DefaultBindings()).binding(new BukkitBindings())
                .bind(Queue.class, new QueueCommandProvider(this))
                .overrideCommands(true).containerCreator(BukkitCommandContainer.CREATOR)
                .fallbackPrefix("flame").build().register(new QueueCommand(this));
    }

}
