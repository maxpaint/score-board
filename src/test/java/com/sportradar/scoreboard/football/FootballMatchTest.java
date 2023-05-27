package com.sportradar.scoreboard.football;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class FootballMatchTest {

    @Test
    void compareTo_firstMatchHasGreaterScore_returnNegativeValue() {
        String date = "2023-05-27T12:04:13.216284";
        FootballMatch matchWithScore1 = new FootballMatch("homeTeam1", 0, "awayTeam1", 2, LocalDateTime.parse(date));

        FootballMatch matchWithScore0 = new FootballMatch("homeTeam1", 0, "awayTeam1", 0, LocalDateTime.parse(date));

        assertThat(matchWithScore1.compareTo(matchWithScore0)).isEqualTo(-1);
    }

    @Test
    void compareTo_matchesHaveTheSameScoreAndStartTime_return0() {
        String date = "2023-05-27T12:04:13.216284";
        FootballMatch matchWithScore1 = new FootballMatch("homeTeam1", 0, "awayTeam1", 0, LocalDateTime.parse(date));

        FootballMatch matchWithScore0 = new FootballMatch("homeTeam1", 0, "awayTeam1", 0, LocalDateTime.parse(date));

        assertThat(matchWithScore1.compareTo(matchWithScore0)).isEqualTo(0);
    }

    @Test
    void compareTo_firstMatchHasLowerScore_return1() {
        String date = "2023-05-27T12:04:13.216284";
        FootballMatch matchWithScore1 = new FootballMatch("homeTeam1", 0, "awayTeam1", 0, LocalDateTime.parse(date));

        FootballMatch matchWithScore0 = new FootballMatch("homeTeam1", 0, "awayTeam1", 2, LocalDateTime.parse(date));

        assertThat(matchWithScore1.compareTo(matchWithScore0)).isEqualTo(1);
    }

    @Test
    void compareTo_ScoreAreEqualSecondMatchStartedAfterFirst_returnNegativeValue() {
        String date = "2023-05-27T12:04:13.216284";
        FootballMatch matchWithScore1 = new FootballMatch("homeTeam1", 0, "awayTeam1", 2, LocalDateTime.parse(date));

        FootballMatch matchWithScore0 = new FootballMatch("homeTeam1", 0, "awayTeam1", 2, LocalDateTime.parse(date).plusHours(1));

        assertThat(matchWithScore1.compareTo(matchWithScore0)).isEqualTo(1);
    }

    @Test
    void compareTo_ScoreAreEqualSecondMatchStartedBeforeFirst_returnNegativeValue() {
        String date = "2023-05-27T12:04:13.216284";
        FootballMatch first = new FootballMatch("homeTeam1", 0, "awayTeam1", 2, LocalDateTime.parse(date).plusHours(1));

        FootballMatch second = new FootballMatch("homeTeam1", 0, "awayTeam1", 2, LocalDateTime.parse(date));

        assertThat(first.compareTo(second)).isEqualTo(-1);
    }

    @Test
    void testEquals_TwoMatchesHaveTheSameTeamName_returnTrue() {
        boolean result = new FootballMatch("homeTeam1", "awayTeam1").equals(new FootballMatch("homeTeam1", "awayTeam1"));
        assertThat(result).isTrue();
    }

    @Test
    void testEquals_TwoMatchesHaveDifferentTeamNames_returnFalse() {
        boolean result = new FootballMatch("homeTeam1", "awayTeam1").equals(new FootballMatch("homeTeam2", "awayTeam2"));
        assertThat(result).isFalse();
    }

    @Test
    void testEquals_FirstAndSecondMatchHaveTheSameHomeTeamNameAndDifferentAway_returnFalse() {
        boolean result = new FootballMatch("homeTeam1", "awayTeam1").equals(new FootballMatch("homeTeam1", "awayTeam2"));
        assertThat(result).isFalse();
    }

    @Test
    void testEquals_FirstAndSecondMatchHaveTheSameAwayTeamNameAndDifferentHome_returnFalse() {
        boolean result = new FootballMatch("homeTeam", "awayTeam").equals(new FootballMatch("homeTeam1", "awayTeam"));
        assertThat(result).isFalse();
    }
}