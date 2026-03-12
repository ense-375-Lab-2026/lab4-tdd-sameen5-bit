# Lab 4: Hockey League Simulator - Code Coverage Report

## Student Information
- **Name:** Yash Jadav
- **Student ID:** 200519386
- **Course:** ENSE 375
- **Lab:** Lab 4 – Code Coverage

## Project Overview
Hockey League Simulator with:
- 6 Original NHL teams loaded from CSV files
- Database storage using SQLite
- Player management (add, delete, edit)
- Game simulation logic
- JUnit tests with >80% code coverage

## Requirements Met

| Requirement | Status |
|-------------|--------|
| Load CSV files into Hockey League | ✅ |
| Store data in database | ✅ |
| Add player to roster | ✅ |
| Delete player from roster | ✅ |
| Edit player rating | ✅ |
| Play a game | ✅ |
| Code coverage >80% | ✅ |

## Test Coverage

### Test Categories
| Category | Number of Tests |
|----------|-----------------|
| HockeyPlayer Tests | 4 |
| HockeyTeam Tests | 15 |
| HockeyLeague Tests | 7 |
| Integration Tests | 2 |
| **Total** | **28** |

### Coverage Analysis
- **HockeyPlayer.java:** ~100% (all methods tested)
- **HockeyTeam.java:** ~95% (all logic tested)
- **HockeyLeague.java:** ~85% (core functionality tested)

## Key Features Implemented

### 1. CSV Loading
- All 6 team CSV files are loaded on startup
- Players are parsed and added to team rosters

### 2. Database Storage
- SQLite database created automatically
- Teams and players stored with proper relationships
- Connection managed properly

### 3. Player Management
- Add players with duplicate checking
- Delete players by name
- Edit player position and rating
- Retrieve players by name

### 4. Game Simulation
- Validates roster composition before game
- Random scoring opportunities
- Goalie vs shooter rating comparison
- Overtime for tied games

## How to Run Tests with Coverage

### In VS Code:
1. Open the Testing sidebar (🧪 icon)
2. Click the three-dot menu (⋮)
3. Select "Run All Tests with Coverage"
4. View coverage highlights in code editor

### Expected Results
- All 28 tests should pass
- Code coverage >80% for main classes

## AI Usage Declaration
I used GitHub Copilot and ChatGPT as AI assistants during this lab.

**Purpose of use:**
- Understanding code coverage requirements
- Implementing database connectivity
- Writing comprehensive test cases
- Debugging game simulation logic
- Generating JavaDoc comments

All code was reviewed, tested, and understood before submission.