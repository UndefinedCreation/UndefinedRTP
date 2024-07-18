package com.undefined.rtp

import com.undefined.api.UndefinedAPI
import com.undefined.api.customEvents.PlayerHitByPlayerEvent
import com.undefined.api.event.event
import com.undefined.api.extension.string.toComponent
import com.undefined.rtp.admin.AdminManager
import com.undefined.rtp.admin.command.AdminCommand
import com.undefined.rtp.commands.RTPCommand
import com.undefined.rtp.data.ConfigManager
import com.undefined.rtp.gui.RTPGUI
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
