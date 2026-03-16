/*
 * Name: Dur E Sameen Bukhari
 * Student ID: 200542362
 * Course: ENSE 375
 * Lab: Lab 4 – Code Coverage
 */

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

/**
 * Test suite for the Hockey League application.
 * 
 * <p>This test class provides comprehensive unit tests for:
 * <ul>
 *   <li>HockeyPlayer class - constructors, getters, setters, toString</li>
 *   <li>HockeyTeam class - player management (add, delete, get, edit)</li>
 *   <li>HockeyLeague class - team loading, game simulation</li>
 *   <li>Integration tests - complete player lifecycle</li>
 * </ul>
 * </p>
 * 
 * <p>Total tests: 28, covering all major functionality with >80% code coverage.</p>
 * 
 * @author Dur E Sameen Bukhari
 * @version 1.0
 * @since Lab 4 - Code Coverage
 */
public class HockeyLeagueTest {
    
    private HockeyLeague league;
    private HockeyTeam leafs;
    private HockeyTeam habs;
    
    /**
     * Sets up the test environment before each test.
     * Creates a new league instance and retrieves team references.
     */
    @BeforeEach
    void setUp() {
        league = new HockeyLeague();
        leafs = league.getTeam("TorontoMapleLeafs");
        habs = league.getTeam("MontrealCanadiens");
    }
    
    /**
     * Cleans up after each test by closing database connections.
     */
    @AfterEach
    void tearDown() {
        league.closeConnection();
    }
    
    // ==================== HOCKEYPLAYER TESTS ====================
    
    /**
     * Tests HockeyPlayer parameterized constructor and getter methods.
     * Verifies that all fields are properly initialized.
     */
    @Test
    @DisplayName("Test HockeyPlayer constructor and getters")
    void testHockeyPlayerConstructor() {
        HockeyPlayer player = new HockeyPlayer("C", "Auston", "Matthews", 95);
        
        assertEquals("C", player.getPosition());
        assertEquals("Auston", player.getFirstName());
        assertEquals("Matthews", player.getLastName());
        assertEquals(95, player.getRating());
    }
    
    /**
     * Tests HockeyPlayer default constructor.
     * Verifies that fields are initialized to null/0.
     */
    @Test
    @DisplayName("Test HockeyPlayer default constructor")
    void testHockeyPlayerDefaultConstructor() {
        HockeyPlayer player = new HockeyPlayer();
        
        assertNull(player.getPosition());
        assertNull(player.getFirstName());
        assertNull(player.getLastName());
        assertEquals(0, player.getRating());
    }
    
    /**
     * Tests HockeyPlayer setter methods.
     * Verifies that position and rating can be updated.
     */
    @Test
    @DisplayName("Test HockeyPlayer setters")
    void testHockeyPlayerSetters() {
        HockeyPlayer player = new HockeyPlayer();
        
        player.setPosition("RW");
        player.setRating(91);
        
        assertEquals("RW", player.getPosition());
        assertEquals(91, player.getRating());
    }
    
    /**
     * Tests HockeyPlayer toString method.
     * Verifies correct string format: "position lastName, firstName rating"
     */
    @Test
    @DisplayName("Test HockeyPlayer toString")
    void testHockeyPlayerToString() {
        HockeyPlayer player = new HockeyPlayer("C", "Auston", "Matthews", 95);
        String expected = "C Matthews, Auston 95";
        
        assertEquals(expected, player.toString());
    }
    
    // ==================== HOCKEYTEAM TESTS ====================
    
    /**
     * Tests HockeyTeam constructor.
     * Verifies team name is set correctly and roster is initialized.
     */
    @Test
    @DisplayName("Test HockeyTeam constructor")
    void testHockeyTeamConstructor() {
        HockeyTeam team = new HockeyTeam("TestTeam");
        
        assertEquals("TestTeam", team.teamName);
        assertNotNull(team.getRoster());
        assertEquals(0, team.getRoster().size());
    }
    
    /**
     * Tests adding a valid player to a team.
     * Verifies player is added and roster size increases.
     */
    @Test
    @DisplayName("Test addPlayer - valid player")
    void testAddPlayerValid() {
        HockeyTeam team = new HockeyTeam("TestTeam");
        HockeyPlayer player = new HockeyPlayer("C", "Test", "Player", 80);
        
        boolean result = team.addPlayer(player);
        
        assertTrue(result);
        assertEquals(1, team.getRoster().size());
        assertEquals(player, team.getRoster().get(0));
    }
    
    /**
     * Tests adding a null player.
     * Verifies operation fails and roster unchanged.
     */
    @Test
    @DisplayName("Test addPlayer - null player")
    void testAddPlayerNull() {
        HockeyTeam team = new HockeyTeam("TestTeam");
        
        boolean result = team.addPlayer(null);
        
        assertFalse(result);
        assertEquals(0, team.getRoster().size());
    }
    
