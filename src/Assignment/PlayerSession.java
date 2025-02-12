package Assignment;



/**
 * The PlayerSession class implements a Singleton pattern to maintain player session data.
 * It stores the player's ID, level, name, and current round.
 */
public class PlayerSession {
    private static PlayerSession instance; // Static instance (Singleton)
    private int playerId;
    private String playerlevel;
    private String playerName;
    private int currentRound = 1; 

    /**
     * Returns the single instance of PlayerSession.
     * @return the singleton instance of PlayerSession
     */

    public static PlayerSession getInstance() {
        if (instance == null) {
            instance = new PlayerSession();
        }
        return instance;
    }

    
    /**
     * Sets the player ID, name, and level.
     * @param playerName the name of the player
     * @param playerId the unique ID of the player
     * @param playerLevel the level of the player
     */
    
    public void setPlayerId(String playerName , int playerId, String playerLevel) {
    	this.playerName = playerName;
        this.playerId = playerId;
        this.playerlevel = playerLevel;
        
    }
    /**
     * Returns the player's level.
     * @return the player's level
     */
    public String getPlayerlevel() {
        return playerlevel;
    }
    
    /**
     * Returns the player's name.
     * @return the player's name
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Returns the player's ID.
     * @return the player's ID
     */
    public int getPlayerId() {
        return playerId;
    }

    /**
     * Returns the current round number.
     * @return the current round
     */
    public int getCurrentRound() {
        return currentRound;
    }

    /**
     * Sets the current round number.
     * @param round the round number to set
     */
    public void setCurrentRound(int round) {
        this.currentRound = round;
    }
}

