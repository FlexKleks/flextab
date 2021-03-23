package io.github.flexkleks.lucktab.listener;

import io.github.flexkleks.lucktab.FlexTab;
import io.github.flexkleks.lucktab.cache.CacheUser;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.context.ContextManager;
import net.luckperms.api.model.user.User;
import net.luckperms.api.query.QueryOptions;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Objects;

public class AsyncPlayerChatListener implements Listener {

    private final ConfigurationSection section;

    public AsyncPlayerChatListener() {
        this.section = FlexTab.getPlugin().getFileBuilder().getConfigurationSection();
    }

    @EventHandler
    public void onAsyncPlayerChat( AsyncPlayerChatEvent event ) {
        Player player = event.getPlayer();
        CacheUser user = CacheUser.getUser( player );

        String message = this.section.getString( "settings.chat.format" )
                .replace( "{PLAYER}", player.getName() )
                .replace( "{DISPLAYNAME}", this.sendColoredMessage( user.getNameTag() + player.getName() ) )
                .replace( "{PLAYERGROUP}", this.sendColoredMessage( this.getPlayerGroupPrefix( player ) ) )
                .replace( "{PREFIX}", this.sendColoredMessage( user.getPrefix() ) )
                .replace( "{SUFFIX}", this.sendColoredMessage( user.getSuffix() ) )
                .replace( "{MESSAGE}", player.hasPermission( "flextab.colored.message" )
                        ? this.sendColoredMessage( event.getMessage().replace( "%", "%%" ) )
                        : event.getMessage().replace( "%", "%%" ) );

        event.setFormat( message );
    }

    private String getPlayerGroupPrefix( Player player ) {
        LuckPerms luckPerms = LuckPermsProvider.get();
        ContextManager contextManager = luckPerms.getContextManager();

        User user = luckPerms.getUserManager().getUser( player.getUniqueId() );
        QueryOptions queryOptions = contextManager.getQueryOptions( Objects.requireNonNull( user ) ).orElse( contextManager.getStaticQueryOptions() );
        CachedMetaData metaData = user.getCachedData().getMetaData( queryOptions );

        if ( metaData.getPrefix() == null )
            return "Unknown meta data (prefix)";

        return this.sendColoredMessage( metaData.getPrefix() );
    }

    private String sendColoredMessage( String message ) {
        return ChatColor.translateAlternateColorCodes( '&', message );
    }
}
