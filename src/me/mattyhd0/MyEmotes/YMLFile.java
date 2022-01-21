package me.mattyhd0.MyEmotes;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;


public class YMLFile {

    private String fileName;
    private boolean voidFile = false;
    private File file;
    private FileConfiguration fileConfiguration;

    public YMLFile(String fileName) {
        this.fileName = fileName;
        this.file = new File(MyEmotes.INSTANCE.getDataFolder(), this.fileName);

        this.load();
    }

    public YMLFile(File file) {
        this.fileName = file.getName();
        this.file = file;
        this.load();
    }

    public FileConfiguration get() {
        return this.fileConfiguration;
    }

    public File getFile() {
        return this.file;
    }

    public void load() {
        if(!this.file.exists()) {

            this.createFile();

        }
        this.loadFile();
    }

    public void setPath(String path, Object value) {
        this.get().set(path, value);
    }

    public void save() {
        try {
            this.get().save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createFile() {
        this.file.getParentFile().mkdirs();
        MyEmotes.INSTANCE.saveResource(this.fileName, false);

    }

    private void loadFile() {
        this.fileConfiguration = new YamlConfiguration();

        try {
            this.fileConfiguration.load(this.file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}
