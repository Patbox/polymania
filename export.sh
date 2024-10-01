#!/bin/bash
VER="$(cat version.txt)"
echo $VER

VERSION_FILE=("pack.toml" "config/simpleupdatechecker_modpack.json")

rm -r export
mkdir export
cd pack
packwiz refresh

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

cd ../export
java -cp ../util/mrpack4server.jar eu.pb4.mrpackserver.Create --project_id polymania --display_name Polymania --version_id $VER