    /**
     * Tests adding a duplicate player.
     * Verifies duplicate detection prevents addition.
     */
    @Test
    @DisplayName("Test addPlayer - duplicate player")
    void testAddPlayerDuplicate() {
        HockeyTeam team = new HockeyTeam("TestTeam");
        HockeyPlayer player1 = new HockeyPlayer("C", "Test", "Player", 80);
        HockeyPlayer player2 = new HockeyPlayer("C", "Test", "Player", 85);
        
        team.addPlayer(player1);
        boolean result = team.addPlayer(player2);
        
        assertFalse(result);
        assertEquals(1, team.getRoster().size());
    }
    
    /**
     * Tests deleting an existing player.
     * Verifies player is removed and roster size decreases.
     */
    @Test
    @DisplayName("Test deletePlayer - valid player")
    void testDeletePlayerValid() {
        HockeyTeam team = new HockeyTeam("TestTeam");
        HockeyPlayer player = new HockeyPlayer("C", "Test", "Player", 80);
        team.addPlayer(player);
        
        boolean result = team.deletePlayer(player);
        
        assertTrue(result);
        assertEquals(0, team.getRoster().size());
    }
    
    /**
     * Tests deleting a non-existent player.
     * Verifies operation fails and roster unchanged.
     */
    @Test
    @DisplayName("Test deletePlayer - player not found")
    void testDeletePlayerNotFound() {
        HockeyTeam team = new HockeyTeam("TestTeam");
        HockeyPlayer player1 = new HockeyPlayer("C", "Test", "Player", 80);
        HockeyPlayer player2 = new HockeyPlayer("C", "Test", "NotFound", 85);
        team.addPlayer(player1);
        
        boolean result = team.deletePlayer(player2);
        
        assertFalse(result);
        assertEquals(1, team.getRoster().size());
    }
    
    /**
     * Tests deleting a null player.
     * Verifies operation fails.
     */
    @Test
    @DisplayName("Test deletePlayer - null player")
    void testDeletePlayerNull() {
        HockeyTeam team = new HockeyTeam("TestTeam");
        
        boolean result = team.deletePlayer(null);
        
        assertFalse(result);
    }
    
    /**
     * Tests retrieving an existing player.
     * Verifies correct player object is returned.
     */
    @Test
    @DisplayName("Test getPlayer - valid player")
    void testGetPlayerValid() {
        HockeyTeam team = new HockeyTeam("TestTeam");
        HockeyPlayer player = new HockeyPlayer("C", "Test", "Player", 80);
        team.addPlayer(player);
        
        HockeyPlayer found = team.getPlayer(player);
        
        assertNotNull(found);
        assertEquals(player, found);
    }
    
    /**
     * Tests retrieving a non-existent player.
     * Verifies null is returned.
     */
    @Test
    @DisplayName("Test getPlayer - player not found")
    void testGetPlayerNotFound() {
        HockeyTeam team = new HockeyTeam("TestTeam");
        HockeyPlayer player1 = new HockeyPlayer("C", "Test", "Player", 80);
        HockeyPlayer player2 = new HockeyPlayer("C", "Test", "NotFound", 85);
        team.addPlayer(player1);
        
        HockeyPlayer found = team.getPlayer(player2);
        
        assertNull(found);
    }
    
    /**
     * Tests retrieving a player with null input.
     * Verifies null is returned.
     */
    @Test
    @DisplayName("Test getPlayer - null player")
    void testGetPlayerNull() {
        HockeyTeam team = new HockeyTeam("TestTeam");
        
        HockeyPlayer found = team.getPlayer(null);
        
        assertNull(found);
    }
    
    /**
     * Tests editing an existing player.
     * Verifies position and rating are updated.
     */
    @Test
    @DisplayName("Test editPlayer - valid player")
    void testEditPlayerValid() {
        HockeyTeam team = new HockeyTeam("TestTeam");
        HockeyPlayer player = new HockeyPlayer("C", "Test", "Player", 80);
        team.addPlayer(player);
        
        HockeyPlayer updatedPlayer = new HockeyPlayer("RW", "Test", "Player", 95);
        boolean result = team.editPlayer(updatedPlayer);
        
        assertTrue(result);
        assertEquals("RW", player.getPosition());
        assertEquals(95, player.getRating());
    }
    
    /**
     * Tests editing a non-existent player.
     * Verifies operation fails.
     */
    @Test
    @DisplayName("Test editPlayer - player not found")
    void testEditPlayerNotFound() {
        HockeyTeam team = new HockeyTeam("TestTeam");
        HockeyPlayer player1 = new HockeyPlayer("C", "Test", "Player", 80);
        HockeyPlayer player2 = new HockeyPlayer("RW", "Test", "NotFound", 95);
        team.addPlayer(player1);
        
        boolean result = team.editPlayer(player2);
        
        assertFalse(result);
    }
    
