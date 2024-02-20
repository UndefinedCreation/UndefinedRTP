package com.redmagic.undefinedrtp

import com.redmagic.undefinedapi.UndefinedAPI
import com.redmagic.undefinedapi.event.event
import com.redmagic.undefinedrtp.admin.AdminManager
import com.redmagic.undefinedrtp.admin.command.AdminCommand
import com.redmagic.undefinedrtp.data.ConfigManager
import net.wesjd.anvilgui.AnvilGUI
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.plugin.java.JavaPlugin

class UndefinedRTP : JavaPlugin() {

    var configManager: ConfigManager? = null
    var adminManager: AdminManager? = null

    override fun onEnable() {
        // Plugin startup logic
        UndefinedAPI(this)

        configManager = ConfigManager(this)

        adminManager = AdminManager(this)

        registerCommands()
    }

    override fun onDisable() {
        // Plugin shutdown logic

        configManager?.saveData()


    }

    private fun registerCommands(){
        AdminCommand(this)
    }
}
