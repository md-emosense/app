
# EmoSense: Emotion Detection and Speech Therapy App for People with Autism Spectrum Disorder (ASD)
EmoSense is an application that provides a place for people with ASD to practice their emotional interpretation and some additional features such as a community platform for sharing experiences and support, health and community articles, and recommendations regarding clinic/therapy locations. This application targets the family members of people with ASD, hence the main users will be them. This application is expected to be a safe place for the family members to engage with their children/siblings who live with ASD more. In this project, we use Convolutional Neural Networks (CNNs) to classify facial expressions into seven different emotions: anger, disgust, fear, happiness, sadness, surprise, and neutral.

## Contributors on Android App

| Member ID      | Name                     | Institution                  |
|----------------|--------------------------|------------------------------|
| A010D4KX3539   | Refiany Shadrina         | Universitas Indonesia        |
| A012D4KX4060   | Aisha Farizka Mawla      | Universitas Telkom           |


## Installation Guideline

Choose one of the following options to get started with Emosense Project:

### Option 1: Run the Project in an Android Emulator

1. **Fork, Clone, or Download the Repository**

   - **Fork** this repository on GitHub to your account.
   - **Clone** the repository to your local machine using:
     ```sh
     git clone https://github.com/md-emosense/md-emosense.git
     cd md-emosense
     ```
   - Alternatively, you can **download** the repository as a ZIP file and extract it.

2. **Open the Project in Android Studio**

   - Open Android Studio.
   - Select `File` -> `Open`.
   - Navigate to the project directory and select it.

3. **Sync Project with Gradle Files**

   - Android Studio should prompt you to sync the project with Gradle files. Click `Sync Now`.
   - Alternatively, manually sync by clicking `File` -> `Sync Project with Gradle Files`.

4. **Set Up the Android SDK**

   - Ensure the required SDK versions are installed. Install SDKs in `File` -> `Project Structure` -> `SDK Location` -> `Android SDK`.
   - Install any missing SDKs as prompted.

5. **Build the Project**

   - Click `Build` -> `Make Project` or use the shortcut `Ctrl+F9` (Windows/Linux) or `Cmd+F9` (Mac).

6. **Run the Application**

   - Connect an Android device via USB or set up an Android Virtual Device (AVD).
   - Click `Run` -> `Run 'app'` or use the shortcut `Shift+F10`.

### Option 2: Download and Install the APK

1. **Download the APK**

   - Download the latest version of the APK from the following link:
     [Emosense-v1.0](https://drive.google.com/drive/u/0/folders/1oJILeZtZg_djDnjVJFTSl_Vn4LoZpkev)

2. **Install the APK**

   - Transfer the downloaded APK to your Android device.
   - Go to `Settings` -> `Security` and enable `Unknown sources` to allow installation of apps from sources other than the Google Play Store.
   - Open the APK file and follow the on-screen instructions to install the app.

## Requirements

Ensure you have the following installed:
- **Kotlin** 100%
- **Java Development Kit (JDK)**: JDK 8 or higher
- **Android Studio**: Latest version
- **Gradle**: Typically, Android Studio handles this
- **Android SDK**:
  - Compile SDK Version: 34
  - Minimum SDK Version: 24
  - Target SDK Version: 34

## Dependencies

Main libraries in this project:

- [Retrofit](https://square.github.io/retrofit/): `com.squareup.retrofit2:retrofit`
- [OkHttp Logging Interceptor](https://square.github.io/okhttp/): `com.squareup.okhttp3:logging-interceptor`
- [Room](https://developer.android.com/training/data-storage/room): `androidx.room:room-runtime`
- [Glide](https://bumptech.github.io/glide/): `com.github.bumptech.glide:glide`
- [JUnit](https://junit.org/junit4/): `junit:junit`

## Troubleshooting

If you encounter any issues, check the following:

- **Gradle Sync Issues**: Ensure you have a stable internet connection. Clearing the Gradle cache can resolve sync issues.
- **SDK Issues**: Ensure all required SDK components are installed and up-to-date.
 
## Acknowledgments

Special thanks to Bangkit 2024 for providing the opportunity to work on this capstone project and to the instructors and mentors for their guidance.

---
