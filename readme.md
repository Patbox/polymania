<center>

![Title Image](https://cdn.modrinth.com/data/gDvfyRyQ/images/54b3a884e008d5922c05eb1e6e4c0fd59f1066ce.png)

</center>

# Welcome to Polymania!
Polymania is a kitchen-sinky, Fabric Server-Side modpack, which main goal is to provide the classic modded experience, while being easy to install and use.
It's ideal for server you want to play on with your friends, as the only thing needed is a place to host it and vanilla Minecraft client!

This modpack is contains:
- A full on factory/automation with machinery powered by rotation and logic cables, provided by [PolyFactory](https://modrinth.com/mod/polyfactory) mod!
- Many new decorative and building blocks blocks for builders from furniture to new bricks and stones! Provided by [PolyDecorations](https://modrinth.com/mod/polydecorations), [(modified) Blockus](https://modrinth.com/mod/blockus) and others.
- New biomes and structures, thanks to mods and datapacks such as [Cliff under a Tree](https://modrinth.com/datapack/clifftree), [(modified) Traverse](https://modrinth.com/mod/traverse), [(modified) Cinderscape](https://modrinth.com/mod/cinderscape) and more.
- New mobs, friendly and aggresive, by [Tom's Mobs](https://modrinth.com/mod/toms-mobs) and [Illager Expansion Recrafted](https://modrinth.com/mod/illager-expansion-polymer)
- Programmable computers from [ComputerCraft](https://modrinth.com/mod/cc-tweaked), modified to work server side.
- New plants and food types, provided by [Farmer's Delight](https://modrinth.com/mod/farmers-delight-refabricated) and others.

- And more!

![](https://cdn.modrinth.com/data/gDvfyRyQ/images/ab761c937dbc68f334549d61b9cae809266b90b8.png)

## Installation.
Modpack is available on [Modrinth](https://modrinth.com/project/polymania), but can be easily installed on any hosting service that supports custom jars.
Naturally it can also be installed on Modrinth Servers using it's builtin modpack installer.
### Provided installer (Recommended).
Every release of a modpack provides extra file named `server.jar`. Just download it and run it like any other Minecraft server ([see MC wiki for running it locally](https://minecraft.wiki/w/Tutorials/Setting_up_a_server), or follow your server hosting tutorial about using custom jars). Only thing you will need to do is to accept Minecraft's eula.
### Using mrpack4server.
You can use [mrpack4server](https://github.com/Patbox/mrpack4server) just as it's described in it's readme. 
Officially provided `server.jar` uses pre-configured version of it set to download the pack, so there should be no functionality difference aside of having to set it up manually.
### Other methods.
Other known methods of installing modrinth modpacks include [mrpack-installer](https://github.com/nothub/mrpack-install), [itzg/minecraft-server docker image](https://docker-minecraft-server.readthedocs.io/en/latest/) or any other compatible installer, through I wouldn't suggest using them for non-technical users.

![](https://cdn.modrinth.com/data/gDvfyRyQ/images/98a6321296ba2acc411320d5796bb1a13d36ec6f.png)

## Faq
### Can I check all recipes somehow? Also can I hide the block names on top of the screen!
Yes! You can use `/polydex` command to show all available item recipes. Block/Entity names on top of the screen are also
handled by the Polydex. [You can read more about it here!](https://modded.wiki/w/Mod:Polydex)

### Can I run this modpack on the client/singleplayer?
It might work, but it's not as tested as running this pack on a server. At least for versions 0.3.0-beta.3 and newer it should work.
Older versions will be missing mods, if not crash outright. Try for yourself.

### Can I use Geyser with this?
Currently? Sadly not. Due to many differences between how java and bedrock handle many technical / datapack / resourcepack features, this modpack won't work correctly with Geyser users. So even if they managed to join, they likely won't see textures and models correctly, while also not being able to use some items and blocks at all.

### Does ViaVersion / ViaBackwards work with this?
Heavily depends on versions you use. Generally it might work in some cases, where enough things didn't change, but at least with older versions things will work worse if not at all in case it's missing required behaviour/feature. In case of newer version, it's possible some things won't work due to format / display changes in Minecraft resource pack features, through it should be way more compatible.

![](https://cdn.modrinth.com/data/gDvfyRyQ/images/6cb0af3de306e758ef233f1d13fbea40181c06c5.png)

## Modified mods, included assets and licenses.
Polymania uses forks of mods, which source code/changes you can see check at [PolymaniaForks github page](https://github.com/PolymaniaForks).

Additionally it includes:
- It includes port of [Dark Paintings](https://modrinth.com/mod/dark-paintings) as a datapack/resourcepack combo, which you can find under `config/openloader/data/DarkPaintings.zip`.
- Copy of [Cinderscapes](https://modrinth.com/mod/cinderscapes) music by LudoCrypt, compressed so it takes less space in the server resource pack. You can find it under `polymer/override_assets/assets/cinderscapes`.
- Fork/Development version of [PolyMc by TheEpicBlock](https://github.com/TheEpicBlock/PolyMc) and [PolyConfig by TheEpicBlock](https://github.com/TheEpicBlock/PolyConfig), a translation layer without which this modpack would never exist, which source you can find on GitHub: [PolyMc](https://github.com/Patbox/polymc), [PolyConfig](https://github.com/TheEpicBlock/PolyConfig/pull/6). You can find them in `mods/PolyMc-(ver).jar` and `mods/polyconfig-(ver).jar`.
- Modified version of Moss Carpet Overhang resource pack form [Vanilla Tweaks](https://vanillatweaks.net/), making it cull more when placed on sides of other carpets/blocks. You can find it in `polymer/override_assets/assets/minecraft/(block|item)/moss_carpet.json` and `polymer/override_assets/assets/minecraft/textures/block/moss_carpet_overlay.png`
- [Unsanded by unascribed](https://modrinth.com/resourcepack/unsanded), modified to exist as it's own font instead of overriding vanilla. You can find it in `polymer/extra/Unsanded-1.1.1-custom.zip`.
- [Lato Regular Font](https://www.latofonts.com/lato-free-fonts/) as an extra usable font. You can find it in `polymer/override_assets/assets/polymania/font/lato`.
- [Tweemoji](https://github.com/jdecked/twemoji) as emoji's used with Styled Chat. You can find it in `polymer/override_assets/assets/polymania/textures/emoji`.

You can see the license of these files on provided websites.

Polymania Extras mod (under `/mod/` in repo) is provided under [LGPLv3](https://github.com/Patbox/polymania/blob/master/mod/LICENSE)
Other files included in modpack are provided under [CC BY-NC 4.0](https://creativecommons.org/licenses/by-nc/4.0/).