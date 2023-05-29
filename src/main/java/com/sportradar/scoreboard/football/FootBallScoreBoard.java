package com.sportradar.scoreboard.football;

import com.sportradar.scoreboard.ScoreBoard;
import com.sportradar.scoreboard.exception.TeamDoesNotExistException;

import java.util.*;

public class FootBallScoreBoard implements ScoreBoard<FootballMatch> {

    private final Set<FootballMatch> board = new TreeSet<>();

    @Override
    public FootballMatch startMatch(String homeTeam, String awayTeam) {
        String homeTeamStriped = homeTeam.strip();
        String awayTeamStriped = awayTeam.strip();

        Optional<FootballMatch> existedMatch = board
                .stream()
                .filter(match -> match.homeTeam().equals(homeTeamStriped) && match.awayTeam().equals(awayTeamStriped))
                .findFirst();

        if (existedMatch.isEmpty()) {
            FootballMatch footballMatch = new FootballMatch(homeTeamStriped, awayTeamStriped);
            board.add(footballMatch);
            return footballMatch;
        }
        return existedMatch.get();
    }

    @Override
    public FootballMatch updateMatch(FootballMatch match, int homeScore, int awayScore) {
        if (!board.remove(match)) {
            throw new TeamDoesNotExistException();
        }
        FootballMatch updated = match.update(homeScore, awayScore);
        board.add(updated);
        return updated;
    }

    @Override
    public boolean finishMatch(FootballMatch match) {
        if (!board.remove(match)) {
            throw new TeamDoesNotExistException();
        }
        return true;
    }

    @Override
    public List<FootballMatch> getSummary() {
        return List.copyOf(board);
    }
}
