<p align="center">
    <img alt="Marvel App Logo" src="app/src/main/ic_launcher-playstore.png" width=250px/>
</p>

# MarvelSuperHeroes 🦸‍

This repository contains the technical test to put into practice the knowledge acquired about Android, Kotlin, Clean Architecture, Jetpack Compose, Retrofit and much more. I hope you enjoy it!

## How to run the application 🏗️

As long as this application is kept with moderate use, I have added my own apikey and private apikey in the gradle.properties file.

If you don't find it added, you can put your own api key and private api key with the following format:

API_KEY_PUBLIC=11111111111
API_KEY_PRIVATE=222222222222222

You can provide your own api key from
here [Marvel docs](https://developer.marvel.com/documentation/getting_started).

Personal tip! Check the app in dark mode 😎

## Preview 📱

<p align="center">
<img width="200" src="https://github.com/AlvaroErd/MarvelSuperHeroes/assets/108676373/c5c9b471-23ca-44e8-acd3-035cbd78b88b"> <img width="200" src="https://github.com/AlvaroErd/MarvelSuperHeroes/assets/108676373/e43cc4e0-b9db-43eb-aa47-8e9d2d33a9ce"> <img width="200" src="https://github.com/AlvaroErd/MarvelSuperHeroes/assets/108676373/6441eee0-3ecd-426c-8a3a-72b65b2df24a"> <img width="200" src="https://github.com/AlvaroErd/MarvelSuperHeroes/assets/108676373/bbc9b453-313c-4985-bfbb-026cc797adfc">
</p>

<p align="center">
<img width="200" src="https://github.com/AlvaroErd/MarvelSuperHeroes/assets/108676373/81a0e9a4-5bee-4d80-8104-00dbe129467f"> <img width="200" src="https://github.com/AlvaroErd/MarvelSuperHeroes/assets/108676373/6c8b1727-ae3f-4642-b527-733f5a4f1ad6"> <img width="200" src="https://github.com/AlvaroErd/MarvelSuperHeroes/assets/108676373/47bbdc0a-647a-4c25-957b-3deae8bb2bc5"> <img width="200" src="https://github.com/AlvaroErd/MarvelSuperHeroes/assets/108676373/e0c23f9a-ea1c-435e-8780-afcda86717e8">
</p>

<div align="center">
<video width="100" src="https://github.com/AlvaroErd/MarvelSuperHeroes/assets/108676373/881934fe-0c29-4f94-bd69-d38eecb77a11">
</video>
</div>

## Built With 🔨

- [Android SDK](https://developer.android.com/) - Create an Android App
- [Kotlin](https://developer.android.com/kotlin) - Develop Android apps with Kotlin
- [Hilt](https://dagger.dev/hilt) - Dependency injection
- [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) - Coroutines
- [Flow](https://developer.android.com/kotlin/flow?hl=es-419/) - Coroutine component to implement
  reactive programming
- [Jetpack compose](https://developer.android.com/develop/ui/views/layout/declaring-layout) -
  Jetpack compose kit to build the UI
- [Navigation compose](https://developer.android.com/jetpack/compose/navigation?hl=es-419) -
  NavGraph for navigation
- [Material Design](https://m3.material.io/) - Material components for the UI
- [Room](Desarrolladores de Android &nbsp;|&nbsp; Android Developers](https://developer.android.com/jetpack/androidx/releases/room) - A persistence library that provides an abstraction layer over SQLite
- [Paging 3.0](https://developer.android.com/topic/libraries/architecture/paging/v3-overview?hl=es-419) - Implementation of the Paging
- [Retrofit](https://square.github.io/retrofit/) - Retrofit to receive data from Internet
- [Coil](https://coil-kt.github.io/coil/) - Library to load images
- [Lottie](https://github.com/airbnb/lottie-android) - Library to load animated vectors

## Features 🧩

- An onboarding page describing the purpose of the app
- Search Bar to filter superheroes by name
- Infinite scroll of the 1000+ marvel superheroes
- The home screen cards show a lot of information about the superhero without having to go into detail
- A striking interface with colors, fonts and shapes closely related to the Marvel universe
- A character detail screen with a lot of information where you can see his full description, a button to see the image in full screen an a carousel of images where you can see the comics in which he/she/it has appeared
- The app can storage the data in his own database so you can use it without internet
- There is an easter egg. Try to find it! Hint: "The magic number is 5: five clicks lead to glory in the world of superheroes."

## Architecture 🏛️

- Build entirely in Kotlin
- Clean Architecture with 5 layers. Datasource -> Data -> Domain <- App ( Presentation) and Model. There are no modules but each layer is intended to depend only on the corresponding one.
- SOLID principles

## Design 🎨

- Support dark, light and auto theme with a dedicated button
- Designed with components from Material 3 but manually modified to follow the Marvel style
- Custom Android icon
- Custom Splash Screen
- Custom icons for the superhero properties (handmade icon of the event, comic and serie)
- Google font typography (Roc Grotesk)
- Custom theme with colors, spacing, shapes, etc to match with the Marvel universe
- Custom dimens, shapes, spacings and colors related to Material Theme
- Entry and exit animations of each view and some components
- Long texts have a displacement animation so that they can be read in full without having to go into detail
- A library to show the error capture in logcat inside the app, only for develop pourposes
- A bunch of composables as a private repository

## Others 👾

- Fully build in jetpack compose
- Custom mappers between RemoteModel, CacheModel and DomainModel
- Different mocks to show the preview with jetpack
- Custom Loading with a lottie animation
- Custom Error message
- Design with accessibility in mind (every item has a content description and great contrast)
- The texts change depending on whether they are singular or plural elements
- The date has been parsed to a cleaner and more readable format
- Invokes a function that converts both marvel api keys and gives you the hash without you having to calculate it
- Follow the Gitflow methodology to create branches, push, merge, etc.
- The app has differents names and icons depend on the build variant. (Debug = DEBUG Marvel SuperHeroes with the logo in orange and Release = Marvel SuperHeroes with the logo in red)
- The app shows when you lost internet and when you recover it.
- The models are parcelable

## Known bugs to fix 😅

- ~~The api call makes too many requests and exceeds the set limit~~ (Fixed)
- ~~The splash screen is only compatible with android 12 onwards~~ (Fixed)
- ~~Gaussian blur of the background image in the main view is only supported on android 12 onwards~~ (Fixed)
- ~~The searchbar does not filter by name~~ (Fixed)
- ~~Maybe when there are more than 999 elements in the icons of the main view it will cut~~ (Fixed)
- ~~There is too slow a reload between Loading and UI display in the home screen~~ (Fixed)
- ~~Every time you travel back from the detail view, the view is reloaded. (Problem with Paging 3 andrememberSaveables)~~ (Fixed)
- ~~No text is selectable and could be interesting for the user~~ (Fixed)
- ~~Detail view comics are not an infinite list~~ (Fixed)
- ~~You can't zoom in on the images~~ (Fixed)
- Does not support horizontal orientation (In consideration)

## License ⚖️

[MIT License](License.txt)

## Author 🧑‍💻

Álvaro Erdociaín Tirado
Android Mobile developer
E-mail: ajerdociain@hiberus.com
[Linkedin](https://www.linkedin.com/in/alvaroerdociain)
[Github](https://github.com/AlvaroErd?)
