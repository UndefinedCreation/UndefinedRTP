package com.redmagic.undefinedrtp.data

import com.redmagic.undefinedapi.builders.ItemBuilder
import com.redmagic.undefinedapi.extension.string.toSmallText
import com.redmagic.undefinedrtp.UndefinedRTP
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class ConfigManager(val plugin: UndefinedRTP) {

    val miniMessage = MiniMessage.miniMessage()

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
        allowedBlocks.save()
        plugin.saveConfig()
    }

    fun RTPWorld.save(string: String){
        plugin.config.set("$string.name", worldName)
        plugin.config.set("$string.range", range)
    }

    fun MutableList<Material>.save(){
        val list: MutableList<String> = mutableListOf()
        forEach {
            list.add(it.name)
        }
        plugin.config.set("allow-blocks", list)
    }

    fun getItemStackAllowedBlocks(): MutableList<ItemStack>{
        val list: MutableList<ItemStack> = mutableListOf()

        plugin.configManager!!.allowedBlocks.forEach {
            list.add(ItemBuilder(it)
                    .setName(miniMessage.deserialize("<!i><#4fdb72>${it.name.replace("_", " ").toSmallText()}"))
                    .addLine(Component.text(" "))
                    .addLine(miniMessage.deserialize("<gray>ᴄʟɪᴄᴋ ᴛᴏ ʀᴇᴍᴏᴠᴇ ꜰʀᴏᴍ ᴀʟʟᴏᴡᴇᴅ ʙʟᴏᴄᴋѕ")).build()
            )
        }
        return list
    }

    fun getBlockedBlockItems(): MutableList<ItemStack>{
        val list: MutableList<ItemStack> = mutableListOf()

        Material.entries.forEach {
            if (it.isBlock && !allowedBlocks.contains(it) && it.isSolid)
                list.add(ItemBuilder(it)
                        .setName(miniMessage.deserialize("<!i><#4be394>${it.name.replace("_", " ").toSmallText()}"))
                        .addLine(Component.text(" "))
                        .addLine(miniMessage.deserialize("<!i><gray>ᴄʟɪᴄᴋ ᴛᴏ ᴀᴅᴅ ${it.name.replace("_", " ").toSmallText()} ᴛᴏ ᴀʟʟᴏᴡᴇᴅ ʙʟᴏᴄᴋѕ"))
                        .build())
        }

        return list
    }
}