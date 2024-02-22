package com.redmagic.undefinedrtp

import com.redmagic.undefinedapi.UndefinedAPI
import com.redmagic.undefinedrtp.admin.AdminManager
import com.redmagic.undefinedrtp.admin.command.AdminCommand
import com.redmagic.undefinedrtp.commands.RTPCommand
import com.redmagic.undefinedrtp.data.ConfigManager
import com.redmagic.undefinedrtp.gui.RTPGUI
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class UndefinedRTP : JavaPlugin() {

    lateinit var configManager: ConfigManager
    lateinit var adminManager: AdminManager
    lateinit var rtpManager: RTPManager

    var rtpgui: RTPGUI? = null

    override fun onLoad() {
        Bukkit.getLogger().info("Loading UndefinedRTP")
    }

    override fun onEnable() {
        // Plugin startup logic
        UndefinedAPI(this)

        rtpgui = RTPGUI(this)

        configManager = ConfigManager(this)

        adminManager = AdminManager(this)

        rtpManager = RTPManager(this)

        registerCommands()

    }

    override fun onDisable() {
        // Plugin shutdown logic
        configManager.saveData()
    }

    private fun registerCommands(){
        AdminCommand(this)
        RTPCommand(this)
    }
}
