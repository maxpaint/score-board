package com.sportradar.scoreboard.football;

import java.util.*;

public class FootBallScoreBoard implements ScoreBoard<FootballMatch> {

    private final Set<FootballMatch> board = new TreeSet<>();
    @Override
    public FootballMatch startMatch(String homeTeam, String awayTeam) {
        Optional<FootballMatch> existedMatch = board
                .stream()
                .filter(match -> match.homeTeam().equals(homeTeam) && match.awayTeam().equals(awayTeam))
                .findFirst();

        if (existedMatch.isEmpty()) {
            FootballMatch footballMatch = new FootballMatch(homeTeam, awayTeam);
            board.add(footballMatch);
            return footballMatch;
        }

        return existedMatch.get();
    }

    @Override
    public FootballMatch updateMatch(FootballMatch match, int homeScore, int awayScore) {
        if (!board.remove(match)) {
           throw new FootballTeamDoesNotExist();
        }
        FootballMatch updated = match.update(homeScore, awayScore);
        board.add(updated);
        return updated;
    }

    @Override
    public boolean finishMatch(FootballMatch match) {
        return board.remove(match);
    }

    @Override
    public List<FootballMatch> getSummary() {
        return List.copyOf(board);
    }
}
