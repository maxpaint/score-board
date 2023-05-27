package com.sportradar.scoreboard.football;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;


class FootBallScoreBoardTest {

    ScoreBoard<FootballMatch> board = new FootBallScoreBoard();

    @Test
    void start() {
        board.startMatch("homeTeam", "awayTeam");

        assertThat(board.getSummary()).hasSize(1);

        FootballMatch footballMatch = board.getSummary().get(0);
        assertThat(footballMatch.homeTeam()).isEqualTo("homeTeam");
        assertThat(footballMatch.awayTeam()).isEqualTo("awayTeam");
        assertThat(footballMatch.homeScore()).isEqualTo(0);
        assertThat(footballMatch.awayScore()).isEqualTo(0);

    }

    @Test
    void start_CallTwiceWithTheSameParams() {
        board.startMatch("homeTeam", "awayTeam");

        LocalDateTime startDate = board.getSummary().get(0).startTime();
        assertThat(board.getSummary()).hasSize(1);

        board.startMatch("homeTeam", "awayTeam");
        assertThat(board.getSummary()).hasSize(1);
        assertThat(startDate).isEqualTo(board.getSummary().get(0).startTime());

    }

    @Test
    void update() {
        FootballMatch match = board.startMatch("homeTeam", "awayTeam");

        assertThat(board.getSummary()).hasSize(1);
        FootballMatch footballMatch = board.getSummary().get(0);
        assertThat(footballMatch.homeScore()).isEqualTo(0);
        assertThat(footballMatch.awayScore()).isEqualTo(0);

        match = board.updateMatch(match, 0, 2);
        assertThat(match.homeScore()).isEqualTo(0);
        assertThat(match.awayScore()).isEqualTo(2);

        assertThat(board.getSummary()).hasSize(1);
        footballMatch = board.getSummary().get(0);
        assertThat(footballMatch.homeScore()).isEqualTo(0);
        assertThat(footballMatch.awayScore()).isEqualTo(2);
    }

    @Test
    void update_withNotExistsTeam_returnException() {

        assertThat(board.getSummary()).hasSize(0);

        assertThatThrownBy(() -> {
            board.updateMatch(new FootballMatch("test1", "test2"), 0, 2);
        }).isInstanceOf(FootballTeamDoesNotExist.class);

        assertThat(board.getSummary()).hasSize(0);
    }

    @Test
    void finish_existMatch_returnTrue() {
        FootballMatch match = board.startMatch("homeTeam", "awayTeam");

        assertThat(board.getSummary()).hasSize(1);

        assertThat(board.finishMatch(match)).isTrue();

        assertThat(board.getSummary()).hasSize(0);
    }

    @Test
    void finish_existMatch_returnFalse() {
        assertThat(board.getSummary()).hasSize(0);

        assertThat(board.finishMatch(new FootballMatch("", ""))).isFalse();

        assertThat(board.getSummary()).hasSize(0);
    }

    @Test
    void getSummary() {
        FootballMatch mc = board.startMatch("Mexico", "Canada");
        board.updateMatch(mc, 0, 5);
        FootballMatch sb = board.startMatch("Spain", "Brazil");
        board.updateMatch(sb, 10, 2);
        FootballMatch gf = board.startMatch("Germany", "France");
        board.updateMatch(gf, 2, 2);
        FootballMatch ui = board.startMatch("Uruguay", "Italy");
        board.updateMatch(ui, 6, 6);
        FootballMatch aa = board.startMatch("Argentina", "Australia");
        board.updateMatch(aa, 3, 1);

        List<FootballMatch> summary = board.getSummary();
        assertThat(summary).hasSize(5);

        assertThat(summary).element(0).extracting("homeTeam").isEqualTo("Uruguay");
        assertThat(summary).element(1).extracting("homeTeam").isEqualTo("Spain");
        assertThat(summary).element(2).extracting("homeTeam").isEqualTo("Mexico");
        assertThat(summary).element(3).extracting("homeTeam").isEqualTo("Argentina");
        assertThat(summary).element(4).extracting("homeTeam").isEqualTo("Germany");

    }
}