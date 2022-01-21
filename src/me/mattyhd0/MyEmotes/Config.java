package me.mattyhd0.MyEmotes;

import java.util.ArrayList;
import java.util.List;

public class Config {
    
    public static String getMessage(String message) {
        YMLFile messagesFile = new YMLFile("messages.yml");
        String msg = messagesFile.get().getString("messages." + message);
        if (msg != null) {
            msg = msg.replaceAll("\\{prefix}", messagesFile.get().getString("messages.prefix"));
            return Util.color(msg);
        }
        return org.bukkit.ChatColor.translateAlternateColorCodes('&', "&c[MyEmotes] Error: Message messages." + message + " does not exist in messages.yml");
    }

    public static List<String> getMessageList(String message) {
        YMLFile messagesFile = new YMLFile("messages.yml");
        List<String> msgList = messagesFile.get().getStringList("messages." + message);
        List<String> coloredList = new ArrayList<>();

        if (msgList.size() > 0) {

            for(String line: msgList){

                line = line.replaceAll("\\{prefix}", messagesFile.get().getString("messages.prefix"));
                coloredList.add(Util.color(line));

            }

            return coloredList;

        } else {

            List<String> error = new ArrayList<>();
            error.add(org.bukkit.ChatColor.translateAlternateColorCodes('&', "&c[MyEmotes] Error: Message messages." + message + " does not exist in messages.yml"));
            return error;
        }

    }

}
