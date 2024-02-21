package com.redmagic.undefinedrtp.admin.gui

import com.redmagic.undefinedapi.builders.ItemBuilder
import com.redmagic.undefinedapi.extension.string.toSmallText
import com.redmagic.undefinedapi.menu.MenuManager.closeMenu
import com.redmagic.undefinedapi.menu.MenuManager.openMenu
import com.redmagic.undefinedapi.menu.MenuSize
import com.redmagic.undefinedapi.menu.normal.UndefinedMenu
import com.redmagic.undefinedapi.menu.normal.button.Button
import com.redmagic.undefinedapi.scheduler.delay
import com.redmagic.undefinedapi.scheduler.sync
import com.redmagic.undefinedrtp.UndefinedRTP
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.wesjd.anvilgui.AnvilGUI
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

class AdminGUI(private val plugin: UndefinedRTP): UndefinedMenu("ᴀᴅᴍɪɴ ɢᴜɪ", MenuSize.MINI) {

    private val miniMessage = MiniMessage.miniMessage()

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
                10 -> plugin.configManager!!.overWorld
                11 -> plugin.configManager!!.netherWorld
                12 -> plugin.configManager!!.endWorld
                else -> null
            }

            if (world == null) return@addButtons

            if (click.isRightClick){
                //Change World Range

                val builder = AnvilGUI.Builder()
                    .itemLeft(ItemBuilder(item!!.type).setName(miniMessage.deserialize("<aqua>${world.range}")).build())
                    .title("ᴄʜᴀɴɢᴇ ʀᴀɴɢᴇ")
                    .text(world.range.toString())
                    .plugin(plugin);
                builder.onClick() { _, clickEvent ->

                    val text = clickEvent.text

                    try {

                        val range = text.toDouble()

                        world.range = range

                        when(slot){
                            10 -> setOverworldItem(inventory!!)
                            11 -> setNetherItem(inventory!!)
                            12 -> setEndItem(inventory!!)
                        }

                    }catch (e: NumberFormatException){
                        player!!.sendMessage(miniMessage.deserialize("<red>$text is not a number."))
                    }

                    return@onClick listOf(AnvilGUI.ResponseAction.run{
                        player!!.openMenu(plugin.adminManager!!.adminGUI)
                    })
                }

                builder.open(player)


            }else if (click.isLeftClick){
                //Change World Name

                val builder = AnvilGUI.Builder()
                    .itemLeft(ItemBuilder(item!!.type).setName(miniMessage.deserialize("<aqua>${world.worldName}")).build())
                    .title("ᴄʜᴀɴɢᴇ ᴡᴏʀʟᴅ ɴᴀᴍᴇ")
                    .text(world.worldName)
                    .plugin(plugin);
                builder.onClick() { _, clickEvent ->

                    val text = clickEvent.text

                    val bukkitWorld = Bukkit.getWorld(text)
                    if (bukkitWorld != null){
                        world.worldName = text
                    }else{
                        player!!.sendMessage(miniMessage.deserialize("<red>$text world doesn't exists"))
                    }

                    return@onClick listOf(AnvilGUI.ResponseAction.run{
                        player!!.openMenu(plugin.adminManager!!.adminGUI)
                    })
                }

                builder.open(player)

            }
        }
        //Attemps
        addButton(Button(24){


            val builder = AnvilGUI.Builder()
                .itemLeft(ItemBuilder(item!!.type).setName(miniMessage.deserialize("<aqua>${plugin.configManager!!.maxAttemps}")).build())
                .title("ᴄʜᴀɴɢᴇ ᴍᴀx ᴀᴛᴛᴇᴍᴘѕ")
                .text(plugin.configManager!!.maxAttemps.toString())
                .plugin(plugin);
            builder.onClick() { _, clickEvent ->

                val text = clickEvent.text

                try {

                    val attemps = text.toInt()
                    plugin.configManager!!.maxAttemps = attemps;
                    setMaxAttemptsItem(inventory!!)
                }catch (e: NumberFormatException){
                    player!!.sendMessage(miniMessage.deserialize("<red>$text is not a number."))
                }

                return@onClick listOf(AnvilGUI.ResponseAction.run{
                    player!!.openMenu(plugin.adminManager!!.adminGUI)
                })
            }

            builder.open(player)

        })
        //Cooldown
        addButton(Button(6){


            val builder = AnvilGUI.Builder()
                .itemLeft(ItemBuilder(item!!.type).setName(miniMessage.deserialize("<aqua>${plugin.configManager!!.cooldown}")).build())
                .title("ᴄʜᴀɴɢᴇ ᴄᴏᴏʟᴅᴏᴡɴ")
                .text(plugin.configManager!!.cooldown.toString())
                .plugin(plugin);
            builder.onClick() { _, clickEvent ->

                val text = clickEvent.text

                try {

                    val cooldown = text.toInt()
                    plugin.configManager!!.cooldown = cooldown;
                    setCooldownItem(inventory!!)
                }catch (e: NumberFormatException){
                    player!!.sendMessage(miniMessage.deserialize("<red>$text is not a number."))
                }

                return@onClick listOf(AnvilGUI.ResponseAction.run{
                    player!!.openMenu(plugin.adminManager!!.adminGUI)
                })
            }

            builder.open(player)

        })

        addButton(Button(15){


            val builder = AnvilGUI.Builder()
                    .itemLeft(ItemBuilder(item!!.type).setName(miniMessage.deserialize("<aqua>${plugin.configManager!!.countdown}")).build())
                    .title("ᴄʜᴀɴɢᴇ ᴄᴏᴜɴᴛᴅᴏᴡɴ")
                    .text(plugin.configManager!!.cooldown.toString())
                    .plugin(plugin);
            builder.onClick() { _, clickEvent ->

                val text = clickEvent.text

                try {

                    val countdown = text.toInt()
                    plugin.configManager!!.countdown = countdown;
                    setCountDownItem(inventory!!)
                }catch (e: NumberFormatException){
                    player!!.sendMessage(miniMessage.deserialize("<red>$text is not a number."))
                }

                return@onClick listOf(AnvilGUI.ResponseAction.run{
                    player!!.openMenu(plugin.adminManager!!.adminGUI)
                })
            }

            builder.open(player)

        })

        addButton(Button(16){

            plugin.configManager!!.autoFillWorldBolder = !plugin.configManager!!.autoFillWorldBolder

            setAutoFillItem(inventory!!)
            setNetherItem(inventory!!)
            setEndItem(inventory!!)
            setOverworldItem(inventory!!)

        })

        addButton(Button(14){

            player!!.openMenu(AdminAllowedBlocks(plugin.configManager!!.getItemStackAllowedBlocks(), plugin))

        })


    }


    private fun setAllowedBlocks(inventory: Inventory){
        inventory.setItem(14, ItemBuilder(Material.OAK_LOG)
            .setName(miniMessage.deserialize("<!i><bold><#33a14f>ᴀʟʟᴏᴡᴇᴅ ʙʟᴏᴄᴋѕ"))
            .addLine(Component.text(" "))
            .addLine(miniMessage.deserialize("<!i><gray>ᴄʟɪᴄᴋ ᴛᴏ ѕᴇᴇ ᴀɴᴅ ᴇᴅɪᴛ ᴀʟʟ ᴀʟʟᴏᴡᴇᴅ ʙʟᴏᴄᴋѕ"))
            .build())
    }

    private fun setAutoFillItem(inventory: Inventory){

        val string = when(plugin.configManager!!.autoFillWorldBolder){
            true -> "<!i><#32e67d>ᴇɴᴀʙʟᴇᴅ"
            false -> "<!i><#d92323>ᴅɪѕᴀʙʟᴇᴅ"
        }

        val material = when(plugin.configManager!!.autoFillWorldBolder){
            true -> Material.LIME_CONCRETE
            false -> Material.RED_CONCRETE
        }

        inventory.setItem(16, ItemBuilder(material)
            .setName(miniMessage.deserialize("<!i><bold><#7748f7>ᴀᴜᴛᴏ ꜰɪʟʟ ᴡᴏʀʟᴅʙᴏʀᴅᴇʀ"))
            .addLine(Component.text(" "))
            .addLine(miniMessage.deserialize(string))
            .addLine(Component.text(" "))
            .addLine(miniMessage.deserialize("<!i><gray>ᴄʟɪᴄᴋ ᴛᴏ ᴛᴏɢɢʟᴇ ᴀᴜᴛᴏ ꜰɪʟʟ ᴡᴏʀʟᴅ ʙᴏʀᴅᴇʀ"))
            .build())
    }

    private fun setMaxAttemptsItem(inventory: Inventory){
        inventory.setItem(24, ItemBuilder(Material.REPEATER)
            .setName(miniMessage.deserialize("<!i><bold><#21d98c>ᴍᴀx ᴀᴛᴛᴇᴍᴘѕ"))
            .addLine(Component.text(" "))
            .addLine(miniMessage.deserialize("<!i><aqua>ᴀᴛᴛᴇᴍᴘѕ  <gray>${plugin.configManager!!.maxAttemps}"))
            .addLine(Component.text(" "))
            .addLine(miniMessage.deserialize("<!i><gray>ᴄʟɪᴄᴋ ᴛᴏ ᴄʜᴀɴɢᴇ ᴛʜᴇ ᴍᴀx ᴀᴍᴏᴜɴᴛ ᴏꜰ ᴀᴛᴛᴇᴍᴘѕ"))
            .build())
    }

    private fun setCooldownItem(inventory: Inventory){
        inventory.setItem(6, ItemBuilder(Material.CLOCK)
            .setName(miniMessage.deserialize("<!i><bold><#e6a732>ᴄᴏᴏʟᴅᴏᴡɴ"))
            .addLine(Component.text(" "))
            .addLine(miniMessage.deserialize("<!i><gray>${plugin.configManager!!.cooldown} <aqua>ѕᴇᴄᴏɴᴅѕ"))
            .addLine(Component.text(" "))
            .addLine(miniMessage.deserialize("<!i><gray>ᴄʟɪᴄᴋ ᴛᴏ ᴄʜᴀɴɢᴇ ᴛʜᴇ ᴄᴏᴏʟᴅᴏᴡɴ ᴛɪᴍᴇ"))
            .build())
    }

    private fun setNetherItem(inventory: Inventory){
        inventory.setItem(11,
            ItemBuilder(Material.NETHERRACK)
                .setName(miniMessage.deserialize("<!i><bold><gradient:#f5252f:#d9212a>ɴᴇᴛʜᴇʀ</gradient>"))
                .addLine(Component.text(" "))
                .addLine(miniMessage.deserialize("<!i><aqua>ᴡᴏʀʟᴅ ɴᴀᴍᴇ  <gray>${plugin.configManager!!.netherWorld.worldName}"))
                .addLine(miniMessage.deserialize("<!i><aqua>ʀᴀɴɢᴇ  ${
                    if (plugin.configManager!!.autoFillWorldBolder){
                        "<#32e67d>ᴀᴜᴛᴏ ꜰɪʟʟ"
                    }else{
                        "<gray>${plugin.configManager!!.netherWorld.range}"
                    }
                }"))
                .addLine(Component.text(" "))
                .addLine(miniMessage.deserialize("<!i><gray>ʟᴇꜰᴛ ᴄʟɪᴄᴋ ᴛᴏ ᴄʜᴀɴɢᴇ ᴡᴏʀʟᴅ ɴᴀᴍᴇ"))
                .addLine(miniMessage.deserialize("<!i><gray>ʀɪɢʜᴛ ᴄʟɪᴄᴋ ᴛᴏ ᴄʜᴀɴɢᴇ ʀᴀɴɢᴇ"))
                .build()
        )
    }
    private fun setEndItem(inventory: Inventory){
        inventory.setItem(12,
            ItemBuilder(Material.END_STONE)
                .setName(miniMessage.deserialize("<!i><bold><gradient:#ab49f5:#9134d9>ᴇɴᴅ</gradient>"))
                .addLine(Component.text(" "))
                .addLine(miniMessage.deserialize("<!i><aqua>ᴡᴏʀʟᴅ ɴᴀᴍᴇ  <gray>${plugin.configManager!!.endWorld.worldName}"))
                .addLine(miniMessage.deserialize("<!i><aqua>ʀᴀɴɢᴇ  ${
                    if (plugin.configManager!!.autoFillWorldBolder){
                        "<#32e67d>ᴀᴜᴛᴏ ꜰɪʟʟ"
                    }else{
                        "<gray>${plugin.configManager!!.endWorld.range}"
                    }
                }"))
                .addLine(Component.text(" "))
                .addLine(miniMessage.deserialize("<!i><gray>ʟᴇꜰᴛ ᴄʟɪᴄᴋ ᴛᴏ ᴄʜᴀɴɢᴇ ᴡᴏʀʟᴅ ɴᴀᴍᴇ"))
                .addLine(miniMessage.deserialize("<!i><gray>ʀɪɢʜᴛ ᴄʟɪᴄᴋ ᴛᴏ ᴄʜᴀɴɢᴇ ʀᴀɴɢᴇ"))
                .build()
        )
    }

    private fun setOverworldItem(inventory: Inventory){
        inventory.setItem(10,
            ItemBuilder(Material.GRASS_BLOCK)
                .setName(miniMessage.deserialize("<!i><bold><gradient:#39db64:#2ca34c>ᴏᴠᴇʀᴡᴏʀʟᴅ</gradient>"))
                .addLine(Component.text(" "))
                .addLine(miniMessage.deserialize("<!i><aqua>ᴡᴏʀʟᴅ ɴᴀᴍᴇ  <gray>${plugin.configManager!!.overWorld.worldName}"))
                .addLine(miniMessage.deserialize("<!i><aqua>ʀᴀɴɢᴇ  ${
                    if (plugin.configManager!!.autoFillWorldBolder){
                        "<#32e67d>ᴀᴜᴛᴏ ꜰɪʟʟ"
                    }else{
                        "<gray>${plugin.configManager!!.overWorld.range}"
                    }
                }"))
                .addLine(Component.text(" "))
                .addLine(miniMessage.deserialize("<!i><gray>ʟᴇꜰᴛ ᴄʟɪᴄᴋ ᴛᴏ ᴄʜᴀɴɢᴇ ᴡᴏʀʟᴅ ɴᴀᴍᴇ"))
                .addLine(miniMessage.deserialize("<!i><gray>ʀɪɢʜᴛ ᴄʟɪᴄᴋ ᴛᴏ ᴄʜᴀɴɢᴇ ʀᴀɴɢᴇ"))
                .build()
        )
    }

    private fun setCountDownItem(inventory: Inventory){
        inventory.setItem(15, ItemBuilder(Material.CLOCK)
                .setName(miniMessage.deserialize("<!i><bold><#2089d4>ᴄᴏᴜɴᴛᴅᴏᴡɴ"))
                .addLine(Component.text(" "))
                .addLine(miniMessage.deserialize("<!i><gray>${plugin.configManager!!.countdown} <aqua>ѕᴇᴄᴏɴᴅѕ"))
                .addLine(Component.text(" "))
                .addLine(miniMessage.deserialize("<!i><gray>ᴄʟɪᴄᴋ ᴛᴏ ᴄʜᴀɴɢᴇ ᴛʜᴇ ᴄᴏᴜɴᴛᴅᴏᴡɴ ᴛɪᴍᴇ"))
                .build())
    }

}