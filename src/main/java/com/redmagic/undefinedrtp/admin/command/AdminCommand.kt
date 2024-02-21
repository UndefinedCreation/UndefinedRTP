package com.redmagic.undefinedrtp.admin.command

import com.redmagic.undefinedapi.builders.ItemBuilder
import com.redmagic.undefinedapi.command.CommandTabUtil
import com.redmagic.undefinedapi.command.CommandType
import com.redmagic.undefinedapi.command.UndefinedCommand
import com.redmagic.undefinedapi.extension.string.toSmallText
import com.redmagic.undefinedapi.menu.MenuManager.openMenu
import com.redmagic.undefinedrtp.UndefinedRTP
import com.redmagic.undefinedrtp.admin.gui.AdminAllowedBlocks
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

class AdminCommand(val plugin: UndefinedRTP): UndefinedCommand(
    "adminrtp",
    aliases = listOf("ar", "artp"),
    permission = "undefined.rtp.command",
    commandType = CommandType.PLAYER,
    description = "Command that managers undefined rtp"
) {

    override fun execute(sender: CommandSender, args: Array<out String>) {
        val player: Player = sender as Player

        player.openMenu(plugin.adminManager!!.adminGUI)
    }

    override fun tabComplete(sender: CommandSender, args: Array<out String>): CommandTabUtil {
        return CommandTabUtil(listOf(), 0)

    }
}