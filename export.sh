#!/bin/bash
VER="$(cat version.txt)"
echo $VER

VERSION_FILE=("pack.toml" "config/simpleupdatechecker_modpack.json", "resources/common/required/polymania/data/polymania/dialog/changelog.json")

./updatemod.sh

rm -r export
mkdir export
cd pack
packwiz refresh

mv "config/simpleupdatechecker_modpack.json" "config/simpleupdatechecker_modpack.json.dev.bkp"
mv "config/simpleupdatechecker_modpack.beta.json" "config/simpleupdatechecker_modpack.beta.json.bkp"
mv "config/simpleupdatechecker_modpack.release.json" "config/simpleupdatechecker_modpack.release.json.bkp"

if [[ $VER =~ "-" ]]; then
  echo "Beta version, using beta update checker config"
  cp "config/simpleupdatechecker_modpack.beta.json.bkp" "config/simpleupdatechecker_modpack.json"
else 
  cp "config/simpleupdatechecker_modpack.release.json.bkp" "config/simpleupdatechecker_modpack.json"
fi

for FILE in ${VERSION_FILE[*]}
do
	echo "Modified $FILE"
	cp "$FILE" "$FILE.bkp"
	sed -i "s/<MODPACKVERSION>/$VER/g" "$FILE"
done

packwiz mr export --output ../export/polymania.mrpack

for FILE in ${VERSION_FILE[*]}
do
	rm "$FILE"
	mv "$FILE.bkp" "$FILE"
	echo "Restored $FILE"
done

rm "config/simpleupdatechecker_modpack.json"

mv "config/simpleupdatechecker_modpack.json.dev.bkp" "config/simpleupdatechecker_modpack.json"
mv "config/simpleupdatechecker_modpack.beta.json.bkp" "config/simpleupdatechecker_modpack.beta.json"
mv "config/simpleupdatechecker_modpack.release.json.bkp" "config/simpleupdatechecker_modpack.release.json"
packwiz refresh
cd ../