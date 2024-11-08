# My Calling App

My Calling App is an Android application that allows users to view, search, sort, and interact with their contacts. The app follows the MVVM architecture and provides features for making calls and sending SMS messages directly from the contact list.

## Features

- **MVVM Architecture**: Organized codebase with clear separation of concerns.
- **Contact List Display**: Uses `RecyclerView` to display contacts with details including:
  - Full name
  - Phone number
  - Profile image (first letter of contact name)
- **Call and SMS Menu**: Each contact item has a menu to initiate calls and send SMS messages.
- **Search Functionality**: Easily search contacts by name.
- **Sort Contacts**: Sort the contact list alphabetically by name.
- **Splash Screen**: Displays the app logo and name on launch.
- **Multi-Language Support**: Supports English (default), Arabic, and French.

## Screenshots

![image](https://github.com/user-attachments/assets/439fd6c2-05ca-4e35-ada8-3017b826ff09)


## Installation

1. **Clone this repository**:
    ```bash
    git clone https://github.com/DrXeno0/contactApp.git
    ```
2. **Open in Android Studio**:
   - Open Android Studio and choose **Open an existing Android Studio project**.
   - Select the cloned repository folder.

3. **Run on an Android device**:
   - Connect an Android device with developer mode enabled, or use an emulator.
   - Click **Run** or press `Shift + F10`.

## Usage

1. **View Contacts**: The main screen displays a list of contacts fetched from the device.
2. **Sort Contacts**: Tap the sort button to arrange contacts alphabetically.
3. **Search Contacts**: Tap the search icon to search contacts by name.
4. **Make Calls**: Tap the call option in a contact’s menu to make a phone call.
5. **Send SMS**: Tap the SMS option to open the messaging app with a prefilled message.
6. **Change Language**: Go to settings to switch between English, Arabic, and French.

## Permissions

The app requires the following permissions:
- **READ_CONTACTS**: To access contacts on the device.
- **CALL_PHONE**: To initiate phone calls.
- **SEND_SMS**: To send SMS messages.

Ensure you grant these permissions when prompted for the app to function correctly.

## MVVM Structure

The app follows the MVVM pattern:
- **Model**: Holds contact data classes.
- **View**: Displays UI elements and updates based on ViewModel data.
- **ViewModel**: Manages UI-related data and handles business logic.

## Technologies Used

- **Kotlin**: For the main programming language.
- **RecyclerView**: To display the contact list.
- **LiveData and ViewModel**: For reactive UI updates.
- **Permissions**: Runtime permissions for accessing contacts, making calls, and sending SMS.
- **Localization**: Support for multiple languages.


---


