/*
 * Name: Yash Jadav
 * Student ID: 200519386
 * Course: ENSE 375
 * Lab: Lab 4 – Code Coverage
 */

/**
* HockeyTeam.java
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

/**
 * Represents a hockey team with a roster of players.
 * 
 * <p>A standard hockey team must have:
 * <ul>
 *   <li>4 Centers (C)</li>
 *   <li>4 Left Wings (LW)</li>
 *   <li>4 Right Wings (RW)</li>
 *   <li>6 Defencemen (LD/RD)</li>
 *   <li>2 Goaltenders (G)</li>
 * </ul>
 * Total roster size: 20 players
 * </p>
 * 
 * @author Trevor Douglas
 * @author Yash Jadav
 * @version 1.0
 * @since Lab 4 - Code Coverage
 */
public class HockeyTeam {

    private ArrayList<HockeyPlayer> roster = new ArrayList<HockeyPlayer>();
    public String teamName;
    private static final int ROSTER_SIZE = 20;

    /**
     * Constructs a new HockeyTeam with the given name.
     * Initializes an empty roster.
     *
     * @param teamName the name of the team
     */
    public HockeyTeam(String teamName) {
        this.teamName = teamName;
    }

    /**
     * Returns the current team roster.
     *
     * @return ArrayList of HockeyPlayer objects
     */
    public ArrayList<HockeyPlayer> getRoster() {
        return roster;
    }

    /**
     * Adds a player to the team roster.
     * 
     * <p>Validation checks:
     * <ul>
     *   <li>Player cannot be null</li>
     *   <li>Roster cannot exceed maximum size (20)</li>
     *   <li>No duplicate players (same first and last name)</li>
     * </ul>
     * </p>
     *
     * @param player the HockeyPlayer to add
     * @return true if player was added successfully, false otherwise
     */
    public boolean addPlayer(HockeyPlayer player) {
        // Check if player is null
        if (player == null) {
            return false;
        }
        
        // Check if roster is already full (max 20 players)
        if (roster.size() >= ROSTER_SIZE) {
            return false;
        }
        
        // Check for duplicate player (same first and last name)
        for (HockeyPlayer p : roster) {
            if (p.getFirstName().equals(player.getFirstName()) && 
                p.getLastName().equals(player.getLastName())) {
                return false; // Duplicate found
            }
        }
        
        roster.add(player);
        return true;
    }


    /**
     * Deletes a player from the team roster.
     * 
     * <p>Finds and removes a player with matching first and last name.
     * The player object's other attributes (position, rating) are ignored
     * for the purpose of identification.</p>
     *
     * @param player the HockeyPlayer to delete (first and last name used for matching)
     * @return true if player was found and deleted, false otherwise
     */
    public boolean deletePlayer(HockeyPlayer player) {
        // Check if player is null
        if (player == null) {
            return false;
        }
        
        // Find and remove player with matching first and last name
        for (int i = 0; i < roster.size(); i++) {
            HockeyPlayer p = roster.get(i);
            if (p.getFirstName().equals(player.getFirstName()) && 
                p.getLastName().equals(player.getLastName())) {
                roster.remove(i);
                return true;
            }
        }
        
        return false; // Player not found
    }


    /**
     * Retrieves a player from the roster.
     * 
     * <p>Searches for a player with matching first and last name.
     * Returns the actual player object from the roster, not a copy.</p>
     *
     * @param player the HockeyPlayer to find (first and last name used for matching)
     * @return the matching HockeyPlayer object, or null if not found
     */
    public HockeyPlayer getPlayer(HockeyPlayer player) {
        if (player == null) {
            return null;
        }
        
        for (HockeyPlayer p : roster) {
            if (p.getFirstName().equals(player.getFirstName()) && 
                p.getLastName().equals(player.getLastName())) {
                return p;
            }
        }
        
        return null;
    }

    /**
     * Edits a player's position and rating.
     * 
     * <p>Finds a player with matching first and last name, then updates
     * their position and rating with the values from the provided player object.
     * Other attributes (first name, last name) cannot be edited through this method.</p>
     *
     * @param player the HockeyPlayer containing updated position and rating
     * @return true if player was found and updated, false otherwise
     */
    public boolean editPlayer(HockeyPlayer player) {
        if (player == null) {
            return false;
        }
        
        HockeyPlayer existingPlayer = getPlayer(player);
        if (existingPlayer != null) {
            // Update position and rating only
            existingPlayer.setPosition(player.getPosition());
            existingPlayer.setRating(player.getRating());
            return true;
        }
        
        return false;
    }
    
    /**
     * Validates that the roster has the correct composition.
     * 
     * <p>A valid NHL roster must have exactly:
     * <ul>
     *   <li>4 Centers (C)</li>
     *   <li>4 Left Wings (LW)</li>
     *   <li>4 Right Wings (RW)</li>
     *   <li>6 Defencemen (LD or RD)</li>
     *   <li>2 Goaltenders (G)</li>
     * </ul>
     * Total players must be exactly 20.</p>
     *
     * @return true if roster composition is valid, false otherwise
     */
    public boolean isValidRoster() {
        if (roster.size() != ROSTER_SIZE) {
            return false;
        }
        
        int centers = 0;
        int leftWings = 0;
        int rightWings = 0;
        int defense = 0;
        int goalies = 0;
        
        for (HockeyPlayer p : roster) {
            String pos = p.getPosition();
            if (pos.equals("C")) {
                centers++;
            } else if (pos.equals("LW")) {
                leftWings++;
            } else if (pos.equals("RW")) {
                rightWings++;
            } else if (pos.equals("LD") || pos.equals("RD")) {
                defense++;
            } else if (pos.equals("G")) {
                goalies++;
            }
        }
        
        return centers == 4 && leftWings == 4 && rightWings == 4 && 
               defense == 6 && goalies == 2;
    }
}