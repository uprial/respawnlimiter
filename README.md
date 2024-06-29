![RespawnLimiter Logo](images/respawnlimiter-logo.png)

## Warning: I froze this plugin due to lack of popularity.

## Compatibility

Tested on Spigot-1.19.

## Introduction

A Minecraft (Bukkit) plugin that limits gameplay strategies based on easy player respawn.

## Features

* Each time a player dies, their max health is scaled down sequentially to a half heart. 
* The player can recover from the last scale-down via 1 day of surviving or 10 mob kills.
* The survival period and the number of mob kills can be configured.

#### You can configure:
* Whether to show messages about max life changes via the "verbose" flag in the configs.

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
* [Project on Bukkit Dev](https://www.curseforge.com/minecraft/bukkit-plugins/respawn-limiter)
* [Project on Spigot](https://www.spigotmc.org/resources/respawnlimiter.106469/)

## Related projects
* CustomCreatures: [Bukkit Dev](http://dev.bukkit.org/bukkit-plugins/customcreatures/), [GitHub](https://github.com/uprial/customcreatures), [Spigot](https://www.spigotmc.org/resources/customcreatures.68711/)
* CustomDamage: [Bukkit Dev](http://dev.bukkit.org/bukkit-plugins/customdamage/), [GitHub](https://github.com/uprial/customdamage), [Spigot](https://www.spigotmc.org/resources/customdamage.68712/)
* CustomNukes: [Bukkit Dev](http://dev.bukkit.org/bukkit-plugins/customnukes/), [GitHub](https://github.com/uprial/customnukes), [Spigot](https://www.spigotmc.org/resources/customnukes.68710/)
* CustomRecipes: [Bukkit Dev](https://dev.bukkit.org/projects/custom-recipes), [GitHub](https://github.com/uprial/customrecipes/), [Spigot](https://www.spigotmc.org/resources/customrecipes.89435/)
* CustomVillage: [Bukkit Dev](http://dev.bukkit.org/bukkit-plugins/customvillage/), [GitHub](https://github.com/uprial/customvillage/), [Spigot](https://www.spigotmc.org/resources/customvillage.69170/)
* NastyIllusioner: [Bukkit Dev](https://legacy.curseforge.com/minecraft/bukkit-plugins/nastyillusioner), [GitHub](https://github.com/uprial/nastyillusioner), [Spigot](https://www.spigotmc.org/resources/nastyillusioner.109715/)
* TakeAim: [Bukkit Dev](https://dev.bukkit.org/projects/takeaim), [GitHub](https://github.com/uprial/takeaim), [Spigot](https://www.spigotmc.org/resources/takeaim.68713/)
