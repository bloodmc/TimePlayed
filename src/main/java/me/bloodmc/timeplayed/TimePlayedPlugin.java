/*
 * This file is part of TimePlayed, licensed under the MIT License (MIT).
 *
 * Copyright (c) bloodmc
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package me.bloodmc.timeplayed;

import me.bloodmc.timeplayed.listener.PlayerEventListener;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TimePlayedPlugin extends JavaPlugin implements CommandExecutor {

    public static Map<UUID, Instant> playedMap = new HashMap<>();

    @Override
    public void onEnable() {
        Bukkit.getPluginCommand("played").setExecutor(this);
        Bukkit.getPluginManager().registerEvents(new PlayerEventListener(), this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)  {
        if ((cmd.getName().equalsIgnoreCase("played"))) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("This command must be run as player.");
                return false;
            }
            final Instant loginTime = playedMap.get(((Player) sender).getUniqueId());
            if (loginTime == null) {
                sender.sendMessage("Could not locate login time. This should never happen, please report to author.");
                return false;
            }

            final long timeElapsed = Duration.between(loginTime, Instant.now()).getSeconds();
            sender.sendMessage("You have been logged in for : " + String.format("%d:%02d:%02d", timeElapsed / 3600, (timeElapsed % 3600) / 60, (timeElapsed % 60)));
            return true;
        }

        return false;
    }
}
