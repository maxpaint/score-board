package com.sportradar.scoreboard.football;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Objects;

import static java.time.LocalDateTime.now;

public record FootballMatch(String homeTeam, int homeScore, String awayTeam, int awayScore,
                            LocalDateTime startTime) implements Comparable<FootballMatch> {

    FootballMatch(String homeTeam, String awayTeam) {
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
