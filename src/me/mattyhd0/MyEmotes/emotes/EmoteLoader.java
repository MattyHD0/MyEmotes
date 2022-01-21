package me.mattyhd0.MyEmotes.emotes;

import me.mattyhd0.MyEmotes.Util;
import me.mattyhd0.MyEmotes.YMLFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EmoteLoader {

    private static HashMap<String, Emote> loadedEmotes = new HashMap<>();


    public static void loadAllEmotes() {

        loadedEmotes.clear();

        YMLFile emotesFile = new YMLFile("emotes.yml");

        for (String key: emotesFile.get().getKeys(false)){

            String identifier = emotesFile.get().getString(key+".key");
            String result = emotesFile.get().getString(key+".emote");
            String permission = emotesFile.get().getString(key+".permission");

            if((permission == null || permission.length() == 0 || permission.equalsIgnoreCase("NONE"))) permission = null;

            Emote emote = new Emote(key, identifier, Util.color(result), permission);

            loadedEmotes.put(key, emote);
        }


    }

    public static List<Emote> getLoadedEmotes() {
        return new ArrayList<>(loadedEmotes.values());
    }

    public static Emote getEmoteById(String id){
        return loadedEmotes.get(id);
    }
}
