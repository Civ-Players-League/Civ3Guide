# Civ3Guide
This project is for a mobile game/guide for
[Sid Meier's Civilization III](https://store.steampowered.com/app/3910/Sid_Meiers_Civilization_III_Complete/).

If you want to get early access to try out the app, you can join the open beta:
  - [Android](https://play.google.com/apps/testing/com.sixbynine.civ3guide.android)
  - [iOS](https://testflight.apple.com/join/4b51LF40)
  
## Project architecture
This project uses [Kotlin Multiplatform](https://kotlinlang.org/lp/mobile/), which allows for significant 
code sharing between Android and iOS. The common Kotlin code is run natively in the Android app, and gets
transpiled to Objective-C and included as a linked framework for iOS.

[MokoResources](https://github.com/icerockdev/moko-resources) is used to have common image and string
resources. However, some strings are defined directly in the source code as it's highly unlikely that this
project will be translated.

The UI layer is written using [SwiftUI](https://developer.apple.com/xcode/swiftui/) for iOS and traditional
Android Views. When 
[Jetpack Compose](https://developer.android.com/jetpack/compose?gclid=Cj0KCQiAx9mABhD0ARIsAEfpavSaW5x_1N7wXt3mapvZV4ewSJDFa824PDIbnZ95iZfODYLKkT6tE6YaArrHEALw_wcB&gclsrc=aw.ds)
is more stable, I may rewrite the Android UI to use that framework.

## Building and running the code
You can edit the common/android code and run the Android app using Android Studio. Follow the steps 
[here](https://kotlinlang.org/docs/mobile/setup.html) to set up your environment. You do not need to have
a mac if you're only working on and running the Android or shared code.

To work on the iOS app, you can use XCode or AppCode. **To access the project, open
`/iosApp/civ3guide.xcodeproject`, not the project root folder.** The project should work like a regular
XCode project and if you've made a change to shared code, the build phase will cause the linked framework
to recompile.

## Contributing
Anyone is welcome to contribute to the project, feel free to send me pull requests.

## About Civ III
[Civilization III](https://en.wikipedia.org/wiki/Civilization_III) is a strategy game originally released 
in 2001. Some links:
  - [Steam](https://store.steampowered.com/app/3910/Sid_Meiers_Civilization_III_Complete/)
  - [Multiplayer league](https://civplayersciv3league.com)
  - [Suede's YouTube channel](https://www.youtube.com/channel/UCvJNJ8HF5BWrErL-RpvqbYQ), which has good tips, 
  guides, and playthroughs
