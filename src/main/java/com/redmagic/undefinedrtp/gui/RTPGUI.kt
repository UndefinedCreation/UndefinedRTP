package com.redmagic.undefinedrtp.gui

import com.redmagic.undefinedapi.builders.ItemBuilder
import com.redmagic.undefinedapi.menu.MenuSize
import com.redmagic.undefinedapi.menu.normal.UndefinedMenu
import com.redmagic.undefinedrtp.UndefinedRTP
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Material
import org.bukkit.inventory.Inventory

class RTPGUI(plugin: UndefinedRTP): UndefinedMenu("ʀᴀɴᴅᴏᴍ ᴛᴇʟᴇᴘᴏʀᴛ", menuSize = MenuSize.MINI) {

    private val miniMessage = MiniMessage.miniMessage()
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

    }
}