package com.sportradar.scoreboard.football;

import com.sportradar.scoreboard.exception.InvalidScoreException;
import com.sportradar.scoreboard.exception.InvalidTeamNameException;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Objects;

import static java.lang.String.format;
import static java.time.LocalDateTime.now;

public record FootballMatch(String homeTeam, int homeScore, String awayTeam, int awayScore,
                            LocalDateTime startTime) implements Comparable<FootballMatch> {


    public FootballMatch(String homeTeam, int homeScore, String awayTeam, int awayScore, LocalDateTime startTime) {
        String homeTeamStriped = homeTeam.strip();
        String awayTeamStriped = awayTeam.strip();
        if (homeTeamStriped.isBlank() || awayTeamStriped.isBlank()) {
            throw new InvalidTeamNameException(format("Can't start the match, because params homeTeam: %s or awayTeam: %s can't be empty", homeTeamStriped, awayTeamStriped));
        }
        if (homeTeamStriped.equalsIgnoreCase(awayTeamStriped)) {
            throw new InvalidTeamNameException(format("Can't start the match, home and away teams have the same names. Please check the params: homeTeam: %s or awayTeam: %s can't be empty", homeTeamStriped, awayTeamStriped));
        }
        if (homeScore < 0 || awayScore < 0) {
            throw new InvalidScoreException(format("Score can't be negative. Please check the input params: homeScore: %s and awayScore: %s", homeScore, awayScore));
        }
        this.homeTeam = homeTeamStriped;
        this.awayTeam = awayTeamStriped;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.startTime = startTime;
    }

    public FootballMatch(String homeTeam, String awayTeam) {
        this(homeTeam, 0, awayTeam, 0, now());
    }

    FootballMatch update(int homeScore, int awayScore) {
        return new FootballMatch(this.homeTeam, homeScore, this.awayTeam, awayScore, this.startTime);
    }

    private int getMatchScore() {
        return homeScore + awayScore;
    }

    @Override
    public int compareTo(FootballMatch o) {
        return Comparator
                .comparing(FootballMatch::getMatchScore)
                .thenComparing(FootballMatch::startTime)
                .reversed()
                .compare(this, o);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FootballMatch that)) return false;

        if (!Objects.equals(homeTeam, that.homeTeam)) return false;
        return Objects.equals(awayTeam, that.awayTeam);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homeTeam, awayTeam);
    }
}
