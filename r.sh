#!/bin/sh


ant clean

ant release

adb uninstall net.synergyinfosys.android.myhomeswitcher

adb install bin/MainActivity-release.apk 

adb shell am start net.synergyinfosys.android.myhomeswitcher/net.synergyinfosys.android.myhomeswitcher.MainActivity
