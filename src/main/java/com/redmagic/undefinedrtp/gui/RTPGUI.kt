package com.redmagic.undefinedrtp.gui

import com.redmagic.undefinedapi.builders.ItemBuilder
import com.redmagic.undefinedapi.customEvents.PlayerMoveEvent
import com.redmagic.undefinedapi.event.event
import com.redmagic.undefinedapi.menu.MenuManager.closeMenu
import com.redmagic.undefinedapi.menu.MenuSize
import com.redmagic.undefinedapi.menu.normal.UndefinedMenu
import com.redmagic.undefinedapi.scheduler.repeatingTask
import com.redmagic.undefinedapi.scheduler.sync
import com.redmagic.undefinedrtp.UndefinedRTP
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.title.TitlePart
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.inventory.Inventory
import java.util.*

class RTPGUI(val plugin: UndefinedRTP): UndefinedMenu("ʀᴀɴᴅᴏᴍ ᴛᴇʟᴇᴘᴏʀᴛ", menuSize = MenuSize.MINI) {

    private val miniMessage = MiniMessage.miniMessage()

    private val teleporting: HashMap<UUID, Int> = hashMapOf()

    private val cooldown: HashMap<UUID, Int> = hashMapOf()


    init {

        event<PlayerMoveEvent> {
            if (!teleporting.contains(player.uniqueId)) return@event
            if (player.location.x == fromLocation.x && player.location.y == fromLocation.y && player.location.z == fromLocation.z) return@event
            teleporting.remove(player.uniqueId)
            player.sendTitlePart(TitlePart.SUBTITLE, miniMessage.deserialize("<!i><#d92323>ᴛᴇʟᴇᴘᴏʀᴛ ᴄᴀɴᴄᴇʟᴇᴅ"))
        }
        event<PlayerQuitEvent> { teleporting.remove(player.uniqueId) }

    }

    override fun generateInventory(): Inventory = createInventory {
        fillEmpty(ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName(" ").build())

        setItem(11, ItemBuilder(Material.GRASS_BLOCK)
                .setName(miniMessage.deserialize("<!i><bold><gradient:#39db64:#2ca34c>ᴏᴠᴇʀᴡᴏʀʟᴅ</gradient>"))
                .addLine(Component.text(" "))
                .addLine(miniMessage.deserialize("<!i><gray>ᴄʟɪᴄᴋ ᴛᴏ ʀᴀɴᴅᴏᴍ ᴛᴇʟᴇᴘᴏʀᴛ ɪɴ ᴛʜᴇ ᴏᴠᴇʀᴡᴏʀʟᴅ"))
                .build()
        )

        setItem(15, ItemBuilder(Material.END_STONE)
                .setName(miniMessage.deserialize("<!i><bold><gradient:#ab49f5:#9134d9>ᴇɴᴅ</gradient>"))
                .addLine(Component.text(" "))
                .addLine(miniMessage.deserialize("<!i><gray>ᴄʟɪᴄᴋ ᴛᴏ ʀᴀɴᴅᴏᴍ ᴛᴇʟᴇᴘᴏʀᴛ ɪɴ ᴛʜᴇ ᴇɴᴅ"))
                .build())

        setItem(13, ItemBuilder(Material.NETHERRACK)
                .setName(miniMessage.deserialize("<!i><bold><gradient:#f5252f:#d9212a>ɴᴇᴛʜᴇʀ</gradient>"))
                .addLine(Component.text(" "))
                .addLine(miniMessage.deserialize("<!i><gray>ᴄʟɪᴄᴋ ᴛᴏ ʀᴀɴᴅᴏᴍ ᴛᴇʟᴇᴘᴏʀᴛ ɪɴ ᴛʜᴇ ɴᴇᴛʜᴇʀ"))
                .build())

        addButtons(listOf(11, 13, 15)){

            if (teleporting.contains(player.uniqueId)){
                player.sendMessage(miniMessage.deserialize("<!i><#d92323>ʏᴏᴜ ᴀʀᴇ ᴀʟʀᴇᴀᴅʏ ʙᴇᴇɴɪɴɢ ᴛᴇʟᴇᴘᴏʀᴛᴇᴅ"))
                player.closeInventory()
                return@addButtons
            }

            if (cooldown.contains(player.uniqueId)){
                player.sendMessage(miniMessage.deserialize("<!i><#d92323>ʏᴏᴜ ᴀʀᴇ ᴏɴ ᴄᴏᴏʟᴅᴏᴡɴ ꜰᴏʀ ${cooldown[player.uniqueId]} ѕᴇᴄᴏɴᴅѕ"))
                player.closeInventory()
                return@addButtons
            }

            val world = when(slot){
                11 -> plugin.configManager.overWorld
                13 -> plugin.configManager.netherWorld
                15 -> plugin.configManager.endWorld
                else -> null
            } ?: return@addButtons

            val bukkitWorld = Bukkit.getWorld(world.worldName) ?: return@addButtons

            val location = plugin.rtpManager.getCheckLocationAsync(bukkitWorld)

            location.thenAccept{
                println(it)
                sync {

                    if (it == null){
                        player.sendMessage(miniMessage.deserialize("<!i><#d92323>ᴄᴀɴ'ᴛ ꜰɪɴᴅ ʟᴏᴄᴀᴛɪᴏɴ"))
                        player.closeMenu()
                        player.closeInventory()
                        return@sync
                    }

                    cooldown[player.uniqueId] = plugin.configManager.cooldown
                    teleporting[player.uniqueId] = plugin.configManager.countdown

                    player.closeMenu()
                    player.closeInventory()
                    repeatingTask(20){

                        if (!teleporting.containsKey(player.uniqueId) && !cooldown.containsKey(player.uniqueId)){
                            cancel()
                            return@repeatingTask
                        }

                        if (teleporting.containsKey(player.uniqueId)){

                            player.sendTitlePart(TitlePart.SUBTITLE, miniMessage.deserialize("<!i><#32e67d>ᴛᴇʟᴇᴘᴏʀᴛɪɴɢ ɪɴ ${teleporting[player.uniqueId]} ѕᴇᴄᴏɴᴅѕ"))

                            var left = teleporting[player.uniqueId]!!
                            left--
                            if (left < 0){
                                teleporting.remove(player.uniqueId)

                                player.sendTitlePart(TitlePart.SUBTITLE, miniMessage.deserialize("<!i><#32e67d>ᴛᴇʟᴇᴘᴏʀᴛᴇᴅ..."))

                                player.teleport(it.add(0.0,1.0,0.0))
                            }else{
                                teleporting[player.uniqueId] = left
                            }
                        }

                        if (cooldown.containsKey(player.uniqueId)){

                            var left = cooldown[player.uniqueId]!!
                            left--
                            if (left < 0){
                                cooldown.remove(player.uniqueId)
                            }else{
                                cooldown[player.uniqueId] = left
                            }


                        }

                    }

                }
            }



        }


    }
}