package me.mattyhd0.MyEmotes.listeners;

import me.mattyhd0.MyEmotes.emotes.Emote;
import me.mattyhd0.MyEmotes.emotes.EmoteLoader;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandListener implements Listener {

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event){

        Player player = event.getPlayer();

        for(Emote emote: EmoteLoader.getLoadedEmotes()){

            if(emote.hasPermission(player)){

                String command = event.getMessage().replaceAll(
                        emote.getKey(),
                        ChatColor.translateAlternateColorCodes('&', emote.getEmote())
                );

                event.setMessage(command);

            }

        }

    }

}
