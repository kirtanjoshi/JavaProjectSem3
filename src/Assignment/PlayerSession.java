package Assignment;


public class PlayerSession {
    private static PlayerSession instance; // Static instance (Singleton)
    private int playerId;
    private String playerlevel;
    private int currentRound = 1; 

    private PlayerSession() {} // Private constructor to prevent multiple instances

    public static PlayerSession getInstance() {
        if (instance == null) {
            instance = new PlayerSession();
        }
        return instance;
    }

    public void setPlayerId(int playerId, String playerLevel) {
        this.playerId = playerId;
        this.playerlevel = playerLevel;
    }

    public String getPlayerlevel() {
        return playerlevel;
    }

    public int getPlayerId() {
        return playerId;
    }
    public int getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(int round) {
        this.currentRound = round;
    }
}

