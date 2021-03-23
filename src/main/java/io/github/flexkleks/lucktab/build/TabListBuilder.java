package io.github.flexkleks.lucktab.build;

import io.github.flexkleks.lucktab.cache.CacheUser;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import java.util.Arrays;

public class TabListBuilder {

    public static void setNameTag() {
        for ( Player currentPlayer : Bukkit.getOnlinePlayers() ) {
            CacheUser user = CacheUser.getUser( currentPlayer );

            if ( user.getPrefix() != null && user.getTagId() != null ) {
                for ( Player allPlayers : Bukkit.getOnlinePlayers() ) {
                    String playerName = currentPlayer.getName();

                    if ( playerName.length() >= 10 )
                        playerName = currentPlayer.getName().substring( 0, 10 );

                    Team team = allPlayers.getScoreboard().getTeam( user.getTagId() + playerName ) != null
                            ? allPlayers.getScoreboard().getTeam( user.getTagId() + playerName )
                            : allPlayers.getScoreboard().registerNewTeam( user.getTagId() + playerName );

                    if ( ! team.getEntries().contains( playerName ) )
                        team.addEntry( playerName );

                    String userPrefix = user.getPrefix();
                    if ( userPrefix.length() >= 16 )
                        userPrefix = userPrefix.substring( 0, 16 );

                    team.setPrefix( userPrefix );
                    team.setSuffix( user.getSuffix() );

                    String prefix = user.getNameTag() + playerName + " " + user.getSuffix();
                    currentPlayer.setPlayerListName( prefix );
                    currentPlayer.setDisplayName( prefix );
                }
            }
        }
    }
}