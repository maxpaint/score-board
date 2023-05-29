package com.sportradar.scoreboard.football;

import com.sportradar.scoreboard.football.exception.InvalidScoreException;
import com.sportradar.scoreboard.football.exception.InvalidTeamNameException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

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

    @Test
    void footballMatch_HomeTeamCantBeEmpty_throwException() {
        assertThatThrownBy(() -> new FootballMatch(" ", "test2"))
                .isInstanceOf(InvalidTeamNameException.class)
                .hasMessageContaining("Can't start the match, because params homeTeam:  or awayTeam: test2 can't be empty");
    }

    @Test
    void footballMatch_HomeTeamScoreCantBeNegative_throwException() {
        assertThatThrownBy(() -> new FootballMatch("1", -2, "2", 3, LocalDateTime.now()))
                .isInstanceOf(InvalidScoreException.class)
                .hasMessageContaining("Score can't be negative. Please check the input params: homeScore: -2 and awayScore: 3");
    }

    @Test
    void footballMatch_AwayTeamCantBeEmpty_throwException() {
        assertThatThrownBy(() -> new FootballMatch("team1", " "))
                .isInstanceOf(InvalidTeamNameException.class)
                .hasMessageContaining("Can't start the match, because params homeTeam: team1 or awayTeam:  can't be empty");
    }

    @Test
    void footballMatch_AwayTeamScoreCantBeNegative_throwException() {
        assertThatThrownBy(() -> new FootballMatch("1", 2, "2", -3, LocalDateTime.now()))
                .isInstanceOf(InvalidScoreException.class)
                .hasMessageContaining("Score can't be negative. Please check the input params: homeScore: 2 and awayScore: -3");
    }

    @Test
    void footballMatch_HameTeamAndAwayTeamHaveTheSameName_throwException() {
        assertThatThrownBy(() -> new FootballMatch("team1", 2, "team1", 3, LocalDateTime.now()))
                .isInstanceOf(InvalidTeamNameException.class)
                .hasMessageContaining("Can't start the match, home and away teams have the same names. Please check the params: homeTeam: team1 or awayTeam: team1 can't be empty");
    }
}