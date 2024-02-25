package com.redmagic.undefinedrtp

import com.redmagic.undefinedapi.UndefinedAPI
import com.redmagic.undefinedapi.customEvents.PlayerHitByPlayerEvent
import com.redmagic.undefinedapi.event.event
import com.redmagic.undefinedapi.extension.string.toComponent
import com.redmagic.undefinedapi.scheduler.delay
import com.redmagic.undefinedapi.scoreboard.UndefinedScoreboard
import com.redmagic.undefinedrtp.admin.AdminManager
import com.redmagic.undefinedrtp.admin.command.AdminCommand
import com.redmagic.undefinedrtp.commands.RTPCommand
import com.redmagic.undefinedrtp.data.ConfigManager
import com.redmagic.undefinedrtp.gui.RTPGUI
import org.bukkit.Bukkit
import org.bukkit.entity.EnderCrystal
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerInteractAtEntityEvent
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin
import java.util.UUID

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


        event<PlayerHitByPlayerEvent> {
            Bukkit.broadcast(damager.name.toComponent())
        }

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
