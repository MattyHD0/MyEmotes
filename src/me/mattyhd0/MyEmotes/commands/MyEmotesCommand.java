package me.mattyhd0.MyEmotes.commands;

import me.mattyhd0.MyEmotes.Config;
import me.mattyhd0.MyEmotes.emotes.Emote;
import me.mattyhd0.MyEmotes.emotes.EmoteLoader;
import me.mattyhd0.MyEmotes.Util;
import me.mattyhd0.MyEmotes.YMLFile;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyEmotesCommand implements CommandExecutor, TabCompleter {

    String[] completions = {"help", "reload", "list", "create", "delete"};

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] arg) {

        if(sender.hasPermission("myemotes.command")){

            if(arg.length >= 1){

                if (arg[0].equalsIgnoreCase("help")){

                    help(sender, arg);

                } else if(arg[0].equalsIgnoreCase("reload")){

                    reload(sender, arg);

                } else if(arg[0].equalsIgnoreCase("list")) {

                    list(sender, arg);

                } else if(arg[0].equalsIgnoreCase("create")) {

                    create(sender, arg);

                } else if(arg[0].equalsIgnoreCase("delete")) {

                    delete(sender, arg);

                } else {

                    unknownSubcommand(sender);

                }

            } else {

                help(sender, arg);

            }

        } else {
            noPermission(sender);
        }

        return true;

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] strings) {

        if(strings.length == 1){
            return Arrays.asList(completions);
        } else if(strings.length == 2 && strings[0].equals("delete")){

            List<String> emotes = new ArrayList<>();

            for(Emote emote: EmoteLoader.getLoadedEmotes()){
                emotes.add(emote.getId());
            }

            return emotes;

        } else if (strings.length >= 2 && strings[0].equals("create")){

            List<String> comp = new ArrayList<>();

            if(strings.length == 2){
                comp.add("<id>");
            } else if (strings.length == 3){
                comp.add("<key>");
            } else if (strings.length == 4){
                comp.add("<emote>");
            } else if (strings.length == 5){
                comp.add("<permission>");
            }

            return comp;

        }

        return new ArrayList<>();
    }

    public void help(CommandSender sender, String[] arg){

        if(sender.hasPermission("myemotes.help")) {

            for (String msg : Config.getMessageList("commands.myemotes.help"))
                sender.sendMessage(msg);

        } else {
            noPermission(sender);
        }

    }

    public void list(CommandSender sender, String[] arg){

        if(sender.hasPermission("myemotes.list")) {

            if(arg.length == 2) {

                YMLFile config = new YMLFile("config.yml");

                if (arg.length > 1) {

                    try {

                        List<Emote> loadedEmotes = EmoteLoader.getLoadedEmotes();
                        int page = Integer.parseInt(arg[1]);
                        int emotesPerPage = 5;
                        int maxPage = (int) loadedEmotes.size() / emotesPerPage;

                        String header = Config.getMessage("commands.myemotes.list.header");
                        String id = Config.getMessage("commands.myemotes.list.id");
                        String key = Config.getMessage("commands.myemotes.list.key");
                        String emoteStr = Config.getMessage("commands.myemotes.list.emote");
                        String permission = Config.getMessage("commands.myemotes.list.permission");
                        String footer = Config.getMessage("commands.myemotes.list.footer");
                        String pageNotExist = Config.getMessage("commands.myemotes.list.page-not-found");

                        if (maxPage >= page && 0 < page) {

                            String header1 = header;
                            String footer1 = footer;

                            header1 = header1.replaceAll("\\{current}", page + "");
                            header1 = header1.replaceAll("\\{max}", maxPage + "");

                            footer1 = footer1.replaceAll("\\{current}", page + "");
                            footer1 = footer1.replaceAll("\\{max}", maxPage + "");

                            sender.sendMessage(Util.color(header1));

                            for (int i = page * emotesPerPage; (loadedEmotes.size() > i && i < (page * emotesPerPage) + emotesPerPage); i++) {

                                Emote emote = loadedEmotes.get(i);

                                sender.sendMessage(" ");

                                sender.sendMessage(id.replaceAll("\\{id}", emote.getId()));
                                sender.sendMessage(key.replaceAll("\\{key}", emote.getKey()));
                                sender.sendMessage(emoteStr.replaceAll("\\{emote}", emote.getEmote()));
                                String perm = emote.getPermission();
                                if (perm == null) perm = "NONE";
                                sender.sendMessage(permission.replaceAll("\\{permission}", perm));

                            }

                            sender.sendMessage(" ");

                            sender.sendMessage(Util.color(footer1));


                        } else {
                            sender.sendMessage(Util.color(pageNotExist.replaceAll("\\{page}", page + "")));
                        }

                    } catch (NumberFormatException exception) {
                        sender.sendMessage(
                                Config.getMessage("commands.myemotes.list.invalid-number")
                        );
                    }

                } else {
                    sender.sendMessage(
                            Config.getMessage("commands.myemotes.list.invalid-number")
                    );
                }
            } else {
                sender.sendMessage(
                        Config.getMessage("other.correct-usage")
                                .replaceAll("\\{command}", "/myemotes list <page>")
                );
            }
        } else {
            noPermission(sender);
        }

    }

    public void reload(CommandSender sender, String[] arg){

        if(sender.hasPermission("myemotes.reload")) {

            if(arg.length == 1) {

                YMLFile config = new YMLFile("config.yml");
                YMLFile emotes = new YMLFile("emotes.yml");
                config.load();
                emotes.load();
                EmoteLoader.loadAllEmotes();

                sender.sendMessage(
                        Config.getMessage("commands.myemotes.reload.reloaded")
                );

            } else {

                sender.sendMessage(
                        Config.getMessage("other.correct-usage")
                                .replaceAll("\\{command}", "/myemotes reload")
                );

            }
        } else {
            noPermission(sender);
        }

    }

    public void noPermission(CommandSender sender){
        sender.sendMessage(
                Config.getMessage("other.no-permission")
        );
    }

    public void badExecutor(CommandSender sender){
        sender.sendMessage(
                Config.getMessage("other.bad-executor")
        );
    }

    public void unknownSubcommand(CommandSender sender){
        sender.sendMessage(
                Config.getMessage("commands.myemotes.invalid-subcommand")
        );
    }

    public void create(CommandSender sender, String[] arg){

        if(sender.hasPermission("myemotes.create")){

            if(arg.length == 5){

                String id = arg[1];
                String key = arg[2];
                String emote = arg[3];
                String permission = arg[4];

                if(!id.contains(".")) {

                    if (EmoteLoader.getEmoteById(arg[1]) == null) {

                        YMLFile emotesFile = new YMLFile("emotes.yml");
                        FileConfiguration emotes = emotesFile.get();

                        emotes.set(id + ".key", key);
                        emotes.set(id + ".emote", emote);
                        emotes.set(id + ".permission", permission);

                        emotesFile.save();
                        EmoteLoader.loadAllEmotes();

                        sender.sendMessage(
                                Config.getMessage("commands.myemotes.create.successfully-created")
                                        .replaceAll("\\{id}", id)
                                        .replaceAll("\\{key}", key)
                                        .replaceAll("\\{emote}", Util.color(emote))
                                        .replaceAll("\\{permission}", permission)

                        );

                    } else {

                        sender.sendMessage(
                                Config.getMessage("commands.myemotes.create.already-exist")
                                        .replaceAll("\\{id}", id)
                        );

                    }
                } else {

                    sender.sendMessage(
                            Config.getMessage("commands.myemotes.create.id-cannot-contain-period")
                    );

                }

            } else {

                sender.sendMessage(
                        Config.getMessage("other.correct-usage")
                                .replaceAll("\\{command}", "/myemotes create <id> <key> <emote> <permission>")
                );

            }

        } else {

            noPermission(sender);

        }

    }

    public void delete(CommandSender sender, String[] arg){

        if(sender.hasPermission("myemotes.delete")){

            if(arg.length == 2){

                String id = arg[1];

                if(EmoteLoader.getEmoteById(id) != null){

                    YMLFile emotesFile = new YMLFile("emotes.yml");
                    FileConfiguration emotes = emotesFile.get();

                    emotes.set(id, null);

                    emotesFile.save();
                    EmoteLoader.loadAllEmotes();

                    sender.sendMessage(
                            Config.getMessage("commands.myemotes.delete.successfully-deleted")
                                    .replaceAll("\\{id}", id)

                    );

                } else {

                    sender.sendMessage(
                            Config.getMessage("commands.myemotes.delete.not-exist")
                                    .replaceAll("\\{id}", id)
                    );

                }

            } else {

                sender.sendMessage(
                        Config.getMessage("other.correct-usage")
                                .replaceAll("\\{command}", "/myemotes delete <id>")
                );

            }

        } else {

            noPermission(sender);

        }

    }

}
