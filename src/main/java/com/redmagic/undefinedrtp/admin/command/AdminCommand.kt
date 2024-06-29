package com.redmagic.undefinedrtp.admin.command

import com.undefined.api.command.UndefinedCommand
import com.undefined.api.menu.MenuManager.openMenu
import com.redmagic.undefinedrtp.UndefinedRTP

class AdminCommand(val plugin: UndefinedRTP) {
    init {

        UndefinedCommand("adminrtp")
            .addExecutePlayer {
                openMenu(plugin.adminManager.adminGUI)

                return@addExecutePlayer true
            }

    }

}