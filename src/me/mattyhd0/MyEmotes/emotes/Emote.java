package me.mattyhd0.MyEmotes.emotes;


import org.bukkit.command.CommandSender;

import javax.annotation.Nullable;

public class Emote {

    private String id;
    private String permission;
    private String key;
    private String emote;

    public Emote(String id, String key, String emote, @Nullable String permission){

        this.id = id;
        this.permission = permission;
        this.key = key;
        this.emote = emote;


    }

    public boolean hasPermission(CommandSender sender){

        if(permission == null) return true;
        return sender.hasPermission(permission);

    }

    public String getId() {
        return id;
    }

    public String getPermission() {
        return permission;
    }

    public String getKey() {
        return key;
    }

    public String getEmote() {
        return emote;
    }
}
