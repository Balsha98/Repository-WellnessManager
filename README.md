# Wellness Manager - Daily Nutrition & Exercise Tracker

A desktop wellness tracking application developed as a group project for the Software Design Principles & Patterns course at the Rochester Institute of Technology (RIT). Built with Java and JavaFX, the app allows users to monitor their daily nutritional intake and exercise output through a clean, login-protected dashboard.

## Features

- **Secure Login** - User credentials are validated against a CSV-based data store on startup.
- **Daily Nutrition Dashboard** - View a live summary of daily calorie, carbohydrate, fat, and protein intake.
- **Basic Food Items** - Log individual food items with their associated nutritional values.
- **Recipes** - Create and log recipes composed of multiple existing food items.
- **Exercise Logging** - Record completed exercises to track calories burned throughout the day.
- **Calorie Balance** - See both calorie intake from food and calorie loss from exercise in one place.

## Tech Stack

- **Java** - Core Application Logic
- **JavaFX** - Desktop GUI Framework
- **CSV** - Lightweight User Credential & Data Storage

## Design Patterns

This project was built around the following software design patterns as part of the course curriculum:

| Pattern                         | Application                                                                                                     |
| ------------------------------- | --------------------------------------------------------------------------------------------------------------- |
| **MVC** (Model-View-Controller) | Overall application architecture separating data, UI, and control logic.                                        |
| **Facade**                      | Simplifies view transitions by providing a unified interface for switching between application screens.         |
| **Composite**                   | Models the food type hierarchy, allowing basic food items and multi-item recipes to be treated uniformly.       |
| **Singleton**                   | Ensures a single shared instance of the database access layer throughout the application lifecycle.             |
| **Proxy**                       | Controls access to the application by intercepting and validating user login credentials before granting entry. |

## Installation

Clone the repository to your local machine:

```bash
git clone https://github.com/Balsha98/Repository-WellnessManager.git
```

Navigate to the project directory:

```bash
cd Repository-WellnessManager
```

Open the project in your preferred Java IDE (IntelliJ IDEA or VS Code) and run:

```bash
# Ensure you have JDK 11+ and JavaFX configured in your environment.
# Run the main application entry point to launch the desktop GUI.
```

## Usage

1. **Log In**: Enter your credentials on the login screen — they are verified against the CSV data store via the Proxy layer.
2. **View Dashboard**: After a successful login, your daily nutrition summary (calories, carbs, fat, protein) loads automatically.
3. **Add Food Items**: Log individual food items to update your daily intake totals.
4. **Add Recipes**: Create a recipe by combining existing food items and log it as a single entry.
5. **Log Exercise**: Record any exercises completed to track your calorie expenditure for the day.
6. **Monitor Balance**: Review both calorie intake and calorie loss side by side on your dashboard.

## Project Structure

```
Repository-WellnessManager/
│
├── javafx-sdk/                 # JavaFX SDK dependency.
│
├── wellness-manager/           # Main application directory.
│   │
│   ├── .vscode/                # VS Code workspace settings.
│   │
│   ├── assets/                 # Assets directory.
│   │   ├── css/                # Basic styling.
│   │   ├── docs/               # CSV database files.
│   │   └── media/              # Images and media assets.
│   │
│   ├── core/                   # Core application source code.
│   │   ├── controllers/        # MVC controllers.
│   │   ├── enums/              # Application enumerations.
│   │   ├── interfaces/         # Shared interfaces.
│   │   ├── models/             # Data models.
│   │   │   ├── entries/        # Food and exercise entry models.
│   │   │   ├── logs/           # Daily log models.
│   │   │   ├── proxies/        # Proxy pattern implementations.
│   │   │   ├── tables/         # Table data models.
│   │   │   ├── types/          # Food type definitions.
│   │   │   ├── Model.java      # Base model class.
│   │   │   └── User.java       # User model.
│   │   └── views/              # Application views.
│   │
│   ├── Main.java               # Application entry point.
│   └── README.md               # Project documentation.
│
└── README.md                   # Root project documentation.
```

## How It Works

The application is structured around five design patterns working in concert:

- **MVC** forms the backbone of the app — models hold nutritional and exercise data, JavaFX controllers handle user interactions, and FXML views render the UI independently of business logic.
- **Proxy** intercepts login attempts and validates the submitted credentials against the CSV data store before allowing the user through to the main dashboard, acting as a protective wrapper around the real subject.
- **Facade** provides a single, simplified interface for navigating between application screens, hiding the complexity of loading and switching JavaFX views from the rest of the codebase.
- **Composite** represents the food type hierarchy uniformly — a basic food item and a recipe composed of multiple food items both implement the same interface, allowing the nutrition dashboard to calculate totals without distinguishing between them.
- **Singleton** guarantees that only one instance of the data access layer is created and shared across the entire application, preventing redundant file reads and ensuring data consistency.

## Let's Connect

If you enjoyed this project or have any questions, feel free to reach out!

[![Portfolio](https://img.shields.io/badge/Portfolio-FF5722?style=for-the-badge&logo=todoist&logoColor=white)](https://bazovich.dev)
[![Email](https://img.shields.io/badge/Email-D14836?style=for-the-badge&logo=gmail&logoColor=white)](mailto:balsa.bazovic@gmail.com)
[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/balsha-bazovic)
[![GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/Balsha98)

⭐ If you found this project helpful, please consider giving it a star!

## License

Personal project - no license has been set.
