cd mod
./gradlew build
cd ..
rm pack/mods/polymania-extras-*
rm mod/build/libs/polymania-extras-*-sources.jar
cp mod/build/libs/polymania-extras-*.jar pack/mods/