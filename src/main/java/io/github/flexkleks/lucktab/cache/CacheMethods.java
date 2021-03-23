package io.github.flexkleks.lucktab.cache;

import io.github.flexkleks.lucktab.FlexTab;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class CacheMethods {

    private final ConfigurationSection section;

    public CacheMethods() {
        this.section = FlexTab.getPlugin().getFileBuilder().getConfigurationSection();
    }

    public void initTeams() {
        this.section.getConfigurationSection( "settings.tablist" )
                .getKeys( false )
                .stream()
                .map( mapName -> this.section.getStringList( "settings.tablist." + mapName ) )
                .forEach( mapNameValues -> {
                    new RankCache(
                            mapNameValues.get( 3 ),
                            this.sendColoredMessage( mapNameValues.get( 0 ) ),
                            this.sendColoredMessage( mapNameValues.get( 1 ) ),
                            this.sendColoredMessage( mapNameValues.get( 2 ) ),
                            mapNameValues.get( 4 )
                    );
                } );
    }

    public synchronized void setPrefix( Player player ) {
        CacheUser user = CacheUser.getUser( player );

        User luckUser = LuckPermsProvider.get().getUserManager().getUser( player.getName() );
        if ( luckUser == null ) {
            FlexTab.getPlugin().getLogger().log( Level.WARNING, "luckUser cannot be null" );
            return;
        }

        this.section.getConfigurationSection( "settings.tablist" ).getKeys( false ).stream().map( mapName
                -> this.section.getStringList( "settings.tablist." + mapName ) ).forEach( mapNameValues -> {

            String rank = mapNameValues.get( 3 );

            if ( luckUser.getPrimaryGroup().equalsIgnoreCase( rank ) ) {
                user.setPrefix( RankCache.getRank( rank ).getPrefix() );
                user.setNameTag( RankCache.getRank( rank ).getNameTag() );
                user.setSuffix( RankCache.getRank( rank ).getSuffix() );
                user.setTagId( RankCache.getRank( rank ).getTagId() );
            }
        } );
    }

    private String sendColoredMessage( String message ) {
        return ChatColor.translateAlternateColorCodes( '&', message );
    }
}
