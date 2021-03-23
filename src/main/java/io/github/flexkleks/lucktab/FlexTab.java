package io.github.flexkleks.lucktab;

import io.github.flexkleks.lucktab.cache.CacheMethods;
import io.github.flexkleks.lucktab.commands.FlexTabCommand;
import io.github.flexkleks.lucktab.config.FileBuilder;
import io.github.flexkleks.lucktab.listener.AsyncPlayerChatListener;
import io.github.flexkleks.lucktab.listener.PlayerJoinListener;
import io.github.flexkleks.lucktab.metrics.MetricsLite;
import lombok.Getter;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Getter
public class FlexTab extends JavaPlugin {

    @Getter
    private static FlexTab plugin;

    private String prefix = "§8▌ §b§lF§3§lT §8» §7";

    private FileBuilder fileBuilder;

    @Override
    public void onLoad() {
        plugin = this;
        this.init();
    }

    @Override
    public void onEnable() {
        System.out.println( String.format( "%sTry to start the §a%s §7plugin...", this.getPrefix(), "FlexTab" ) );
        new MetricsLite( this, 10729 );
        try {
            this.loadLuckPerms();
            this.register();

            new CacheMethods().initTeams();

            this.infoLog( "&8&m-==========================================================-&r" );
            this.infoLog( this.getPrefix() + "&b&lFlex&3&lTab &7development by &6FlexKleks" );
            this.infoLog( this.getPrefix() + "&8➥ &9https://discord.me/FlexKleks" );
            this.infoLog( this.getPrefix() + "&8➥ &9https://flexkleks.de" );
            this.infoLog( this.getPrefix() + "&7" );
            this.infoLog( this.getPrefix() + "    &3&l___" );
            this.infoLog( this.getPrefix() + "&b&l|    &3&l|" );
            this.infoLog( this.getPrefix() + "&b&l|___ &3&l|" );
            this.infoLog( this.getPrefix() + "&7" );
            ;
            System.out.println( String.format( "%s&a&o%s successful activated.", this.getPrefix(), "FlexTab" ) );
            this.infoLog( "&9Version: &bv" + this.getDescription().getVersion() );
            this.infoLog( "&8&m-==========================================================-&r" );
        } catch ( Exception ex ) {
            System.out.println( String.format( "%s§cAn error occurred when starting the §4%s §cplugin!", this.getPrefix(), "FlexTab" ) );
            ex.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        System.out.println( String.format( "%s§cThe §4%s §cplugin has been deactivated.", this.getPrefix(), "FlexTab" ) );
    }

    private void register() {
        //commands
        this.getCommand( "flextab" ).setExecutor( new FlexTabCommand() );

        //listener
        Listener[] listeners = new Listener[]{
                new AsyncPlayerChatListener(), new PlayerJoinListener()
        };

        Arrays.stream( listeners ).forEach( listener -> Bukkit.getPluginManager().registerEvents( listener, this ) );
    }

    private void init() {
        this.fileBuilder = new FileBuilder( "settings.yml" );
        this.saveResource( "settings.yml", false );
    }

    private void loadLuckPerms() {
        Plugin luckPerms = Bukkit.getPluginManager().getPlugin( "LuckPerms" );

        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration( LuckPerms.class );
        if ( provider != null ) {
            LuckPerms api = provider.getProvider();
            return;
        }

        this.infoLog( "§4LuckPerms could not be found. Please download LuckPerms." );
        this.infoLog( "§7➥ §9https://luckperms.net/" );
        this.infoLog( " " );
        this.infoLog( "§4§lThe plugin is now deactivated." );
        Bukkit.getPluginManager().disablePlugin( this );
    }

    private void infoLog( String message ) {
        Bukkit.getConsoleSender().sendMessage( ChatColor.translateAlternateColorCodes( '&', new String( message.getBytes(), StandardCharsets.UTF_8 ) ) );
    }
}
