package com.undefined.rtp.data

import com.undefined.api.builders.ItemBuilder
import com.undefined.api.extension.string.toSmallText
import com.undefined.rtp.UndefinedRTP
import com.undefined.api.extension.string.translateColor
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.Material

class ConfigManager(val plugin: UndefinedRTP) {

    private val miniMessage = MiniMessage.miniMessage()

    val overWorld: RTPWorld = RTPWorld(
        plugin.config.getString("overworld.name")!!,
        plugin.config.getDouble("overworld.range"),
    )

    val netherWorld: RTPWorld = RTPWorld(
        plugin.config.getString("nether.name")!!,
        plugin.config.getDouble("nether.range"),
    )

    val endWorld: RTPWorld = RTPWorld(
        plugin.config.getString("end.name")!!,
        plugin.config.getDouble("end.range"),
    )

    var allowedBlocks: MutableList<Material> = mutableListOf()

    var cooldown: Int = plugin.config.getInt("cooldown")
    var countdown: Int = plugin.config.getInt("countdown")

    var autoFillWorldBolder: Boolean = plugin.config.getBoolean("auto-fill-worldborder")

    var maxAttemps: Int = plugin.config.getInt("max-attemps")

    var rtpWorld = Bukkit.getWorld(plugin.config.getString("queue-world")!!)!!

    init {
        plugin.config.getStringList("allowed-blocks").forEach {
            allowedBlocks.add(Material.valueOf(it))
        }
    }

    fun saveData(){
        overWorld.save("overworld")
        netherWorld.save("nether")
        endWorld.save("end")
        plugin.config.set("cooldown", cooldown)
        plugin.config.set("countdown", countdown)
        plugin.config.set("auto-fill-world-bolder", autoFillWorldBolder)
        plugin.config.set("max-attempts", maxAttemps)
        plugin.config.set("queue-world", rtpWorld.name)
        allowedBlocks.save()
        plugin.saveConfig()
    }

    private fun RTPWorld.save(string: String){
        plugin.config.set("$string.name", worldName)
        plugin.config.set("$string.range", range)
    }

    private fun MutableList<Material>.save() = plugin.config.set("allow-blocks", map(Material::toString))

    fun getItemStackAllowedBlocks() = plugin.configManager.allowedBlocks.map {
        ItemBuilder(it)
                .setName("<reset><#4fdb72>${it.name.replace("_", " ").toSmallText()}".translateColor())
                .addLine(" ")
                .addLine("<gray>ᴄʟɪᴄᴋ ᴛᴏ ʀᴇᴍᴏᴠᴇ ꜰʀᴏᴍ ᴀʟʟᴏᴡᴇᴅ ʙʟᴏᴄᴋѕ".translateColor()).build()
    }

    fun getBlockedBlockItems() = Material.entries
            .filter { it.isBlock && it.isSolid && it !in allowedBlocks }
            .map { ItemBuilder(it)
                    .setName("<reset><#4be394>${it.name.replace("_", " ").toSmallText()}".translateColor())
                    .addLine(" ")
                    .addLine("<reset><gray>ᴄʟɪᴄᴋ ᴛᴏ ᴀᴅᴅ ${it.name.replace("_", " ").toSmallText()} ᴛᴏ ᴀʟʟᴏᴡᴇᴅ ʙʟᴏᴄᴋѕ".translateColor())
                    .build()
            }
}