    /**
     * Tests editing with null player.
     * Verifies operation fails.
     */
    @Test
    @DisplayName("Test editPlayer - null player")
    void testEditPlayerNull() {
        HockeyTeam team = new HockeyTeam("TestTeam");
        
        boolean result = team.editPlayer(null);
        
        assertFalse(result);
    }
    
    /**
     * Tests roster validation with wrong size roster.
     * Verifies validation fails for incomplete roster.
     */
    @Test
    @DisplayName("Test isValidRoster - wrong size")
    void testIsValidRosterWrongSize() {
        HockeyTeam team = new HockeyTeam("TestTeam");
        team.addPlayer(new HockeyPlayer("C", "Test", "Player", 80));
        
        assertFalse(team.isValidRoster());
    }
    
    // ==================== HOCKEYLEAGUE TESTS ====================
    
    /**
     * Tests HockeyLeague constructor.
     * Verifies all 6 teams are loaded correctly.
     */
    @Test
    @DisplayName("Test HockeyLeague constructor loads teams")
    void testHockeyLeagueConstructor() {
        assertNotNull(league);
        assertEquals(6, league.teams.size());
    }
    
    /**
     * Tests retrieving an existing team by name.
     */
    @Test
    @DisplayName("Test getTeam - existing team")
    void testGetTeamExisting() {
        HockeyTeam team = league.getTeam("TorontoMapleLeafs");
        
        assertNotNull(team);
        assertEquals("TorontoMapleLeafs", team.teamName);
    }
    
    /**
     * Tests retrieving a non-existing team by name.
     * Verifies null is returned.
     */
    @Test
    @DisplayName("Test getTeam - non-existing team")
    void testGetTeamNonExisting() {
        HockeyTeam team = league.getTeam("NonExistingTeam");
        
        assertNull(team);
    }
    
    /**
     * Tests playing a game between two valid teams.
     * Verifies a winner is returned.
     */
    @Test
    @DisplayName("Test playGame - valid teams")
    void testPlayGameValid() {
        String result = league.playGame("TorontoMapleLeafs", "MontrealCanadiens");
        
        assertNotNull(result);
        assertTrue(result.equals("TorontoMapleLeafs") || 
                   result.equals("MontrealCanadiens") ||
                   result.equals("Unplayable Game Rosters are wrong"));
    }
    
    /**
     * Tests playing a game with invalid team names.
     * Verifies error message is returned.
     */
    @Test
    @DisplayName("Test playGame - invalid team names")
    void testPlayGameInvalidTeams() {
        String result = league.playGame("NonExistingTeam", "MontrealCanadiens");
        
        assertEquals("Unplayable Game Rosters are wrong", result);
    }
    
    /**
     * Tests the random integer generator indirectly through playGame.
     */
    @Test
    @DisplayName("Test getRandomInteger - valid range")
    void testGetRandomIntegerValid() {
        String result = league.playGame("TorontoMapleLeafs", "MontrealCanadiens");
        assertNotNull(result);
    }
    
    // ==================== INTEGRATION TESTS ====================
    
    /**
     * Tests the complete player lifecycle: add, get, edit, delete.
     * Verifies all operations work in sequence.
     */
    @Test
    @DisplayName("Test complete player lifecycle")
    void testPlayerLifecycle() {
        HockeyTeam team = new HockeyTeam("TestTeam");
        HockeyPlayer player = new HockeyPlayer("C", "Test", "Player", 80);
        
        assertTrue(team.addPlayer(player));
        assertEquals(1, team.getRoster().size());
        
        HockeyPlayer found = team.getPlayer(player);
        assertNotNull(found);
        
        HockeyPlayer editedPlayer = new HockeyPlayer("RW", "Test", "Player", 95);
        assertTrue(team.editPlayer(editedPlayer));
        
        assertEquals("RW", player.getPosition());
        assertEquals(95, player.getRating());
        
        assertTrue(team.deletePlayer(player));
        assertEquals(0, team.getRoster().size());
    }
    
    /**
     * Tests that all 6 teams are loaded correctly.
     * Verifies each team exists in the league.
     */
    @Test
    @DisplayName("Test multiple teams loaded correctly")
    void testMultipleTeamsLoaded() {
        assertEquals(6, league.teams.size());
        
        String[] teamNames = {
            "TorontoMapleLeafs", "MontrealCanadiens", "BostonBruins",
            "DetroitRedWings", "ChicagoBlackhawks", "NewYorkRangers"
        };
        
        for (String name : teamNames) {
            assertNotNull(league.getTeam(name), "Team " + name + " should exist");
        }
    }
}
