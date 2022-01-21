package me.mattyhd0.MyEmotes.listeners;

import me.mattyhd0.MyEmotes.emotes.Emote;
import me.mattyhd0.MyEmotes.emotes.EmoteLoader;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerChat(AsyncPlayerChatEvent event){

        Player player = event.getPlayer();

        for(Emote emote: EmoteLoader.getLoadedEmotes()){

            if(emote.hasPermission(player)){

                String msg = event.getMessage().replaceAll(
                        emote.getKey(),
                        ChatColor.translateAlternateColorCodes('&', emote.getEmote())
                );

                event.setMessage(msg);

            }

        }

    }

}
