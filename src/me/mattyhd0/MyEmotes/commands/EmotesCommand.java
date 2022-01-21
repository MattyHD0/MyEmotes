package me.mattyhd0.MyEmotes.commands;

import me.mattyhd0.MyEmotes.Config;
import me.mattyhd0.MyEmotes.emotes.Emote;
import me.mattyhd0.MyEmotes.emotes.EmoteLoader;
import me.mattyhd0.MyEmotes.Util;
import me.mattyhd0.MyEmotes.YMLFile;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class EmotesCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] arg) {

        if(sender.hasPermission("myemotes.emotes")) {

            list(sender);

        }

        return true;

    }

    public void list(CommandSender sender){

        YMLFile config = new YMLFile("config.yml");

        boolean showEmoteOnlyIfHasPerm = config.get().getBoolean("config.emotes-list.auto.show-emote-only-if-have-permission");
        boolean useCustomList = config.get().getBoolean("config.emotes-list.custom-list.use-custom-list");

        List<String> customList = Config.getMessageList("commands.emotes.custom-list");

        boolean useHeader = config.get().getBoolean("config.emotes-list.auto.use-header");
        boolean useFooter = config.get().getBoolean("config.emotes-list.auto.use-footer");


        String listHeader = Config.getMessage("commands.emotes.auto-list.header");
        String listMiddle = Config.getMessage("commands.emotes.auto-list.emote");
        String listFooter = Config.getMessage("commands.emotes.auto-list.footer");

        if(useCustomList){

            for(String string: customList){
                sender.sendMessage(Util.color(string));
            }

        } else {

            if(useHeader) sender.sendMessage(Util.color(listHeader));

            for (Emote emote: EmoteLoader.getLoadedEmotes()){

                if(showEmoteOnlyIfHasPerm){

                    String listMiddleEmote = listMiddle;
                    listMiddleEmote = listMiddleEmote.replaceAll("\\{key}",emote.getKey());
                    listMiddleEmote = listMiddleEmote.replaceAll("\\{emote}", emote.getEmote());

                    if(emote.hasPermission(sender)) sender.sendMessage(Util.color(listMiddleEmote));

                } else {

                    String listMiddleEmote = listMiddle;
                    listMiddleEmote = listMiddleEmote.replaceAll("\\{key}",emote.getKey());
                    listMiddleEmote = listMiddleEmote.replaceAll("\\{emote}", emote.getEmote());

                    sender.sendMessage(Util.color(listMiddleEmote));

                }

            }

            if(useFooter) sender.sendMessage(Util.color(listFooter));

        }

    }

}
