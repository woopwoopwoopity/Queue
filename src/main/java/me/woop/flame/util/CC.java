package me.woop.flame.util;

import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class CC {

    public final String CHAT_BAR = translate("&7&m-----------------------------");
    public final String HALF_BAR = translate("&7&m------------");

    public final String translate(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public final List<String> translate(List<String> stringList) {
        return stringList.stream().map(CC::translate).collect(Collectors.toList());
    }

}
