package com.redmagic.undefinedrtp.admin.gui

import com.redmagic.undefinedapi.builders.ItemBuilder
import com.redmagic.undefinedapi.menu.MenuManager.openMenu
import com.redmagic.undefinedapi.menu.MenuSize
import com.redmagic.undefinedapi.menu.normal.button.Button
import com.redmagic.undefinedapi.menu.normal.button.ClickData
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import com.redmagic.undefinedapi.menu.page.UndefinedPageMenu
import com.redmagic.undefinedapi.menu.setRow
import com.redmagic.undefinedrtp.UndefinedRTP

class AdminAllowedBlocks(list: List<ItemStack>, val plugin: UndefinedRTP): UndefinedPageMenu("ᴀʟʟᴏᴡᴇᴅ ʙʟᴏᴄᴋѕ",MenuSize.LARGE, list) {

    private val miniMessage = MiniMessage.miniMessage()

    override fun generateInventory(): Inventory = createPageInventory {

        setRow(5, ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName(" ").build())

        setItem(50, ItemBuilder(Material.HOPPER)
                .setName(miniMessage.deserialize("<!i><#32e67d>ᴀᴅᴅ ᴀʟʟᴏᴡᴇᴅ ʙʟᴏᴄᴋ"))
                .addLine(Component.text(" "))
                .addLine(miniMessage.deserialize("<!i><gray>ᴄʟɪᴄᴋ ᴛᴏ ᴀᴅᴅ ᴀɴ ᴀʟʟᴏᴡᴇᴅ ʙʟᴏᴄᴋ"))
                .build()
        )

        setItem(48, ItemBuilder(Material.BARRIER)
                .setName(miniMessage.deserialize("<!i><#d92323>ʙᴀᴄᴋ ᴀɴ ᴍᴇɴᴜ"))
                .addLine(Component.text(" "))
                .addLine(miniMessage.deserialize("<!i><gray>ᴄʟɪᴄᴋ ᴛᴏ ɢᴏ ʙᴀᴄᴋ ᴀɴ ᴍᴇɴᴜ"))
                .build())

        addButton(Button(48){
            player.openMenu(plugin.adminManager.adminGUI)
        })

        addButton(Button(50){
            val list = plugin.configManager.getBlockedBlockItems()

            player.openMenu(AdminChooseBlocksGUI(list, plugin))

        })

        setBackButton(
            45, ItemBuilder(Material.RED_STAINED_GLASS_PANE)
                .setName(miniMessage.deserialize("<!i><#32e67d>ʙᴀᴄᴋ"))
                .addLine(Component.text(" "))
                .addLine(miniMessage.deserialize("<gray>ᴄʟɪᴄᴋ ᴛᴏ ɢᴏ ʙᴀᴄᴋ ᴀ ᴘᴀɢᴇ"))
                .build(),
            ItemBuilder(Material.GRAY_STAINED_GLASS_PANE)
                .setName(" ").build()
        )

        setNextButton(53, ItemBuilder(Material.LIME_STAINED_GLASS_PANE)
            .setName(miniMessage.deserialize("<!i><#32e67d>ɴᴇxᴛ"))
            .addLine(Component.text(" "))
            .addLine(miniMessage.deserialize("<gray>ᴄʟɪᴄᴋ ᴛᴏ ɢᴏ ᴛᴏ ᴛʜᴇ ɴᴇxᴛ ᴘᴀɢᴇ")).build(),
            ItemBuilder(Material.GRAY_STAINED_GLASS_PANE)
                .setName(" ").build()
        )



    }


    override var clickData: ClickData.() -> Unit = {
        plugin.configManager.allowedBlocks.remove(item!!.type)
        player.openMenu(plugin.adminManager.adminGUI)
    }

}