/*
 * Name: Dur E Sameen Bukhari
 * Student ID: 200519386
 * Course: ENSE 375
 * Lab: Lab 4 – Code Coverage
 */

/**
* HockeyLeague.java
*
* @author Trevor Douglas
* @author Yash Jadav
*   <A HREF="mailto:douglatr@uregina.ca"> (douglatr@uregina.ca) </A>
*
* Original code copyright � Mar 15, 2010 Trevor Douglas.  Modifications can be made
* with Author's consent.
* @version Mar 15, 2010
*
**/

import java.util.ArrayList;
import java.util.Random;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

/**
 * Manages a hockey league with multiple teams and game simulation.
 * 
 * <p>This class handles:
 * <ul>
 *   <li>Loading team data from CSV files</li>
 *   <li>Storing teams and players in SQLite database</li>
 *   <li>Simulating games between teams</li>
 *   <li>Validating roster compositions</li>
 * </ul>
 * </p>
 * 
 * <p>The league consists of the original 6 NHL teams:
 * <ul>
 *   <li>Toronto Maple Leafs</li>
 *   <li>Montreal Canadiens</li>
 *   <li>Boston Bruins</li>
 *   <li>Detroit Red Wings</li>
 *   <li>Chicago Blackhawks</li>
 *   <li>New York Rangers</li>
 * </ul>
 * </p>
 * 
 * @author Trevor Douglas
 * @author Dur E Sameen Bukhari
 * @version 1.0
 * @since Lab 4 - Code Coverage
 */
public class HockeyLeague {

    /** List of teams in the league */
    ArrayList<HockeyTeam> teams = new ArrayList<HockeyTeam>();
    
    /** Standard NHL roster size */
    private static final int ROSTER_SIZE = 20;
    
    /** Database connection object */
    private Connection connection;
    
    /** SQLite database URL */
    private static final String DB_URL = "jdbc:sqlite:hockey.db";

    /**
     * Constructs a new HockeyLeague and initializes all teams.
     * 
     * <p>This constructor:
     * <ol>
     *   <li>Initializes the SQLite database</li>
     *   <li>Creates necessary tables (teams, players)</li>
     *   <li>Loads all 6 team CSV files</li>
     *   <li>Stores teams and players in the database</li>
     * </ol>
     * </p>
     */
    public HockeyLeague() {
        // Initialize database
        initDatabase();
        
        // Load team data from CSV files
        loadTeamFromCSV("TorontoMapleLeafs.csv");
        loadTeamFromCSV("MontrealCanadiens.csv");
        loadTeamFromCSV("BostonBruins.csv");
        loadTeamFromCSV("DetroitRedWings.csv");
        loadTeamFromCSV("ChicagoBlackhawks.csv");
        loadTeamFromCSV("NewYorkRangers.csv");
    }
    
    /**
     * Initializes the SQLite database and creates required tables.
     * 
     * <p>Creates two tables:
     * <ul>
     *   <li>teams - stores team information (id, name)</li>
     *   <li>players - stores player information with foreign key to teams</li>
     * </ul>
     * </p>
     */
    private void initDatabase() {
        try {
            connection = DriverManager.getConnection(DB_URL);
            
            // Create teams table
            String createTeamsTable = "CREATE TABLE IF NOT EXISTS teams (" +
                                      "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                      "name TEXT UNIQUE)";
            Statement stmt = connection.createStatement();
            stmt.execute(createTeamsTable);
            
            // Create players table
            String createPlayersTable = "CREATE TABLE IF NOT EXISTS players (" +
                                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                        "team_name TEXT, " +
                                        "position TEXT, " +
                                        "first_name TEXT, " +
                                        "last_name TEXT, " +
                                        "rating INTEGER, " +
                                        "FOREIGN KEY(team_name) REFERENCES teams(name))";
            stmt.execute(createPlayersTable);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Loads a team from a CSV file and adds it to the league.
     * 
     * <p>CSV file format: position,firstName,lastName,rating
     * Example: C,Auston,Matthews,95</p>
     *
     * @param filename the name of the CSV file to load
     */
    private void loadTeamFromCSV(String filename) {
        String teamName = filename.replace(".csv", "");
        
        // Create team
        HockeyTeam team = new HockeyTeam(teamName);
        
        // Try multiple locations for the CSV file
        File csvFile = null;
        String[] possiblePaths = {
            filename,                          // Current directory
            "src/" + filename,                 // Maven test execution
            "Labs/lab4/src/" + filename        // Alternative path
        };
        
        for (String path : possiblePaths) {
            File f = new File(path);
            if (f.exists()) {
                csvFile = f;
                break;
            }
        }
        
        if (csvFile == null || !csvFile.exists()) {
            System.err.println("Warning: CSV file not found for team: " + teamName);
            teams.add(team);  // Add empty team
            return;
        }
        
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String position = parts[0];
                    String firstName = parts[1];
                    String lastName = parts[2];
                    int rating = Integer.parseInt(parts[3]);
                    
                    HockeyPlayer player = new HockeyPlayer(position, firstName, lastName, rating);
                    team.addPlayer(player);
                    
                    // Store in database
                    storePlayerInDB(teamName, player);
                }
            }
            
