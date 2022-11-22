![RespawnLimiter Logo](images/respawnlimiter-logo.png)

## Compatibility

Tested on Spigot-1.19.

## Introduction

A Minecraft (Bukkit) plugin that limits gameplay strategies based on easy player respawn.

## Features

* Each time a player dies, their max health is reduced sequentially down to a half heart.
* After surviving for 1 full game day without deaths,  the player recovers the max health to its level before the previous death, sequentially to the initial max health.

## Commands

`respawnlimiter reload` - reload config from disk
`respawnlimiter info [@player]` - show player limits
`respawnlimiter reset [@player]` - reset player limits

## Permissions

* Access to 'reload' command:
`respawnlimiter.reload` (default: op)

* Access to 'info' command:
`respawnlimiter.info` (default: op)

* Access to 'reset' command:
`respawnlimiter.reset` (default: op)

## Configuration
[Default configuration file](src/main/resources/config.yml)

## Author
I will be happy to add some features or fix bugs. My mail: uprial@gmail.com.

## Useful links
* [Project on GitHub](https://github.com/uprial/respawnlimiter/)
* [Project on Bukkit Dev](TBD)
* [Project on Spigot](TBD)

## Related projects
* CustomNukes: [Bukkit Dev](http://dev.bukkit.org/bukkit-plugins/customnukes/), [GitHub](https://github.com/uprial/customnukes), [Spigot](https://www.spigotmc.org/resources/customnukes.68710/)
* CustomCreatures: [Bukkit Dev](http://dev.bukkit.org/bukkit-plugins/customcreatures/), [GitHub](https://github.com/uprial/customcreatures), [Spigot](https://www.spigotmc.org/resources/customcreatures.68711/)
* CustomDamage: [Bukkit Dev](http://dev.bukkit.org/bukkit-plugins/customdamage/), [GitHub](https://github.com/uprial/customdamage), [Spigot](https://www.spigotmc.org/resources/customdamage.68712/)
* CustomRecipes: [Bukkit Dev](https://dev.bukkit.org/projects/custom-recipes), [GitHub](https://github.com/uprial/customrecipes/), [Spigot](https://www.spigotmc.org/resources/customrecipes.89435/)
* CustomVillage: [Bukkit Dev](http://dev.bukkit.org/bukkit-plugins/customvillage/), [GitHub](https://github.com/uprial/customvillage/), [Spigot](https://www.spigotmc.org/resources/customvillage.69170/)
