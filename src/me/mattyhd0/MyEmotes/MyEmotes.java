package me.mattyhd0.MyEmotes;

import me.mattyhd0.MyEmotes.commands.EmotesCommand;
import me.mattyhd0.MyEmotes.commands.MyEmotesCommand;
import me.mattyhd0.MyEmotes.emotes.EmoteLoader;
import me.mattyhd0.MyEmotes.listeners.ChatListener;
import me.mattyhd0.MyEmotes.bStats.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class MyEmotes extends JavaPlugin {

    public static Plugin INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;
        Metrics metrics = new Metrics(this, 13171);
        EmoteLoader.loadAllEmotes();
        setupCommands();
        setupListeners();
        updateChecker();
    }

    @Override
    public void onDisable() {

    }

    public void setupListeners(){
        getServer().getPluginManager().registerEvents(new ChatListener(), this);
    }

    public void setupCommands(){
        getCommand("emotes").setExecutor(new EmotesCommand());
        getCommand("myemotes").setExecutor(new MyEmotesCommand());
        getCommand("myemotes").setTabCompleter(new MyEmotesCommand());
    }

    private void updateChecker() {
        YMLFile config = new YMLFile("config.yml");
        if (config.get().getBoolean("config.update-checker")) {
            UpdateChecker updateChecker = new UpdateChecker();
            ConsoleCommandSender console = Bukkit.getConsoleSender();
            if (updateChecker.taskIsValid()) {
                if (updateChecker.isRunningLatestVersion()) {
                    String message = Util.color("&8[&e&lMyEmotes&8] &7You are using the latest version of "+this.getName()+"!");
                    console.sendMessage(message);
                } else {
                    String message = Util.color("&8[&e&lMyEmotes&8] &7You are using version &e" + updateChecker.getVersion() + "&7 and the latest version is &e" + updateChecker.getLatestVersion());
                    String message2 = Util.color("&8[&e&lMyEmotes&8] &7You can download the latest version at: &e" + updateChecker.getSpigotUrl());
                    console.sendMessage(message);
                    console.sendMessage(message2);
                }
            } else {
                String message = Util.color("&8[&e&lMyEmotes&8] &7Could not verify if you are using the latest version of "+this.getName()+" :(");
                String message2 = Util.color("&8[&e&lMyEmotes&8] &7You can disable update checker in config.yml file");
                console.sendMessage(message);
                console.sendMessage(message2);
            }

        }

    }

}
