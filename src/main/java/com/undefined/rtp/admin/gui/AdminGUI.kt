package com.undefined.rtp.admin.gui

import com.undefined.api.builders.ItemBuilder
import com.undefined.api.menu.MenuManager.openMenu
import com.undefined.api.menu.MenuSize
import com.undefined.api.menu.normal.UndefinedMenu
import com.undefined.api.menu.normal.button.Button
import com.undefined.rtp.UndefinedRTP
import com.undefined.api.extension.string.translateColor
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.wesjd.anvilgui.AnvilGUI
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.Inventory

class AdminGUI(private val plugin: UndefinedRTP): UndefinedMenu("ᴀᴅᴍɪɴ ɢᴜɪ", MenuSize.MINI) {

    override fun generateInventory(): Inventory = createInventory {

        fillEmpty(ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName(" ").build())

        setOverworldItem(this)
        setNetherItem(this)
        setEndItem(this)
        setMaxAttemptsItem(this)
        setCooldownItem(this)
        setAutoFillItem(this)
        setAllowedBlocks(this)
        setCountDownItem(this)

        //World name and range
        addButtons(listOf(10,11,12)){

            val world = when(slot){
                10 -> plugin.configManager.overWorld
                11 -> plugin.configManager.netherWorld
                12 -> plugin.configManager.endWorld
                else -> null
            }

            if (world == null) return@addButtons

            if (click.isRightClick){
                //Change World Range

                val builder = AnvilGUI.Builder()
                    .itemLeft(ItemBuilder(item!!.type).setName("<aqua>${world.range}".translateColor()).build())
                    .title("ᴄʜᴀɴɢᴇ ʀᴀɴɢᴇ")
                    .text(world.range.toString())
                    .plugin(plugin)
                builder.onClick { _, clickEvent ->

                    val text = clickEvent.text

                    try {

                        val range = text.toDouble()

                        world.range = range

                        when(slot){
                            10 -> setOverworldItem(inventory)
                            11 -> setNetherItem(inventory)
                            12 -> setEndItem(inventory)
                        }

                    }catch (e: NumberFormatException){
                        player.sendMessage("<red>$text is not a number.".translateColor())
                    }

                    return@onClick listOf(AnvilGUI.ResponseAction.run{
                        player.openMenu(plugin.adminManager.adminGUI)
                    })
                }

                builder.open(player)


            }else if (click.isLeftClick){
                //Change World Name

                val builder = AnvilGUI.Builder()
                    .itemLeft(ItemBuilder(item!!.type).setName("<aqua>${world.worldName}".translateColor()).build())
                    .title("ᴄʜᴀɴɢᴇ ᴡᴏʀʟᴅ ɴᴀᴍᴇ")
                    .text(world.worldName)
                    .plugin(plugin)
                builder.onClick { _, clickEvent ->

                    val text = clickEvent.text

                    val bukkitWorld = Bukkit.getWorld(text)
                    if (bukkitWorld != null){
                        world.worldName = text
                    }else{
                        player.sendMessage("<red>$text world doesn't exists".translateColor())
                    }

                    return@onClick listOf(AnvilGUI.ResponseAction.run{
                        player.openMenu(plugin.adminManager.adminGUI)
                    })
                }

                builder.open(player)

            }
        }
        //Attemps
        addButton(Button(24){


            val builder = AnvilGUI.Builder()
                .itemLeft(ItemBuilder(item!!.type).setName("<aqua>${plugin.configManager.maxAttemps}".translateColor()).build())
                .title("ᴄʜᴀɴɢᴇ ᴍᴀx ᴀᴛᴛᴇᴍᴘѕ")
                .text(plugin.configManager.maxAttemps.toString())
                .plugin(plugin)
            builder.onClick { _, clickEvent ->

                val text = clickEvent.text

                try {

                    val attemps = text.toInt()
                    plugin.configManager.maxAttemps = attemps
                    setMaxAttemptsItem(inventory)
                }catch (e: NumberFormatException){
                    player.sendMessage("<red>$text is not a number.".translateColor())
                }

                return@onClick listOf(AnvilGUI.ResponseAction.run{
                    player.openMenu(plugin.adminManager.adminGUI)
                })
            }

            builder.open(player)

        })
        //Cooldown
        addButton(Button(6){


            val builder = AnvilGUI.Builder()
                .itemLeft(ItemBuilder(item!!.type).setName("<aqua>${plugin.configManager.cooldown}".translateColor()).build())
                .title("ᴄʜᴀɴɢᴇ ᴄᴏᴏʟᴅᴏᴡɴ")
                .text(plugin.configManager.cooldown.toString())
                .plugin(plugin)
            builder.onClick { _, clickEvent ->

                val text = clickEvent.text

                try {

                    val cooldown = text.toInt()
                    plugin.configManager.cooldown = cooldown
                    setCooldownItem(inventory)
                }catch (e: NumberFormatException){
                    player.sendMessage("<red>$text is not a number.".translateColor())
                }

                return@onClick listOf(AnvilGUI.ResponseAction.run{
                    player.openMenu(plugin.adminManager.adminGUI)
                })
            }

            builder.open(player)

        })

        addButton(Button(15){


            val builder = AnvilGUI.Builder()
                    .itemLeft(ItemBuilder(item!!.type).setName("<aqua>${plugin.configManager.countdown}".translateColor()).build())
                    .title("ᴄʜᴀɴɢᴇ ᴄᴏᴜɴᴛᴅᴏᴡɴ")
                    .text(plugin.configManager.cooldown.toString())
                    .plugin(plugin)
            builder.onClick { _, clickEvent ->

                val text = clickEvent.text

                try {

                    val countdown = text.toInt()
                    plugin.configManager.countdown = countdown
                    setCountDownItem(inventory)
                }catch (e: NumberFormatException){
                    player.sendMessage("<red>$text is not a number.".translateColor())
                }

                return@onClick listOf(AnvilGUI.ResponseAction.run{
                    player.openMenu(plugin.adminManager.adminGUI)
                })
            }

            builder.open(player)

        })

        addButton(Button(16){

            plugin.configManager.autoFillWorldBolder = !plugin.configManager.autoFillWorldBolder

            setAutoFillItem(inventory)
            setNetherItem(inventory)
            setEndItem(inventory)
            setOverworldItem(inventory)

        })

        addButton(Button(14){

            player.openMenu(AdminAllowedBlocks(plugin.configManager.getItemStackAllowedBlocks(), plugin))

        })


    }


