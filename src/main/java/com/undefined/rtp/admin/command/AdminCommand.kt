package com.undefined.rtp.admin.command

import com.undefined.api.command.UndefinedCommand
import com.undefined.api.menu.MenuManager.openMenu
import com.undefined.rtp.UndefinedRTP

class AdminCommand(val plugin: UndefinedRTP) {
    init {

        UndefinedCommand("adminrtp", permission = "undefined.rtp.admin.command")
            .addExecutePlayer {
                openMenu(plugin.adminManager.adminGUI)

                return@addExecutePlayer true
            }

    }

}