<p align="center">    
    <img alt="Marvel App Logo" src="app/src/main/ic_launcher-playstore.png" width=250px/>    
</p>

# MarvelSuperHeroes 🦸‍

This repository contains the technical test to put into practice the knowledge acquired about
Android, Kotlin, Clean Architecture, Jetpack Compose, Retrofit and much more. I hope you enjoy it!

## How to run the application 🏗️

In order to test this app for the Mango team, you will need to create an apikey.properties file in
the root directory and paste the BASE_URL, API_KEY_PUBLIC and API_KEY_PRIVATE you will find in the
build config. But shhh, don´t tell this to anyone, it´s a secret 🤫  
Or you can provice your own api key from
here [Marvel docs](https://developer.marvel.com/documentation/getting_started).

Personal tip! Put your smarthphone in dark mode 😎

## Preview 📱

<p align="center">
<img width="200" src="https://github.com/AlvaroErd/MarvelSuperHeroes/assets/108676373/c5c9b471-23ca-44e8-acd3-035cbd78b88b"> <img width="200" src="https://github.com/AlvaroErd/MarvelSuperHeroes/assets/108676373/e43cc4e0-b9db-43eb-aa47-8e9d2d33a9ce"> <img width="200" src="https://github.com/AlvaroErd/MarvelSuperHeroes/assets/108676373/6441eee0-3ecd-426c-8a3a-72b65b2df24a"> <img width="200" src="https://github.com/AlvaroErd/MarvelSuperHeroes/assets/108676373/bbc9b453-313c-4985-bfbb-026cc797adfc">
</p>

<p align="center">
<img width="200" src="https://github.com/AlvaroErd/MarvelSuperHeroes/assets/108676373/81a0e9a4-5bee-4d80-8104-00dbe129467f"> <img width="200" src="https://github.com/AlvaroErd/MarvelSuperHeroes/assets/108676373/6c8b1727-ae3f-4642-b527-733f5a4f1ad6"> <img width="200" src="https://github.com/AlvaroErd/MarvelSuperHeroes/assets/108676373/47bbdc0a-647a-4c25-957b-3deae8bb2bc5"> <img width="200" src="https://github.com/AlvaroErd/MarvelSuperHeroes/assets/108676373/e0c23f9a-ea1c-435e-8780-afcda86717e8">
</p>

<div align="center">
<video width="100" src="https://github.com/AlvaroErd/MarvelSuperHeroes/assets/108676373/881934fe-0c29-4f94-bd69-d38eecb77a11">
</div>

## Built With 🔨

- [Android SDK](https://developer.android.com/) - Create an Android App
- [Kotlin](https://developer.android.com/kotlin) - Develop Android apps with Kotlin
- [Hilt](https://dagger.dev/hilt) - Dependency injection
- [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) - Coroutines
- [Flow](https://developer.android.com/kotlin/flow?hl=es-419/) - Coroutine component to implement reactive programming
- [Jetpack compose](https://developer.android.com/develop/ui/views/layout/declaring-layout) - Jetpack compose kit to build the UI
- [Navigation compose](https://developer.android.com/jetpack/compose/navigation?hl=es-419) - NavGraph for navigation
- [Android XML](https://developer.android.com/develop/ui/views/layout/declaring-layout) - XML Classic views to build the UI
- [View Binding](https://developer.android.com/topic/libraries/view-binding?hl=es-419) - Tool that makes it easier to write code that interacts with views
- [Material Design](https://m3.material.io/) - Material components for the UI
- [Paging 3.0](https://developer.android.com/topic/libraries/architecture/paging/v3-overview?hl=es-419) - Implementation of the Paging
- [Retrofit](https://square.github.io/retrofit/) - Retrofit to recive data from Internet
- [Coil](https://coil-kt.github.io/coil/) - Library to load images
- [Lottie](https://github.com/airbnb/lottie-android) - Library to load animated vectors

    

## Features 🧩

- Search Bar to filter superheroes by name (Work in Progress)
- Infinite scroll of the 1000+ marvel superheroes
- The home screen cards show a lot of information about the superhero without having to go into detail
- A striking interface with colors, fonts and shapes closely related to the Marvel universe
- A character detail screen with a lot of information where you can see his full description, a link to his database on the Marvel website and a carousel of images where you can see the comics in which he/she/it has appeared

## Architecture 🏛️

- Build entirely in Kotlin
- Clean Arquitecture with 3 layers. Data -> Domain -> App (Presentation)
- SOLID principies

## Design 🎨

- Support dark and light theme
- Designed with components from Material 3 but manually modified to follow the Marvel style
- Custom Android icon
- Custom Splash Screen
- Custom icons for the superhero properties (handmade icon of the event, comic and serie)
- Google font typography (Roc Grotesk)
- Custom theme with colors, spacing, shapes, etc to match with the Marvel universe
- Custom dimens, shapes, spacings and colors related to Material Theme
- Entry and exit animations of each view and some components
- Long texts have a displacement animation so that they can be read in full without having to go into detail

## Others 👾

- Not relevant to the user but the home screen is built with Jetpack Compose and the detail screen with classic XML view and some components with Jetpack Compose
- Custom mappers between Data and Domain
- Different mocks to show the preview with jetpack
- Custom Loading with a lottie animation
- Custom Error message
- Design with accesibility in mind (every item has a content description and great contrast)
- The texts change depending on whether they are singular or plural elements
- The date has been parsed to a cleaner and more readable format
- Invokes a function that converts both marvel api keys and gives you the hash without you having to calculate it
- Follow the Gitflow methodology to create branches, push, merge, etc.
    
## Known bugs to fix 😅

- The api call makes too many requests and exceeds the set limit. (Fixed in the branch bugfix_request (only changed a 1 to a 0 in the pagination) but not pushed to master because the error was fixed out of the technical test time)
- No message is displayed when that limit is exceeded 
- The splash screen is only compatible with android 12 onwards
- Gaussian blur of the background image in the main view is only supported on android 12 onwards
- The searchbar does not filter by name
- Not checked layout on very small screen sizes
- Maybe when there are more than 999 elements in the icons of the main view it will cut
- There is too slow a reload between Loading and UI display in the home screen
- Every time you travel back from the detail view, the view is reloaded. (Problem with Paging 3 and rememberSaveables)
- No text is selectable and could be interesting for the user
- Detail view comics are not an infinite list
- You can't zoom in on the images
- The link to the wiki of each superhero in most cases takes you to a url where the character does not exist since they have not created a record for it
- Does not support horizontal orientation

## Author 🧑‍💻

Álvaro Erdociaín Tirado  
Android Mobile developer  
E-mail: ajerdociain@hiberus.com  
[Linkedin](https://www.linkedin.com/in/alvaroerdociain)  
[Github](https://github.com/AlvaroErd?)
