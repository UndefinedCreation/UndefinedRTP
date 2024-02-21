package com.redmagic.undefinedrtp.commands

import com.redmagic.undefinedapi.command.CommandTabUtil
import com.redmagic.undefinedapi.command.CommandType
import com.redmagic.undefinedapi.command.UndefinedCommand
import com.redmagic.undefinedapi.menu.MenuManager.openMenu
import com.redmagic.undefinedrtp.UndefinedRTP
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class RTPCommand(val plugin: UndefinedRTP): UndefinedCommand("rtp", permission = "undefined.rtp.command", commandType = CommandType.PLAYER) {
    override fun execute(sender: CommandSender, args: Array<out String>) {
        val player: Player = sender as Player
        player.openMenu(plugin.rtpgui!!)
    }

    override fun tabComplete(sender: CommandSender, args: Array<out String>): CommandTabUtil = CommandTabUtil(listOf(), 0)
}