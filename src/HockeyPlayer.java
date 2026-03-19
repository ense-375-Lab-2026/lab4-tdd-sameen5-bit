/*
 * Name: Dur E Sameen Bukhari
 * Student ID: 200542362
 * Course: ENSE 375
 * Lab: Lab 4 – Code Coverage
 */

/**
* HockeyPlayer.java
*
* @author Trevor Douglas
* @author Dur e Sameen Bukhari
*   <A HREF="mailto:douglatr@uregina.ca"> (douglatr@uregina.ca) </A>
*
* Original code copyright � Mar 15, 2010 Trevor Douglas.  Modifications can be made
* with Author's consent.
* @version Mar 15, 2010
*
**/

/**
 * Represents a hockey player with position, name, and rating.
 * 
 * <p>This class stores basic player information including:
 * <ul>
 *   <li>Position (C, LW, RW, LD, RD, G)</li>
 *   <li>First and last name</li>
 *   <li>Player rating (0-100 scale)</li>
 * </ul>
 * </p>
 * 
 * @author Trevor Douglas
 * @author Dur E Sameen Bukhari
 * @version 1.0
 * @since Lab 4 - Code Coverage
 */
public class HockeyPlayer {

    private String firstName;
    private String lastName;
    private String position;
    private int rating;

    /**
     * Constructs a new HockeyPlayer with specified attributes.
     *
     * @param position the player's position (C, LW, RW, LD, RD, G)
     * @param firstName the player's first name
     * @param lastName the player's last name
     * @param rating the player's skill rating (0-100)
     */
    public HockeyPlayer(String position, String firstName,
            String lastName, int rating) {
        this.position = position;
        this.firstName = firstName;
        this.lastName = lastName;
        this.rating = rating;
    }

    /**
     * Default constructor that creates an empty HockeyPlayer.
     * All fields are initialized to null or 0.
     */
    public HockeyPlayer() {
        this.position = null;
        this.firstName = null;
        this.lastName = null;
        this.rating = 0;
    }

    /**
     * Returns the player's first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }


    /**
     * Returns the player's last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }


    /**
     * Returns the player's position.
     *
     * @return the position (C, LW, RW, LD, RD, G)
     */
    public String getPosition() {
        return position;
    }


    /**
     * Returns the player's rating.
     *
     * @return the rating (0-100)
     */
    public int getRating() {
        return rating;
    }
    
    /**
     * Sets the player's position.
     *
     * @param position the new position
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * Sets the player's rating.
     *
     * @param rating the new rating (0-100)
     */
    public void setRating(int rating) {
        this.rating = rating;
    }


    /**
     * Returns a string representation of the player.
     * Format: "position lastName, firstName rating"
     *
     * @return formatted string representation
     */
    public String toString() {
        return (position+" "+lastName+", "+firstName+" "+rating);
    }
}
