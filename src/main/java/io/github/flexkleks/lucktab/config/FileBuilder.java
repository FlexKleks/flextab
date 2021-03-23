package io.github.flexkleks.lucktab.config;

import io.github.flexkleks.lucktab.FlexTab;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author FlexKleks & NoJokeFNA
 * @version 1.0.0
 */
public class FileBuilder {

    private final File file;
    private final FileConfiguration fileConfiguration;

    public FileBuilder( String value ) {
        this.file = new File( FlexTab.getPlugin().getDataFolder(), value );
        this.fileConfiguration = YamlConfiguration.loadConfiguration( this.file );
    }

    public String getKey( String key ) {
        String configValue = this.fileConfiguration.getString( key );

        if ( configValue == null ) {
            return "§cError: §4Key " + key + " §edoes not exists.";
        }

        configValue = configValue
                .replace( "{NL}", "\n" )
                .replace( "%nl%", "\n" )
                .replace( "%newLine%", "\n" )
                .replace( "%nw%", "\n" );

        return ChatColor.translateAlternateColorCodes( '&', new String( configValue.getBytes(), StandardCharsets.UTF_8 ) );
    }

    public int getInt( String key ) {
        if ( key == null ) {
            return Integer.parseInt( "§cError: §4Key " + key + " §edoes not exists." );
        }

        return this.fileConfiguration.getInt( key );
    }

    public boolean getBoolean( String key ) {
        if ( key == null ) {
            return Boolean.parseBoolean( "§cError: §4Key " + key + " §edoes not exists." );
        }

        return this.fileConfiguration.getBoolean( key );
    }

    public ConfigurationSection getConfigurationSection() {
        return this.fileConfiguration;
    }

    public void setKey( String path, Object value ) {
        this.fileConfiguration.set( path, value );
        this.saveConfig();
    }

    public void loadConfig() {
        try {
            this.fileConfiguration.load( this.file );
        } catch ( IOException | InvalidConfigurationException ex ) {
            ex.printStackTrace();
        }
    }

    private void saveConfig() {
        try {
            this.fileConfiguration.save( this.file );
        } catch ( IOException ex ) {
            ex.printStackTrace();
        }
    }
}