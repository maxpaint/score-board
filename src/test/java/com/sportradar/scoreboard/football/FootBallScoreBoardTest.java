package com.sportradar.scoreboard.football;

import com.sportradar.scoreboard.ScoreBoard;
import com.sportradar.scoreboard.football.exception.TeamDoesNotExistException;
import com.sportradar.scoreboard.football.exception.InvalidScoreException;
import com.sportradar.scoreboard.football.exception.InvalidTeamNameException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class FootBallScoreBoardTest {

    ScoreBoard<FootballMatch> board = new FootBallScoreBoard();

    @Test
    void start_twoStringParams_returnMatch() {
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
    void start_CallWithEmptyParams() {
        assertThatThrownBy(() -> board.startMatch("", ""))
                .isInstanceOf(InvalidTeamNameException.class)
                .hasMessageContaining("Can't start the match, because params homeTeam:  or awayTeam:  can't be empty");
    }

    @Test
    void update_MatchWithHomeScore0AndAway2_returnUpdatedMatch() {
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

        assertThatThrownBy(() -> board.updateMatch(new FootballMatch("test1", "test2"), 0, 2)).isInstanceOf(TeamDoesNotExistException.class);

        assertThat(board.getSummary()).hasSize(0);
    }

    @Test
    void update_homeTeamHasNegativeScore_returnException() {
        FootballMatch match = board.startMatch("homeTeam", "awayTeam");

        assertThatThrownBy(() -> board.updateMatch(match, -1, 2))
                .isInstanceOf(InvalidScoreException.class)
                .hasMessageContaining("Score can't be negative. Please check the input params: homeScore: -1 and awayScore: 2");
    }

    @Test
    void update_awayTeamHasNegativeScore_returnException() {
        FootballMatch match = board.startMatch("homeTeam", "awayTeam");

        assertThatThrownBy(() -> board.updateMatch(match, 2, -1))
                .isInstanceOf(InvalidScoreException.class)
                .hasMessageContaining("Score can't be negative. Please check the input params: homeScore: 2 and awayScore: -1");
    }

    @Test
    void finish_existMatch_returnTrue() {
        FootballMatch match = board.startMatch("homeTeam", "awayTeam");

        assertThat(board.getSummary()).hasSize(1);

        assertThat(board.finishMatch(match)).isTrue();

        assertThat(board.getSummary()).hasSize(0);
    }

    @Test
    void finish_MatchDoesnotExist_returnFalse() {
        assertThat(board.getSummary()).hasSize(0);

        assertThatThrownBy(() -> board.finishMatch(new FootballMatch("t1", "t2")))
                .isInstanceOf(TeamDoesNotExistException.class);

        assertThat(board.getSummary()).hasSize(0);
    }

    @Test
    void getSummary_positiveScenario() {
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

        assertThat(summary.get(0).homeTeam()).isEqualTo("Uruguay");
        assertThat(summary.get(0).awayTeam()).isEqualTo("Italy");

        assertThat(summary.get(1).homeTeam()).isEqualTo("Spain");
        assertThat(summary.get(1).awayTeam()).isEqualTo("Brazil");

        assertThat(summary.get(2).homeTeam()).isEqualTo("Mexico");
        assertThat(summary.get(2).awayTeam()).isEqualTo("Canada");

        assertThat(summary.get(3).homeTeam()).isEqualTo("Argentina");
        assertThat(summary.get(3).awayTeam()).isEqualTo("Australia");

        assertThat(summary.get(4).homeTeam()).isEqualTo("Germany");
        assertThat(summary.get(4).awayTeam()).isEqualTo("France");

    }
}