    private fun setAllowedBlocks(inventory: Inventory){
        inventory.setItem(14, ItemBuilder(Material.OAK_LOG)
            .setName(("<reset><bold><#33a14f>ᴀʟʟᴏᴡᴇᴅ ʙʟᴏᴄᴋѕ".translateColor()))
            .addLine(" ")
            .addLine(("<reset><gray>ᴄʟɪᴄᴋ ᴛᴏ ѕᴇᴇ ᴀɴᴅ ᴇᴅɪᴛ ᴀʟʟ ᴀʟʟᴏᴡᴇᴅ ʙʟᴏᴄᴋѕ".translateColor()))
            .build())
    }

    private fun setAutoFillItem(inventory: Inventory){

        val string = when(plugin.configManager.autoFillWorldBolder){
            true -> "<reset><#32e67d>ᴇɴᴀʙʟᴇᴅ".translateColor()
            false -> "<reset><#d92323>ᴅɪѕᴀʙʟᴇᴅ".translateColor()
        }

        val material = when(plugin.configManager.autoFillWorldBolder){
            true -> Material.LIME_CONCRETE
            false -> Material.RED_CONCRETE
        }

        inventory.setItem(16, ItemBuilder(material)
            .setName("<reset><bold><#7748f7>ᴀᴜᴛᴏ ꜰɪʟʟ ᴡᴏʀʟᴅʙᴏʀᴅᴇʀ".translateColor())
            .addLine(" ")
            .addLine(string)
            .addLine(" ")
            .addLine("<reset><gray>ᴄʟɪᴄᴋ ᴛᴏ ᴛᴏɢɢʟᴇ ᴀᴜᴛᴏ ꜰɪʟʟ ᴡᴏʀʟᴅ ʙᴏʀᴅᴇʀ".translateColor())
            .build())
    }

    private fun setMaxAttemptsItem(inventory: Inventory){
        inventory.setItem(24, ItemBuilder(Material.REPEATER)
            .setName("<reset><bold><#21d98c>ᴍᴀx ᴀᴛᴛᴇᴍᴘѕ".translateColor())
            .addLine(" ")
            .addLine("<reset><aqua>ᴀᴛᴛᴇᴍᴘѕ  <gray>${plugin.configManager.maxAttemps}".translateColor())
            .addLine(" ")
            .addLine("<reset><gray>ᴄʟɪᴄᴋ ᴛᴏ ᴄʜᴀɴɢᴇ ᴛʜᴇ ᴍᴀx ᴀᴍᴏᴜɴᴛ ᴏꜰ ᴀᴛᴛᴇᴍᴘѕ".translateColor())
            .build())
    }

    private fun setCooldownItem(inventory: Inventory){
        inventory.setItem(6, ItemBuilder(Material.CLOCK)
            .setName("<reset><bold><#e6a732>ᴄᴏᴏʟᴅᴏᴡɴ".translateColor())
            .addLine(" ")
            .addLine("<reset><gray>${plugin.configManager.cooldown} <aqua>ѕᴇᴄᴏɴᴅѕ".translateColor())
            .addLine(" ")
            .addLine("<reset><gray>ᴄʟɪᴄᴋ ᴛᴏ ᴄʜᴀɴɢᴇ ᴛʜᴇ ᴄᴏᴏʟᴅᴏᴡɴ ᴛɪᴍᴇ".translateColor())
            .build())
    }

