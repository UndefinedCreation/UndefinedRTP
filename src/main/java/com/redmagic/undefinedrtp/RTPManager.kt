package com.redmagic.undefinedrtp

import com.undefined.api.extension.randomLocation
import org.bukkit.Location
import org.bukkit.World
import java.util.concurrent.CompletableFuture
class RTPManager(val plugin: UndefinedRTP) {

    private fun getLocation(world: World): Location{

        val range = if (plugin.configManager.autoFillWorldBolder) {
            world.worldBorder.size /2
        }else{
            when(world.environment){
                World.Environment.NORMAL -> plugin.configManager.overWorld.range
                World.Environment.NETHER -> plugin.configManager.netherWorld.range
                World.Environment.THE_END -> plugin.configManager.endWorld.range
                else -> 0
            }
        }

        return world.randomLocation(range.toDouble())
    }

    private fun getCheckLocation(world: World): Location?{

        var attempts = 0
        while (attempts < plugin.configManager.maxAttemps) {
            val location = getLocation(world)
            if (location.clone().subtract(0.0,1.0,0.0).block.type in plugin.configManager.allowedBlocks && location.clone().add(0.0,1.0,0.0).block.type.isAir) {
                return location
            }
            attempts++
        }
        return null

    }

    fun getCheckLocationAsync(world: World): CompletableFuture<Location?>{
        return CompletableFuture.supplyAsync {
            getCheckLocation(world)
        }
    }

}