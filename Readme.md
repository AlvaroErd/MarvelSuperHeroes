<p align="center">
    <img alt="Marvel App Logo" src="app/src/main/ic_launcher-playstore.png" width=250px/>
</p>

# MarvelSuperHeroes 🦸‍

This repository contains the technical test to put into practice the knowledge acquired about Android, Kotlin, Clean Architecture, Jetpack Compose, Retrofit and much more. I hope you enjoy it!

## Preview 📱

![Marvel Superheroes App presentation](https://private-user-images.githubusercontent.com/108676373/291011793-a56abf5f-8efd-4744-8fa3-d015e99e5697.jpg?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTEiLCJleHAiOjE3MDI3NTQ3MTcsIm5iZiI6MTcwMjc1NDQxNywicGF0aCI6Ii8xMDg2NzYzNzMvMjkxMDExNzkzLWE1NmFiZjVmLThlZmQtNDc0NC04ZmEzLWQwMTVlOTllNTY5Ny5qcGc_WC1BbXotQWxnb3JpdGhtPUFXUzQtSE1BQy1TSEEyNTYmWC1BbXotQ3JlZGVudGlhbD1BS0lBSVdOSllBWDRDU1ZFSDUzQSUyRjIwMjMxMjE2JTJGdXMtZWFzdC0xJTJGczMlMkZhd3M0X3JlcXVlc3QmWC1BbXotRGF0ZT0yMDIzMTIxNlQxOTIwMTdaJlgtQW16LUV4cGlyZXM9MzAwJlgtQW16LVNpZ25hdHVyZT05YWUyMGY2YzE4MTk0NWQ2ZjZmZGFiZGY5NDYzYTI4M2VjZTA5MjBmMmJlN2JhMTdiM2Y2YjMwN2ZhYTAyMTUwJlgtQW16LVNpZ25lZEhlYWRlcnM9aG9zdCZhY3Rvcl9pZD0wJmtleV9pZD0wJnJlcG9faWQ9MCJ9.zDl7Q94roxlCXF9C2Tdr4cbGNGQ-riiayJy67E6uqR0)

<br>

![Marvel Superheroes App functions](https://private-user-images.githubusercontent.com/108676373/291011592-87e42fb7-a159-4166-ac4d-85f7713c765e.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTEiLCJleHAiOjE3MDI3NTQ4NzIsIm5iZiI6MTcwMjc1NDU3MiwicGF0aCI6Ii8xMDg2NzYzNzMvMjkxMDExNTkyLTg3ZTQyZmI3LWExNTktNDE2Ni1hYzRkLTg1Zjc3MTNjNzY1ZS5wbmc_WC1BbXotQWxnb3JpdGhtPUFXUzQtSE1BQy1TSEEyNTYmWC1BbXotQ3JlZGVudGlhbD1BS0lBSVdOSllBWDRDU1ZFSDUzQSUyRjIwMjMxMjE2JTJGdXMtZWFzdC0xJTJGczMlMkZhd3M0X3JlcXVlc3QmWC1BbXotRGF0ZT0yMDIzMTIxNlQxOTIyNTJaJlgtQW16LUV4cGlyZXM9MzAwJlgtQW16LVNpZ25hdHVyZT0zMDIwNGVjNjVkZjY5NDNlMzllNmEyY2E4ODA0NTY4YjI2YTYxMGNmMTg3ZGIzMGUxZTc4NWYzNzE0ZWViOWY3JlgtQW16LVNpZ25lZEhlYWRlcnM9aG9zdCZhY3Rvcl9pZD0wJmtleV9pZD0wJnJlcG9faWQ9MCJ9.C6zkgVDeHH0C-byiKA6jeUTZp41Nw08L2lz0hMQ9iPQ)

<br>

![Marvel Superheroes App theme modes](https://private-user-images.githubusercontent.com/108676373/291011590-b6e62daf-b9cc-4607-8a7e-ce11a04c3569.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTEiLCJleHAiOjE3MDI3NTQ4NzIsIm5iZiI6MTcwMjc1NDU3MiwicGF0aCI6Ii8xMDg2NzYzNzMvMjkxMDExNTkwLWI2ZTYyZGFmLWI5Y2MtNDYwNy04YTdlLWNlMTFhMDRjMzU2OS5wbmc_WC1BbXotQWxnb3JpdGhtPUFXUzQtSE1BQy1TSEEyNTYmWC1BbXotQ3JlZGVudGlhbD1BS0lBSVdOSllBWDRDU1ZFSDUzQSUyRjIwMjMxMjE2JTJGdXMtZWFzdC0xJTJGczMlMkZhd3M0X3JlcXVlc3QmWC1BbXotRGF0ZT0yMDIzMTIxNlQxOTIyNTJaJlgtQW16LUV4cGlyZXM9MzAwJlgtQW16LVNpZ25hdHVyZT0wZDFkMGRlYjhhNDdlMmI0YTNmMjE5MjQyNmQwMjg0NDU3NjQ5YTE4NzZjYmMxMjgyNTExOTJiNTMyNDFkMDBhJlgtQW16LVNpZ25lZEhlYWRlcnM9aG9zdCZhY3Rvcl9pZD0wJmtleV9pZD0wJnJlcG9faWQ9MCJ9.tY5dOkTen3NzUcZrA3cJyBLGVmt7S4m6_6GvMnk1vKA)

<br>

![Marvel Superheroes App Build Variants](https://private-user-images.githubusercontent.com/108676373/291011582-39134772-9bb8-4f5b-ab2c-7e23efb5213c.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTEiLCJleHAiOjE3MDI3NTQ4NzIsIm5iZiI6MTcwMjc1NDU3MiwicGF0aCI6Ii8xMDg2NzYzNzMvMjkxMDExNTgyLTM5MTM0NzcyLTliYjgtNGY1Yi1hYjJjLTdlMjNlZmI1MjEzYy5wbmc_WC1BbXotQWxnb3JpdGhtPUFXUzQtSE1BQy1TSEEyNTYmWC1BbXotQ3JlZGVudGlhbD1BS0lBSVdOSllBWDRDU1ZFSDUzQSUyRjIwMjMxMjE2JTJGdXMtZWFzdC0xJTJGczMlMkZhd3M0X3JlcXVlc3QmWC1BbXotRGF0ZT0yMDIzMTIxNlQxOTIyNTJaJlgtQW16LUV4cGlyZXM9MzAwJlgtQW16LVNpZ25hdHVyZT02M2RiZDBmMGQzNGNhYzdkZjM4MTRiNjNjMDY1YmI5MzIyODQ3ZmQ5OWM4Zjc2YTBlOWNiMDNmNjFhZWQxMjY0JlgtQW16LVNpZ25lZEhlYWRlcnM9aG9zdCZhY3Rvcl9pZD0wJmtleV9pZD0wJnJlcG9faWQ9MCJ9.QozbGCQpJuLxTeg4zuMXhKuzIeRsLyHEuzbPsxA5TX4)

<div align="center">
<video width="100" src="https://gist.github.com/assets/108676373/8d1798be-e97f-4cd5-8062-4ba1363954b0">
</video>
</div>

## Built With 🔨

- [Android SDK](https://developer.android.com/) - Create an Android App
- [Kotlin](https://developer.android.com/kotlin) - Develop Android apps with Kotlin
- [Hilt](https://dagger.dev/hilt) - Dependency injection
- [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) - Coroutines
- [Flow](https://developer.android.com/kotlin/flow?hl=es-419/) - Coroutine component to implement reactive programming
- [Jetpack compose](https://developer.android.com/develop/ui/views/layout/declaring-layout) - Jetpack compose kit to build the UI
- [Navigation compose](https://developer.android.com/jetpack/compose/navigation?hl=es-419) - NavGraph for navigation
- [Material Design](https://m3.material.io/) - Material components for the UI
- [Room](https://developer.android.com/jetpack/androidx/releases/room) - A persistence library that provides an abstraction layer over SQLite
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
- Personal tip! Check the app in dark mode 😎

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

## How to run the application 🏗️

As long as this application is kept with moderate use, I have added my own apikey and private apikey in the gradle.properties file.

If you don't find it added, you can put your own api key and private api key with the following format:

API_KEY_PUBLIC=11111111111
API_KEY_PRIVATE=222222222222222

You can provide your own api key from
here [Marvel docs](https://developer.marvel.com/documentation/getting_started).

## License ⚖️

[MIT License](License.txt)

## Author 🧑‍💻

Álvaro Erdociaín Tirado
Android Mobile developer
E-mail: ajerdociain@hiberus.com
[Linkedin](https://www.linkedin.com/in/alvaroerdociain)
[Github](https://github.com/AlvaroErd?)
