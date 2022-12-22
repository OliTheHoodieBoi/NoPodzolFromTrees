# PodzolCleanser

No more podzol when growing large spruce trees!  

<img alt="Large spruce tree with grass around it" src="https://github.com/OliTheHoodieBoi/PodzolCleanser/raw/main/podzolcleanser.png" height="160" />

## Enabling/disabling

The functionality of the plugin is **enabled** by default but may be disabled either [in the config file](#with-the-config-file) or with [the command](#command).

### With the config file

The default `config.yml` file looks like this:

```yml
remove-podzol: true

```

Changing `remove-podzol` from `true` to `false` podzol will not be removed from grown large spruce trees.

### With the command

You may also use the command `/removepodzol set [disabled|enabled]` to modify the config in-game.
See [Command](#command) for more details.

## Command

The command to control the plugin is `/removepodzol` (alias `/podzolcleanser`)

`/removepodzol set [enabled|disabled]`  
Enable/disable the functionality of the plugin.  
`/removepodzol reload`  
Reload the config file.  
`/removepodzol info`  
Get info about the plugin and if it is enabled or disabled.  
