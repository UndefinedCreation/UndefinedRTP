package com.redmagic.undefinedrtp

import com.redmagic.undefinedapi.UndefinedAPI
import com.redmagic.undefinedapi.builders.ItemBuilder
import com.redmagic.undefinedapi.event.event
import com.redmagic.undefinedapi.extension.string.asInventory
import com.redmagic.undefinedapi.extension.string.asItemStack
import com.redmagic.undefinedapi.extension.string.asString
import com.redmagic.undefinedrtp.admin.AdminManager
import com.redmagic.undefinedrtp.admin.command.AdminCommand
import com.redmagic.undefinedrtp.commands.RTPCommand
import com.redmagic.undefinedrtp.data.ConfigManager
import com.redmagic.undefinedrtp.gui.RTPGUI
import jdk.incubator.vector.VectorOperators.Test
import net.kyori.adventure.text.Component
import net.wesjd.anvilgui.AnvilGUI
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.Inventory
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

class UndefinedRTP : JavaPlugin() {

    var configManager: ConfigManager? = null
    var adminManager: AdminManager? = null
    var rtpManager: RTPManager? = null

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
        configManager?.saveData()
    }

    private fun registerCommands(){
        AdminCommand(this)
        RTPCommand(this)
    }
}
