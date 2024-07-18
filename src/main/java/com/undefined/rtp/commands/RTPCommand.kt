package com.undefined.rtp.commands

import com.undefined.api.command.UndefinedCommand
import com.undefined.api.event.event
import com.undefined.api.extension.string.translateColor
import com.undefined.api.menu.MenuManager.openMenu
import com.undefined.api.scheduler.sync
import com.undefined.rtp.UndefinedRTP
import org.bukkit.Bukkit
import org.bukkit.event.player.PlayerQuitEvent
import java.util.UUID

class RTPCommand(val plugin: UndefinedRTP) {

    val queueList: MutableList<UUID> = mutableListOf()

    init {

        event<PlayerQuitEvent> { queueList.remove(player.uniqueId) }

        UndefinedCommand("rtp", "undefined.rtp.command")
            .addExecutePlayer {
                openMenu(plugin.rtpgui!!)
                return@addExecutePlayer true
            }

        UndefinedCommand("rtpQueue", permission = "undefined.rtp.queue.command")
            .addExecutePlayer {

                player!!.sendMessage("<reset><#32e67d>ʏᴏᴜ ʜᴀᴠᴇ ʙᴇᴇɴ ᴀᴅᴅᴇᴅ ᴛᴏ ᴛʜᴇ ʀᴛᴘ ǫᴜᴇᴜᴇ.".translateColor())
                if (queueList.size >= 1) {

                    val secondsPlayer = queueList[0]
                    val secondsP = Bukkit.getPlayer(secondsPlayer)!!
                    secondsP.sendMessage("<reset><#32e67d>ᴘʟᴀʏᴇʀ ꜰᴏᴜɴᴅ.".translateColor())
                    player!!.sendMessage("<reset><#32e67d>ᴘʟᴀʏᴇʀ ꜰᴏᴜɴᴅ.".translateColor())
                    queueList.remove(secondsPlayer)

                    plugin.rtpManager.getCheckLocationAsync(plugin.configManager.rtpWorld).thenAccept {
                        if (it == null) {
                            player!!.sendMessage("<reset><#32e67d>ᴄᴀɴ'ᴛ ꜰɪɴᴅ ʟᴏᴄᴀᴛɪᴏɴ.".translateColor())
                            secondsP.sendMessage("<reset><#32e67d>ᴄᴀɴ'ᴛ ꜰɪɴᴅ ʟᴏᴄᴀᴛɪᴏɴ.".translateColor())
                            return@thenAccept
                        }
                        sync {
                            player!!.teleport(it)
                            secondsP.teleport(it)
                            player!!.sendMessage("<reset><#d92323>ᴛᴇʟᴇᴘᴏʀᴛᴇᴅ.".translateColor())
                            secondsP.sendMessage("<reset><#d92323>ᴛᴇʟᴇᴘᴏʀᴛᴇᴅ.".translateColor())
                        }
                    }
                } else {
                    queueList.add(player!!.uniqueId)
                }

                return@addExecutePlayer true
            }

    }
}