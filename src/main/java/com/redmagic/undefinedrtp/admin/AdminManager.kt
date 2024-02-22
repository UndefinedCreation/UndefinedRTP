package com.redmagic.undefinedrtp.admin

import com.redmagic.undefinedrtp.UndefinedRTP
import com.redmagic.undefinedrtp.admin.gui.AdminGUI

class AdminManager(plugin: UndefinedRTP){
    val adminGUI: AdminGUI = AdminGUI(plugin)
}