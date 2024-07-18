package com.undefined.rtp.gui

import com.undefined.api.builders.ItemBuilder
import com.undefined.api.event.event
import com.undefined.api.menu.MenuManager.closeMenu
import com.undefined.api.menu.MenuSize
import com.undefined.api.menu.normal.UndefinedMenu
import com.undefined.api.scheduler.repeatingTask
import com.undefined.api.scheduler.sync
import com.undefined.rtp.UndefinedRTP
import com.undefined.api.extension.string.translateColor
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.title.TitlePart
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.inventory.Inventory
import java.util.*

class RTPGUI(val plugin: UndefinedRTP): UndefinedMenu("ʀᴀɴᴅᴏᴍ ᴛᴇʟᴇᴘᴏʀᴛ", menuSize = MenuSize.MINI) {

    private val teleporting: HashMap<UUID, Int> = hashMapOf()

    private val cooldown: HashMap<UUID, Int> = hashMapOf()


    init {

        event<PlayerMoveEvent> {
            if (!teleporting.contains(player.uniqueId)) return@event
            if (to!!.x != from.x || to!!.y != from.y || to!!.z != from.z) {
                teleporting.remove(player.uniqueId)
                player.sendTitle("", "<reset><#d92323>ᴛᴇʟᴇᴘᴏʀᴛ ᴄᴀɴᴄᴇʟᴇᴅ".translateColor())
            }
        }
        event<PlayerQuitEvent> { teleporting.remove(player.uniqueId) }

    }

    override fun generateInventory(): Inventory = createInventory {
        fillEmpty(ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName(" ").build())

        setItem(11, ItemBuilder(Material.GRASS_BLOCK)
                .setName("<reset><bold><#2ca34c>ᴏᴠᴇʀᴡᴏʀʟᴅ".translateColor())
                .addLine(" ")
                .addLine("<reset><gray>ᴄʟɪᴄᴋ ᴛᴏ ʀᴀɴᴅᴏᴍ ᴛᴇʟᴇᴘᴏʀᴛ ɪɴ ᴛʜᴇ ᴏᴠᴇʀᴡᴏʀʟᴅ".translateColor())
                .build()
        )

        setItem(15, ItemBuilder(Material.END_STONE)
                .setName("<reset><bold><#9134d9>ᴇɴᴅ".translateColor())
                .addLine(" ")
                .addLine("<reset><gray>ᴄʟɪᴄᴋ ᴛᴏ ʀᴀɴᴅᴏᴍ ᴛᴇʟᴇᴘᴏʀᴛ ɪɴ ᴛʜᴇ ᴇɴᴅ".translateColor())
                .build())

        setItem(13, ItemBuilder(Material.NETHERRACK)
                .setName("<reset><bold><#d9212a>ɴᴇᴛʜᴇʀ".translateColor())
                .addLine(" ")
                .addLine("<reset><gray>ᴄʟɪᴄᴋ ᴛᴏ ʀᴀɴᴅᴏᴍ ᴛᴇʟᴇᴘᴏʀᴛ ɪɴ ᴛʜᴇ ɴᴇᴛʜᴇʀ".translateColor())
                .build())

        addButtons(listOf(11, 13, 15)){

            if (teleporting.contains(player.uniqueId)){
                player.sendMessage("<reset><#d92323>ʏᴏᴜ ᴀʀᴇ ᴀʟʀᴇᴀᴅʏ ʙᴇᴇɴɪɴɢ ᴛᴇʟᴇᴘᴏʀᴛᴇᴅ".translateColor())
                player.closeInventory()
                return@addButtons
            }

            if (cooldown.contains(player.uniqueId)){
                player.sendMessage("<reset><#d92323>ʏᴏᴜ ᴀʀᴇ ᴏɴ ᴄᴏᴏʟᴅᴏᴡɴ ꜰᴏʀ ${cooldown[player.uniqueId]} ѕᴇᴄᴏɴᴅѕ".translateColor())
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

            player.closeMenu()

            val location = plugin.rtpManager.getCheckLocationAsync(bukkitWorld)

            location.thenAccept{

                sync {

                    if (it == null){
                        player.sendMessage("<reset><#d92323>ᴄᴀɴ'ᴛ ꜰɪɴᴅ ʟᴏᴄᴀᴛɪᴏɴ".translateColor())
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

                            player.sendTitle("", "<reset><#32e67d>ᴛᴇʟᴇᴘᴏʀᴛɪɴɢ ɪɴ ${teleporting[player.uniqueId]} ѕᴇᴄᴏɴᴅѕ".translateColor())

                            var left = teleporting[player.uniqueId]!!
                            left--
                            if (left < 0){
                                teleporting.remove(player.uniqueId)

                                player.sendTitle("", "<reset><#32e67d>ᴛᴇʟᴇᴘᴏʀᴛᴇᴅ...".translateColor())

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