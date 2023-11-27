# TGeniusLobby
## Minecraft Paper plugin for /lobby

## Dependencies:
Multiverse-Core (https://dev.bukkit.org/projects/multiverse-core)


The plugin has an extra support of CombatLogX
(https://www.spigotmc.org/resources/combatlogx.31689/)



## usage:
#### /lobby has aliaces: /lobby, /hub, /spawn - all of them do the same thing

/lobby - teleport player to the spawn
/lobby set {world} - set the destination of the /lobby command. IMPORTANT that world has to be at the Multiverse-Core
/lobby other {player} - teleport other player to the lobby

## permissions:

`tgeniuslobby.lobby.self` - using /lobby to teleport yourself
`tgeniuslobby.lobby.others` - using /lobby other {player} to teleport other player
`tgeniuslobby.lobby.usage_while_in_combat` - relevant only with CombatLogX. Can player use the command while he is in a combat.
`tgeniuslobby.lobby.set_lobby` - using /lobby set {world} to change the lobby location
`tgeniuslobby.lobby.bypass_autoteleport` - will the player be teleported at the lobby every joining





