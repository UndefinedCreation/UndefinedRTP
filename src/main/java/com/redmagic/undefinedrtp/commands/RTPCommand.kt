package com.redmagic.undefinedrtp.commands

import com.undefined.api.command.UndefinedCommand
import com.undefined.api.menu.MenuManager.openMenu
import com.redmagic.undefinedrtp.UndefinedRTP

class RTPCommand(val plugin: UndefinedRTP) {

    init {

        UndefinedCommand("rtp")
            .addExecutePlayer {
                openMenu(plugin.rtpgui!!)
                return@addExecutePlayer true
            }

    }
}