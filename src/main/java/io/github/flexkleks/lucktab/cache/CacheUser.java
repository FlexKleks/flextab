package io.github.flexkleks.lucktab.cache;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author FlexKleks & NoJokeFNA
 * @version 1.0.0
 */
@Getter
@Setter
public class CacheUser {

    private static final Map<UUID, CacheUser> CACHE_USER_MAP = new HashMap<>();

    // init
    private CacheMethods cacheMethods;

    // tabList
    private String prefix, nameTag, suffix;
    private String tagId;

    public CacheUser() {
        this.cacheMethods = new CacheMethods();
    }

    public static CacheUser getUser( Player player ) {
        return getUserByUuid( player.getUniqueId() );
    }

    public static CacheUser getUserByUuid( UUID playerUuid ) {
        if ( ! CACHE_USER_MAP.containsKey( playerUuid) )
            CACHE_USER_MAP.put( playerUuid, new CacheUser() );
        return CACHE_USER_MAP.get( playerUuid );
    }

    public static void deleteUser( Player player ) {
        CACHE_USER_MAP.remove( player.getUniqueId() );
    }
}
