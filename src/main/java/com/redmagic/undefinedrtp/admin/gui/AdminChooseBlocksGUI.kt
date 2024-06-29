package com.redmagic.undefinedrtp.admin.gui

import com.undefined.api.builders.ItemBuilder
import com.undefined.api.menu.MenuManager.openMenu
import com.undefined.api.menu.MenuSize
import com.undefined.api.menu.normal.button.Button
import com.undefined.api.menu.normal.button.ClickData
import com.undefined.api.menu.page.UndefinedPageMenu
import com.undefined.api.menu.setRow
import com.redmagic.undefinedrtp.UndefinedRTP
import com.undefined.api.extension.string.translateColor
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

class AdminChooseBlocksGUI(list: List<ItemStack>, val plugin: UndefinedRTP): UndefinedPageMenu("ᴀʟʟᴏᴡᴇᴅ ʙʟᴏᴄᴋѕ", MenuSize.LARGE, list) {

    override fun generateInventory(): Inventory = createPageInventory {

        setRow(5, ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName(" ").build())

        setItem(49, ItemBuilder(Material.BARRIER)
                .setName("<reset><#d92323>ʙᴀᴄᴋ ᴀɴ ᴍᴇɴᴜ".translateColor())
                .addLine(" ")
                .addLine("<reset><gray>ᴄʟɪᴄᴋ ᴛᴏ ɢᴏ ʙᴀᴄᴋ ᴀɴ ᴍᴇɴᴜ".translateColor())
                .build())

        addButton(Button(49){
            player.openMenu(AdminAllowedBlocks(plugin.configManager.getItemStackAllowedBlocks(), plugin))
        })

        setBackButton(
                45, ItemBuilder(Material.RED_STAINED_GLASS_PANE)
                .setName("<reset><#32e67d>ʙᴀᴄᴋ".translateColor())
                .addLine(" ")
                .addLine("<gray>ᴄʟɪᴄᴋ ᴛᴏ ɢᴏ ʙᴀᴄᴋ ᴀ ᴘᴀɢᴇ".translateColor())
                .build(),
                ItemBuilder(Material.GRAY_STAINED_GLASS_PANE)
                        .setName(" ").build()
        )

        setNextButton(53, ItemBuilder(Material.LIME_STAINED_GLASS_PANE)
                .setName("<reset><#32e67d>ɴᴇxᴛ".translateColor())
                .addLine(" ")
                .addLine("<gray>ᴄʟɪᴄᴋ ᴛᴏ ɢᴏ ᴛᴏ ᴛʜᴇ ɴᴇxᴛ ᴘᴀɢᴇ".translateColor()).build(),
                ItemBuilder(Material.GRAY_STAINED_GLASS_PANE)
                        .setName(" ").build()
        )
    }

    override var clickData: ClickData.() -> Unit = {
        plugin.configManager.allowedBlocks.add(item!!.type)

        player.openMenu(AdminAllowedBlocks(plugin.configManager.getItemStackAllowedBlocks(), plugin))
    }
}