package com.undefined.rtp.admin

import com.undefined.rtp.UndefinedRTP
import com.undefined.rtp.admin.gui.AdminGUI

class AdminManager(plugin: UndefinedRTP){
    val adminGUI: AdminGUI = AdminGUI(plugin)
}