# Mobile Programming with Native Technologies

## Week 3 assignment

### Overview

This week’s assignment builds on the previous project by improving architecture, adding a detail/edit dialog, and making the UI fully reactive using StateFlow and MVVM. The app now has a clear layer structure and updates instantly whenever the ViewModel state changes.

### New Features

* **Edit Task dialog** (Save, Delete, Cancel)

* **UI updates** instantly when tasks change

* Project split into model, viewmodel, and view

* Cleaner and more consistent UI design


### MVVM (Model-View-ViewModel) Architecture

The project now follows the MVVM pattern:

* **Model** – data classes (like Task)

* **ViewModel** – app logic (add, edit, delete, filter)

* **View** – the UI made with Jetpack Compose that observes ViewModel state

MVVM keeps UI and logic separate, UI shows the data, ViewModel handles the logic.


### Why use MVVM
Compose is reactive.
This means the UI updates automatically when the data changes.

Using MVVM:

* ViewModel holds the state

* UI listens to that state

* When the ViewModel updates something, the UI changes right away

No manual refresh needed


### How StateFlow works

StateFlow is a value that the UI can listen to.

* ViewModel updates the StateFlow

* UI uses collectAsState() to watch it

* When the value changes, the UI updates automatically

If the ViewModel changes tasks, the list on screen updates instantly


### APK

Debug APK available in the Week 3 Release


### Demo video

A short video to demonstrate the code and app on an emulator: Week 3
Release


### Screenshot
![Home Screen](screenshots/HomeScreen.png)

![Detail Screen](screenshots/DetailScreen.png)










