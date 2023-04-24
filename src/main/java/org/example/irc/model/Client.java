package org.example.irc.model;

/**
 * @author Tomas Kozakas
 */
public class Client {
    private String channel;
    private final String nick;
    private final String user;
    private int randNum;
    private int winCount = 0;
    private int guessCount = 0;


    public Client(String nick, String user) {
        this.nick = nick;
        this.user = user;
        randomiseNumber();
    }

    public void randomiseNumber() {
        randNum = ((int) (Math.random() * (100 - 1 + 1)) + 1);
    }

    public int getRandNum() {
        return randNum;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getNick() {
        return nick;
    }

    public int getWinCount() {
        return winCount;
    }

    public void setWinCount(int winCount) {
        this.winCount = winCount;
    }

    public int getGuessCount() {
        return guessCount;
    }

    public void setGuessCount(int guessCount) {
        this.guessCount = guessCount;
    }
}
