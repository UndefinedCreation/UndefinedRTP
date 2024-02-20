package com.redmagic.undefinedrtp.admin

import com.redmagic.undefinedrtp.UndefinedRTP
import com.redmagic.undefinedrtp.admin.gui.AdminGUI
import org.bukkit.plugin.java.JavaPlugin

class AdminManager(plugin: UndefinedRTP){
    val adminGUI: AdminGUI = AdminGUI(plugin)
}