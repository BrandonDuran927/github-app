# GitHub App

An offline-first Android application that makes exploring GitHub repositories effortless on android devices. Built for developers who want quick access to repository information without relying on constant internet connectivity.

## Download

**[Download APK (Latest Version)](https://archive.org/download/github_202510/github.apk)**

> Download and install the APK directly on your Android device. Make sure to enable "Install from unknown sources" in your device settings.

## Features

### Core Functionality
- **Repository Search**: Search for any GitHub user's public repositories
- **Repository Explorer**: Browse repository contents including directories and files
- **Code Viewer**: View code files with syntax highlighting support for 20+ programming languages
- **Search History**: Keep track of your recent searches with easy access
- **Offline-First**: All data is cached locally for offline access
- **Network Status**: Real-time network connectivity indicators
- **Modern UI**: Clean, intuitive interface built with Material Design 3

### Technical Highlights
- **Smart Caching**: Efficient local storage with Room Database
- **Change Detection**: Only updates data when remote changes are detected
- **Error Handling**: Graceful fallback to cached data when offline
- **Responsive Design**: Optimized for various Android screen sizes

## Technology Stack

| Category | Technologies |
|----------|-------------|
| **Language** | Kotlin |
| **UI Framework** | Jetpack Compose |
| **Architecture** | MVVM + Clean Architecture |
| **Dependency Injection** | Dagger Hilt |
| **Networking** | Retrofit, OkHttp |
| **Local Database** | Room |
| **Image Loading** | Coil |
| **Navigation** | Navigation Compose with Type-Safe Routes |
| **Serialization** | Kotlinx Serialization |
| **Async Operations** | Kotlin Coroutines & Flow |

## Prerequisites

- Android Studio Hedgehog | 2023.1.1 or newer
- Android SDK 24 or higher (minSdk: 24)
- Target SDK 36
- JDK 11 or higher
- Gradle 8.13

## Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/yourusername/github-app.git
cd github-app
```

### 2. GitHub API Token Configuration

To maximize your API rate limits and access private repositories (if needed), you'll need a GitHub Personal Access Token.

#### Generate a GitHub Token:

1. Go to [GitHub Settings > Developer Settings > Personal Access Tokens](https://github.com/settings/tokens)
2. Click **"Generate new token (classic)"**
3. Give your token a descriptive name (e.g., "GitHub App Mobile")
4. Select scopes:
   - `public_repo` (for public repository access)
   - `repo` (if you want private repository access)
5. Click **"Generate token"**
6. **Copy the token immediately** (you won't be able to see it again)

#### Add Token to Project:

1. Create or open the `local.properties` file in your project root directory
2. Add your token:

```properties
GITHUB_TOKEN=ghp_yourTokenHere123456789
```

> **!!Important!!**: The `local.properties` file is already in `.gitignore` and will not be committed to version control. Never commit your API token to a public repository.

#### Using Without a Token:

The app will work without a token but with GitHub's default rate limits (60 requests per hour). With a token, you get 5,000 requests per hour.

### 3. Build and Run

1. Open the project in Android Studio
2. Sync project with Gradle files
3. Select a device or emulator
4. Click **Run** or press `Shift + F10`

## Project Structure

```
com.brandon.github_app/
├── app/                    # Application module
├── core/                   # Core utilities and models
│   ├── composables/        # Reusable UI components
│   ├── local/              # Database entities and DAOs
│   ├── model/              # Domain models
│   ├── network/            # Network monitoring
│   └── route/              # Navigation routes
├── search/                 # Search feature
│   ├── presentation/       # UI layer
├── searchHistory/          # Search history feature
│   ├── data/               # Data layer
│   ├── domain/             # Business logic
│   └── presentation/       # UI layer
├── listOfRepo/             # Repository list feature
│   ├── data/               # Data layer
│   ├── domain/             # Business logic
│   └── presentation/       # UI layer
├── repoContents/           # Repository contents feature
│   ├── data/               # Data layer
│   ├── domain/             # Business logic
│   └── presentation/       # UI layer
└── fileViewer/             # Code viewer feature
    ├── data/               # Data layer
    ├── domain/             # Business logic
    └── presentation/       # UI layer
```

## Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## Screenshots
<img width="150" alt="Screenshot_20251020_222749" src="https://github.com/user-attachments/assets/10d7abf6-1d98-4631-bc1e-5dbe90a1f6ac" />
<img width="150" alt="Screenshot_20251020_222810" src="https://github.com/user-attachments/assets/1200f97d-5202-4dff-9a02-01ffbe626cd0" />
<img width="150" alt="Screenshot_20251020_222839" src="https://github.com/user-attachments/assets/5e259474-b6c6-4eb5-9ec0-f5f4345e6d66" />
<img width="150" alt="Screenshot_20251020_222927" src="https://github.com/user-attachments/assets/f1bba797-9ebd-4ca8-8dcb-63c5cdd99c3c" />
<img width="150" alt="Screenshot_20251020_223048" src="https://github.com/user-attachments/assets/593f3a31-7872-4bb9-aa72-915364ee0cbf" />
<img width="150" alt="Screenshot_20251020_223129" src="https://github.com/user-attachments/assets/a25138a8-3683-40ba-8ef4-cf8f958056f8" />
<img width="150" alt="Screenshot_20251020_223153" src="https://github.com/user-attachments/assets/f9d57086-4760-46c7-9520-a88b93da9a34" />
<img width="150" alt="Screenshot_20251020_223209" src="https://github.com/user-attachments/assets/cb338a23-49fb-46d1-beee-21eaa4973587" />

## Author

**Brandon Duran**
- GitHub: [@BrandonDuran927](https://github.com/BrandonDuran927)
---

<div align="center">
  Made with ❤️ using Kotlin and Jetpack Compose
</div>
