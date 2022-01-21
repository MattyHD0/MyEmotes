package me.mattyhd0.MyEmotes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class UpdateChecker
{
    private String version;
    private String latestVersion;
    private String spigotUrl;
    
    public UpdateChecker() {
        this.version = MyEmotes.INSTANCE.getDescription().getVersion();
        this.latestVersion = null;
        this.spigotUrl = null;
        try {

            URL url = new URL("https://pastebin.com/raw/mwu3Kxw9");
            URLConnection urlConnection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            List<String> mattyhd0Plugins = new ArrayList<String>();
            String line;

            while ((line = bufferedReader.readLine()) != null) {

                mattyhd0Plugins.add(line);

            }

            for (final String plugin : mattyhd0Plugins) {

                String[] pluginInfo = plugin.split("\\|");

                if (pluginInfo[0].equals(MyEmotes.INSTANCE.getDescription().getName())) {
                    this.latestVersion = pluginInfo[1];
                    this.spigotUrl = pluginInfo[2];
                }

            }
        } catch (Exception ex) {}
    }
    
    public boolean isRunningLatestVersion() {
        return this.version.equals(this.latestVersion);
    }
    
    public String getVersion() {
        return this.version;
    }
    
    public String getLatestVersion() {
        return this.latestVersion;
    }
    
    public String getSpigotUrl() {
        return this.spigotUrl;
    }
    
    public boolean taskIsValid() {
        return this.version != null;
    }
}