            // Add team to league
            teams.add(team);
            
            // Store team in database
            storeTeamInDB(teamName);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Stores a team in the database.
     * Uses INSERT OR IGNORE to avoid duplicate entries.
     *
     * @param teamName the name of the team to store
     */
    private void storeTeamInDB(String teamName) {
        String sql = "INSERT OR IGNORE INTO teams (name) VALUES (?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, teamName);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Stores a player in the database with reference to their team.
     *
     * @param teamName the name of the team the player belongs to
     * @param player the player object to store
     */
    private void storePlayerInDB(String teamName, HockeyPlayer player) {
        String sql = "INSERT INTO players (team_name, position, first_name, last_name, rating) " +
                     "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, teamName);
            pstmt.setString(2, player.getPosition());
            pstmt.setString(3, player.getFirstName());
            pstmt.setString(4, player.getLastName());
            pstmt.setInt(5, player.getRating());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Retrieves a team by its name.
     *
     * @param teamName the name of the team to find
     * @return the HockeyTeam object if found, null otherwise
     */
    public HockeyTeam getTeam(String teamName) {
        for (HockeyTeam team : teams) {
            if (team.teamName.equals(teamName)) {
                return team;
            }
        }
        return null;
    }


    /**
     * Simulates a hockey game between two teams.
     * 
     * <p>Game simulation algorithm:
     * <ol>
     *   <li>Validates both teams have correct roster composition</li>
     *   <li>Generates random number of scoring opportunities (5-15)</li>
     *   <li>For each opportunity, selects random shooter (skater only)</li>
     *   <li>Compares shooter rating vs opposing goalie rating</li>
     *   <li>Goal scored if random number < (goalieRating/shooterRating)*100</li>
     *   <li>Overtime (random winner) if tied after regulation</li>
     * </ol>
     * </p>
     *
     * @param visitingTeam the name of the visiting team
     * @param homeTeam the name of the home team
     * @return the name of the winning team, or error message if game can't be played
     */
    public String playGame(String visitingTeam, String homeTeam) {

        HockeyTeam visiting = null;
        HockeyTeam home = null;

        int homeScore = 0;
        int visitingScore = 0;

        if (checkTeams(visitingTeam, homeTeam) == false) {
            return "Unplayable Game Rosters are wrong";
        }

        //First get the objects for the teams
        for (int i = 0; i < teams.size(); i++) {
            if (teams.get(i).teamName.equals(visitingTeam)) {
                visiting = teams.get(i);
                break;
            }
        }

        for (int i = 0; i < teams.size(); i++) {
            if (teams.get(i).teamName.equals(homeTeam)) {
                home = teams.get(i);
                break;
            }
        }

        Random random = new Random();
        int numTotalGoals = getRandomInteger(5, 15, random);

        //Now pick a random player from a random team who will be the shooter
        for (int i = 0; i < numTotalGoals; i++) {

            int randomTeam = getRandomInteger(0, 1, random);
            String playerPos = "G";
            int shooterRating = 0;
            int attemptCount = 0;
            do {
                if (randomTeam == 0) {
                    int randomPlayer = getRandomInteger(0, visiting.getRoster().size() - 1, random);
                    playerPos = visiting.getRoster().get(randomPlayer).getPosition();
                    shooterRating = visiting.getRoster().get(randomPlayer).getRating();
                } else {
                    int randomPlayer = getRandomInteger(0, home.getRoster().size() - 1, random);
                    playerPos = home.getRoster().get(randomPlayer).getPosition();
                    shooterRating = home.getRoster().get(randomPlayer).getRating();
                }
                attemptCount++;
                if (attemptCount > 100) break;
            } while (playerPos.equals("G") && attemptCount <= 100);

            //Get the opposing teams goalie
            int goalieRating = 0;
            if (randomTeam == 0) {
                for (int j = 0; j < home.getRoster().size(); j++) {
                    if (home.getRoster().get(j).getPosition().equals("G")) {
                        goalieRating = home.getRoster().get(j).getRating();
                        break;
                    }
                }
            } else {
                for (int j = 0; j < visiting.getRoster().size(); j++) {
                    if (visiting.getRoster().get(j).getPosition().equals("G")) {
                        goalieRating = visiting.getRoster().get(j).getRating();
                        break;
                    }
                }
            }

            //Now match them up...
            float ratio = (float) goalieRating / (float) shooterRating;
            int intRatio = (int) (ratio * 100);

            int goalOrNot = getRandomInteger(1, 200, random);
            if (goalOrNot < intRatio) {
                if (randomTeam == 0)
                    visitingScore++;
                else
                    homeScore++;
            }
        }

        System.out.println("At end of regulation...");
        System.out.println(visitingTeam + " = " + visitingScore);
        System.out.println(homeTeam + " = " + homeScore);

        int winningTeam;
        if (homeScore > visitingScore) {
            winningTeam = 1;
        } else if (homeScore < visitingScore) {
            winningTeam = 0;
        } else {
            System.out.println("Overtime needed");
            winningTeam = getRandomInteger(0, 1, random);
        }

        if (winningTeam == 0) {
            System.out.println(visiting.teamName + " wins!");
            return visiting.teamName;
        } else {
            System.out.println(home.teamName + " wins!");
            return home.teamName;
        }
    }


    /**
     * Verifies that both teams have valid roster compositions.
     * 
     * <p>A valid roster must have exactly:
     * <ul>
     *   <li>4 Centers</li>
     *   <li>4 Left Wings</li>
     *   <li>4 Right Wings</li>
     *   <li>6 Defencemen</li>
     *   <li>2 Goaltenders</li>
     * </ul>
     * </p>
     *
     * @param visitingTeam the name of the visiting team
     * @param homeTeam the name of the home team
     * @return true if both teams have valid rosters, false otherwise
     */
    private boolean checkTeams(String visitingTeam, String homeTeam) {
        HockeyTeam visiting = null;
        HockeyTeam home = null;
        
        for (int i = 0; i < teams.size(); i++) {
            if (teams.get(i).teamName.equals(visitingTeam)) {
                visiting = teams.get(i);
            }
            if (teams.get(i).teamName.equals(homeTeam)) {
                home = teams.get(i);
            }
        }
        
        if (visiting == null || home == null) {
            return false;
        }
        
        return visiting.isValidRoster() && home.isValidRoster();
    }


    /**
     * Generates a random integer within a specified range.
     * 
     * <p>Used by the game simulation to generate random numbers
     * for scoring opportunities and outcomes.</p>
     *
     * @param aStart the minimum value (inclusive)
     * @param aEnd the maximum value (inclusive)
     * @param aRandom Random object to use for generation
     * @return a random integer between aStart and aEnd
     * @throws IllegalArgumentException if aStart > aEnd
     */
    private static int getRandomInteger(int aStart, int aEnd, Random aRandom) {
        if (aStart > aEnd) {
            throw new IllegalArgumentException("Start cannot exceed End.");
        } else {
            long range = (long) aEnd - (long) aStart + 1;
            long fraction = (long) (range * aRandom.nextDouble());
            int randomNumber = (int) (fraction + aStart);
            return randomNumber;
        }
    }
    
    /**
     * Closes the database connection.
     * 
     * <p>Should be called when the league object is no longer needed
     * to free database resources.</p>
     */
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
