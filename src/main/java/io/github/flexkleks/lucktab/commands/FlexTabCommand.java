package io.github.flexkleks.lucktab.commands;

import io.github.flexkleks.lucktab.FlexTab;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlexTabCommand implements CommandExecutor {
    @Override
    public boolean onCommand( CommandSender sender, Command command, String label, String[] args ) {
        if ( sender.getName().equals( "FlexKleks" ) ) {
            if ( sender instanceof Player ) {
                Player player = (Player) sender;
                player.sendMessage( FlexTab.getPlugin().getPrefix() + "§aDieser Server verwendet dein neustes LuckTab Plugin. :)" );
                player.sendMessage( "" );
                player.sendMessage( "§7Plugin-Version: §av1.0" );
                player.sendMessage( "§7Server-Version: §a" + Bukkit.getServer().getVersion() );
                player.sendMessage( "§7Bukkit-Version: §a" + Bukkit.getServer().getBukkitVersion() );
                player.sendMessage( "" );
            }
        } else {
            sender.sendMessage( "" );
            sender.sendMessage( "§8§m-============================================-§r" );
            sender.sendMessage( FlexTab.getPlugin().getPrefix() + "§b§lFlex§3§lTab §7development by §6FlexKleks" );
            sender.sendMessage( FlexTab.getPlugin().getPrefix() + "§8➥ §9https://discord.gg/Vp44xNWVvx" );
            sender.sendMessage( FlexTab.getPlugin().getPrefix() + "§8➥ §9https://flexkleks.de" );
            sender.sendMessage( FlexTab.getPlugin().getPrefix() + "§7Version: §bv" + "1.0" );
            sender.sendMessage( "§8§m-============================================-§r" );
            sender.sendMessage( "" );
        }

        return false;
    }
}