    private fun setNetherItem(inventory: Inventory){
        inventory.setItem(11,
            ItemBuilder(Material.NETHERRACK)
                .setName("<reset><bold><#d9212a>ɴᴇᴛʜᴇʀ".translateColor())
                .addLine(" ")
                .addLine("<reset><aqua>ᴡᴏʀʟᴅ ɴᴀᴍᴇ  <gray>${plugin.configManager.netherWorld.worldName}".translateColor())
                .addLine("<reset><aqua>ʀᴀɴɢᴇ  ${
                    if (plugin.configManager.autoFillWorldBolder){
                        "<#32e67d>ᴀᴜᴛᴏ ꜰɪʟʟ"
                    }else{
                        "<gray>${plugin.configManager.netherWorld.range}"
                    }
                }".translateColor())
                .addLine(" ")
                .addLine("<reset><gray>ʟᴇꜰᴛ ᴄʟɪᴄᴋ ᴛᴏ ᴄʜᴀɴɢᴇ ᴡᴏʀʟᴅ ɴᴀᴍᴇ".translateColor())
                .addLine("<reset><gray>ʀɪɢʜᴛ ᴄʟɪᴄᴋ ᴛᴏ ᴄʜᴀɴɢᴇ ʀᴀɴɢᴇ".translateColor())
                .build()
        )
    }
    private fun setEndItem(inventory: Inventory){
        inventory.setItem(12,
            ItemBuilder(Material.END_STONE)
                .setName("<reset><bold><#9134d9>ᴇɴᴅ".translateColor())
                .addLine(" ")
                .addLine("<reset><aqua>ᴡᴏʀʟᴅ ɴᴀᴍᴇ  <gray>${plugin.configManager.endWorld.worldName}".translateColor())
                .addLine("<reset><aqua>ʀᴀɴɢᴇ  ${
                    if (plugin.configManager.autoFillWorldBolder){
                        "<#32e67d>ᴀᴜᴛᴏ ꜰɪʟʟ"
                    }else{
                        "<gray>${plugin.configManager.endWorld.range}"
                    }
                }".translateColor())
                .addLine(" ")
                .addLine("<reset><gray>ʟᴇꜰᴛ ᴄʟɪᴄᴋ ᴛᴏ ᴄʜᴀɴɢᴇ ᴡᴏʀʟᴅ ɴᴀᴍᴇ".translateColor())
                .addLine("<reset><gray>ʀɪɢʜᴛ ᴄʟɪᴄᴋ ᴛᴏ ᴄʜᴀɴɢᴇ ʀᴀɴɢᴇ".translateColor())
                .build()
        )
    }

    private fun setOverworldItem(inventory: Inventory){
        inventory.setItem(10,
            ItemBuilder(Material.GRASS_BLOCK)
                .setName("<reset><bold><#2ca34c>ᴏᴠᴇʀᴡᴏʀʟᴅ".translateColor())
                .addLine(" ")
                .addLine("<reset><aqua>ᴡᴏʀʟᴅ ɴᴀᴍᴇ  <gray>${plugin.configManager.overWorld.worldName}".translateColor())
                .addLine("<reset><aqua>ʀᴀɴɢᴇ  ${
                    if (plugin.configManager.autoFillWorldBolder){
                        "<#32e67d>ᴀᴜᴛᴏ ꜰɪʟʟ"
                    }else{
                        "<gray>${plugin.configManager.overWorld.range}"
                    }
                }".translateColor())
                .addLine(" ")
                .addLine("<reset><gray>ʟᴇꜰᴛ ᴄʟɪᴄᴋ ᴛᴏ ᴄʜᴀɴɢᴇ ᴡᴏʀʟᴅ ɴᴀᴍᴇ".translateColor())
                .addLine("<reset><gray>ʀɪɢʜᴛ ᴄʟɪᴄᴋ ᴛᴏ ᴄʜᴀɴɢᴇ ʀᴀɴɢᴇ".translateColor())
                .build()
        )
    }

    private fun setCountDownItem(inventory: Inventory){
        inventory.setItem(15, ItemBuilder(Material.COMPASS)
                .setName("<reset><bold><#2089d4>ᴄᴏᴜɴᴛᴅᴏᴡɴ".translateColor())
                .addLine(" ")
                .addLine("<reset><gray>${plugin.configManager.countdown} <aqua>ѕᴇᴄᴏɴᴅѕ".translateColor())
                .addLine(" ")
                .addLine("<reset><gray>ᴄʟɪᴄᴋ ᴛᴏ ᴄʜᴀɴɢᴇ ᴛʜᴇ ᴄᴏᴜɴᴛᴅᴏᴡɴ ᴛɪᴍᴇ".translateColor())
                .build())
    }

}