package com.sportradar.scoreboard;

import java.util.List;

public interface ScoreBoard<T> {

    T startMatch(String homeTeam, String awayTeam);

    T updateMatch(T match, int homeScore, int awayScore);

    boolean finishMatch(T match);

    List<T> getSummary();




}
