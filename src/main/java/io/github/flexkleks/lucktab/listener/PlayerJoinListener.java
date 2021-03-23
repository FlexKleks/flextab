package io.github.flexkleks.lucktab.listener;

import io.github.flexkleks.lucktab.build.TabListBuilder;
import io.github.flexkleks.lucktab.cache.CacheUser;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler( priority = EventPriority.HIGH )
    public void onPlayerJoin( PlayerJoinEvent event ) {
        Player player = event.getPlayer();
        CacheUser user = CacheUser.getUser( player );

        user.getCacheMethods().setPrefix( player );

        TabListBuilder.setNameTag();
    }
}
