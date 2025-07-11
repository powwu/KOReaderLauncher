# KOReaderLauncher

A simple Android app that redirects the user to KOReader upon opening. It treats itself as a launcher, meaning you can replace your usual home screen with an automatic redirect.

To prevent users from locking themselves out of their real home screens, a 5 second delay is imposed before the redirect. Tapping the screen during this time will take you to the settings page for home screen app selection, letting you easily change back.

**You can download an apk [here]().**

## Known issues
- Only supports FDroid version of KOReader
- May fail to open when "app freezing" is in effect (default on BOOX devices; be sure to turn this off for both KOReader AND the launcher!)
