# 0.3.0-beta.3
- Updated to 1.21.5
- TEMP: Removed Blahaj Polymerized
- TEMP: Removed Televator
- Moved from PolymaniaFork updated Go Fish! to Gone Fishing! (Functionally the same, aside it being polymer mod).
- Removed ViaFabric and ViaVersion:
  - No longer needed.
- Removed Necronomicon API (it wasn't used anyway)
  

# 0.3.0-beta.2
- Added ViaVersion and ViaFabric:
  - Servers running this modpack should now be joinable from 1.21.5.
  - While things should generally work, it's possible there might be some bugs.
  - Once enough mods update to 1.21.5, Polymania will move fully to it.
- Added Blahaj Polymerized: https://modrinth.com/mod/blahaj-polymerized
- Updated PolyFactory (0.6.1+1.21.4 -> 0.6.3+1.21.4): https://modrinth.com/mod/polyfactory/version/0.6.2+1.21.4, https://modrinth.com/mod/polyfactory/version/0.6.3+1.21.4
- Updated Patbox's Brewery (0.10.2+1.21.4 -> 0.10.3+1.21.4): https://modrinth.com/mod/brewery/version/0.10.3+1.21.4
- Updated Nightosphere (1.3 -> 2.0): https://modrinth.com/datapack/nightosphere/version/2.0
- Updated Cliff under a Tree (2.0.1 -> 2.0.3): https://modrinth.com/datapack/clifftree/version/2.0.2
- Updated Polydex (1.4.1+1.21.4 -> 1.4.2+1.21.4): https://modrinth.com/mod/polydex/version/1.4.2+1.21.4
- Updated Glide Away! (1.4.0+1.21.4-rc3 -> 1.4.1+1.21.4): https://modrinth.com/mod/glide-away/version/1.4.1+1.21.4
- Updated Toms Mobs (2.1.4+1.21.4 -> 2.2.0+1.21.4): https://modrinth.com/mod/toms-mobs/version/2.2.0+1.21.4
- Updated This Rocks! (1.9.1 -> 1.9.3)
- Updated Braver Bundles (1.21.4-1.0.3 -> 1.21.4-1.0.5)
- Updated Baby Fat - Polymerized (1.0.0+1.21.4 -> 1.0.1+1.21.4)
- Updated FSit (2.7.5-beta.1+mc1.21.2 -> 2.7.6+mc1.21.2)
- Updated Polymer (0.11.6+1.21.4 -> 0.11.8+1.21.4)
- Updated Lithium (0.14.7+mc1.21.4 -> 0.15.1+mc1.21.4)
- Updated MidnightLib (1.6.8-fabric+1.21.4 -> 1.6.10-fabric+1.21.4)
- Updated Balm (1.21.4-21.4.12 -> 1.21.4-21.4.23)
- Updated Architectury API (15.0.2-fabric -> 15.0.3-fabric)
- Updated Fabric API (0.117.0+1.21.4 -> 0.119.2+1.21.4)
- Updated Fabric Language Kotlin (1.13.1+kotlin.2.1.10 -> 1.13.2+kotlin.2.1.20)


# 0.3.0-beta.1
- Updated to 1.21.4
- Updated ALL mods. Won't put them all here as it's just too many.
- Warning: Old worlds aren't fully compatible yet!
  - Due to some content mods being removed (at least temporarely) old (0.2.1 and older) worlds won't work.
  - If you didn't engage with them or don't mind losing them, you should still make sure to backup everything before updating!
  - Old, missing items and blocks will be converted into existing blocks, to try preserving buildings somewhat.
- Added Braver Bundles.
- Added links to modpack's Modrinth page, bug reporting page and discord (in Server Links).
  - Provided via Simple Server Links.
- Removed content mods:
  - CC: Tweaked - not on 1.21.4
  - Borukva Food - not on 1.21.4
- Removed util mods:
  - Polymer Patch for CC: Tweaked - requires CC: Tweaked
  - PolydexBridge - EMI isn't on 1.21.4 yet
  - Mod Viewer - not on 1.21.4
- Removed not needed/replaced mods:
  - Crash Exploit Fixer - Fixed in vanilla
  - Anti Dupe - Fixed in vanilla
  - Boat Break Fix - Fixed in vanilla
  - ViaFabric / ViaVersion - Already on latest
  - Server Ui Fix - Better fix implemented in Polymer
  - This Rocks Polymer - Merged into This Rocks!


# 0.2.1
- You can now join the modpack server with 1.21.2/3.
  - While it should faily playable, there might be some issues/bugs related to that, as features/behaviour between these versions have some differences. 
  - For best experience, you should play on 1.21.1 client, until the Polymania fully moves to 1.21.3 (with 0.3.0 update).
  - Some known issues are:
	- Walking on all blocks sounds like stone.
	- ComputerCraft mouse movement is broken.
	- Some display-entity blocks don't display correctly when upside down (e.g. Fans and Portable Fluid Tanks).
	- Some items have incorrect names and drinking/eating visuals (e.g. Brewery Brews).
	- Platinum Infused Netherite Armor doesn't have correct texture when worn.
  - They might get fixed in meantime in later patch, as ViaVersion gets issues fixed.
- Added ViaFabric + ViaVersion.
- Updated Armor Stand Editor (2.5.0 -> 2.5.1)
- Updated Toms Mobs (2.0.2 -> 2.1.2+1.21)
- Updated Leaves Us In Peace (1.6.0 -> 1.6.1)
- Updated Open Sesame (1.0.0 -> 1.0.1)
- Updated FSit (2.5.2 -> 2.6.0)
- Updated Polymer (0.9.16 -> 0.9.17)
- Updated Simple Update Checker (1.0.0 -> 1.0.1)
- Updated FerriteCore (7.0.0 -> 7.0.2-hotfix)
- Updated Fabric API (0.105.0 -> 0.107.0)
- Updated TerraBlender (4.1.0.3 -> 4.1.0.4)
- Updated Balm (1.21.1 -> 1.21.1)
- Updated Fabric Language Kotlin (1.12.2+kotlin.2.0.20 -> 1.12.3+kotlin.2.0.21)


# 0.2.0
- Changed texture and recipe of Televator. Now it's styled more like PolyFactory blocks.
- Fixed seeds from Borukva Food not being plantable with Mechanical Planter
- Modpack version is now visible on the tablist header. Additionally all future modpack updates will be announced for ops and in logs/console.
- Added Vaulted End (1.0.0): https://modrinth.com/mod/vaulted-end
- Added Go Fish (1.6.3+1.21 / PolymaniaFork): https://github.com/PolymaniaForks/go-fish
- Added Extended Drawers (3.0.4): https://modrinth.com/mod/extended-drawers
- Added Polymer Patch Extended Drawers (3.0.4.0 / as included jar): https://github.com/PolymerPorts/extended-drawers-patch
- Added Open Sesame (1.0.0): https://modrinth.com/mod/open-sesame
- Added Leaves Us In Peace (1.6.0): https://modrinth.com/mod/leaves-us-in-peace
- Added Simple Resource Loader (1.0.0): https://modrinth.com/mod/simple-resource-loader
- Added Simple Update Checker (1.0.0): https://modrinth.com/mod/simple-update-checker
- Updated PolyDecorations (0.4.2 -> 0.4.4): https://modrinth.com/mod/polydecorations/version/0.4.3+1.21.1
- Updated Dungeons and Taverns (4.3 -> 4.4.4): https://modrinth.com/datapack/dungeons-and-taverns/version/oiBH7RGx
- Updated Borukva Food (0.1.4 -> 0.1.5): https://modrinth.com/mod/borukva-food/version/0.1.5
- Updated Banner Text (1.2.0 -> 1.3.2): https://modrinth.com/datapack/banner-text/version/v1.3.0+mod
- Updated CC: Tweaked (1.113.0 -> 1.113.1): https://github.com/cc-tweaked/CC-Tweaked/releases/tag/v1.21.1-1.113.1
- Updated Explorify (1.6.1 -> 1.6.2): https://modrinth.com/datapack/explorify/version/1.6.2
- Updated Toms Mobs:(2.0.1 -> 2.0.2): https://modrinth.com/mod/toms-mobs/version/2.0.2+1.21
- Updated Polydex (1.2.3 -> 1.2.4): https://modrinth.com/mod/polydex/version/1.2.4+1.21.1
- Updated Fabric API (0.103.0 -> 0.105.0)
- Updated Polymer Patch for CC:Tweaked (1.113.0.0 -> 1.113.1.0)
- Updated Lithium (0.13.0 -> 0.13.1)
- Updated Puzzles Lib (21.0.28 -> 21.1.10)
- Updated Forge Config API Port (21.1.0 -> 21.1.1)
- Updated Necronomicon API (1.5.0 -> 1.6.0)
- Updated Cloth Config (15.0.130 -> 15.0.140)
- Updated Leaves Be Gone (21.0.0 -> 21.1.0)
- Updated Polymer (0.9.14 -> 0.9.16)
- Updated Spark (1.10.97 -> 1.10.109)
- Updated Translations for Patbox's Mods (2024.08.29 -> 2024.09.17)
- Updated Fabric Language Kotlin (1.12.1+kotlin.2.0.20 -> 1.12.2+kotlin.2.0.20)
- Updated PolyMc (5.7.0-rev.7774f29 -> 5.7.0-rev.188d8ee)
- Removed Double Doors, Collective, Leaves be Gone, Puzzles Lib, OpenLoader.
- Moved all included datapacks from `config/openloader/data` to `resources/common/required`.

# 0.1.0
- Initial Release!