# Mobile Programming with Native Technologies

## Week 4 assignment - Navigation with Jetpack Compose

### Overview

This week extends the Week 3 ToDo app by adding **navigation between multiple screens** while keeping a **shared state** with MVVM.


### New Features

* **HomeScreen**
Displays the task list, same as Week 3.

* **CalendarScreen** 
Displays the same tasks in a calendar view, grouped by date.

* **SettingsScreen** 
The SettingsScreen includes a working light/dark theme toggle


### What is Jetpack Compose Navigation
Navigation is handled with **NavController** and **NavHost**.

Navigation is built using three main concepts:
* **NavController**  
  Switching between screens

* **NavHost**  
  Defines the navigation graph and maps routes to composable screens.

* **Routes**  
  String identifiers used to navigate between screens (e.g. "home", "calendar").

In this application, a single NavController is used to navigate between HomeScreen, CalendarScreen, and SettingsScreen.

### Navigation UI
The TopAppBar contains icons for navigating between Home, Calendar, and Settings.
* Home → Calendar via calendar icon
* Calendar → Home via list icon
* Home → Settings via settings icon
* Settings → Home via back arrow

### Single-Activity Architecture
* The entire app runs inside a single MainActivity.
All navigation happens inside one activity using Jetpack Compose Navigation.
* TaskViewModel is created at the activity level using by viewModels(), so it survives navigation and is shared across all screens.

### The Architecture (MVVM and Navigation)
The application follows the **MVVM (Model–View–ViewModel)** architecture.

* One TaskViewModel holds all tasks.
* HomeScreen and CalendarScreen share the same ViewModel
* Changes in one screen are immediately visible in the other
* ViewModel survives navigation between screens

Because the same ViewModel instance is shared:
* Changes made on one screen are immediately visible on the others.
* The application state is preserved when navigating back and forth.


### How CalendarScreen works
CalendarScreen presents tasks in a **calendar-like layout**.

Instead of using a full calendar component, tasks are:
* Grouped by their dueDate
* Displayed under date headers (for example: 2026-01-30)

This approach makes it clear:
* Which tasks belong to which day
* How the calendar concept relates to the task data

The calendar view uses the same task data as HomeScreen, ensuring consistency across the app.


### What AlertDialog does
Adding and editing tasks is done using **AlertDialog** and not navigation.

* **Add task**  
  Triggered by an “Add” or “+” button.  
  Opens a dialog where the user can enter task details and save or cancel.

* **Edit task**  
  Triggered by clicking an existing task.  
  Opens a dialog with pre-filled values and allows the user to update or delete the task.

Dialogs keep navigation focused on **screen-level changes** and handle temporary interactions.


### APK

The debug APK is included directly in the week4/ folder of this repository.


### Demo video

A short video to demonstrate the code and app on an emulator: 
https://youtube.com/shorts/MTh03vpIk4Q?feature=share


### Screenshot
Home Screen
<img width="339" height="596" alt="HomeScreen" src="https://github.com/user-attachments/assets/e66df666-57c6-47df-93c4-962bb5c3dd87" />

Calendar Screen
<img width="339" height="590" alt="CalendarScreen" src="https://github.com/user-attachments/assets/6c3dafe7-bf17-4d23-8900-e0714726fd85" />

Settings Screen
<img width="351" height="281" alt="SettingsScreen" src="https://github.com/user-attachments/assets/fdad632e-b842-4c35-8da9-0db9f29d5216" />


Detail Screen
<img width="345" height="614" alt="DetailScreen" src="https://github.com/user-attachments/assets/fafedbba-c4a9-4f13-af96-41299e10af6e" />












