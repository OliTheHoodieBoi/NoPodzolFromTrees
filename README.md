# No Podzol from Trees [![Modrinth Game Versions](https://img.shields.io/modrinth/game-versions/no-podzol-from-trees?style=flat-square&logo=modrinth&color=17b85a)](https://modrinth.com/plugin/no-podzol-from-trees)

A tiny Bukkit plugin that removes the podzol that normally appears when growing large spruce trees.  

<img alt="Large spruce tree with grass around it." src="https://github.com/OliTheHoodieBoi/PodzolCleanser/raw/main/icon.png" width="160" height="160" />

## Enabling/disabling

The functionality of the plugin is **enabled** by default but may be disabled either [in the config file](#with-the-config-file) or with [the command](#command).  

### With the config file

The default `config.yml` file looks like this:  

```yml
remove-podzol: true

```

Changing `remove-podzol` from `true` to `false` podzol will not be removed from grown large spruce trees.  

### With the command

You may also use the command `/npfl [enable|disable]` to modify the config in-game.
See [Command](#command) for more details.  

## Command

The command to control the plugin is `/npfl` (alternatively `/nopodzol`)

`/npfl enable`  
Enable the functionality of the plugin. Podzol will not appear when growing large spruce trees.  
`/npfl disable`  
Disable the functionality of the plugin. Podzol will appear when growing large spruce trees.  
`/npfl reload`  
Reload the config file.  
`/npfl info`  
Get info about the plugin and if it is enabled or disabled.  
