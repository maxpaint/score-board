# score-board

The `ScoreBoard` interface is a basic contract for the scoreboard library.
`ScoreBoard` contains next methods:

`T startMatch(String homeTeam, String awayTeam);\` - For starting a new match, please call this method. Initial score for two team will be  0 – 0. As a result team will be added to the scoreboard. This method captures string parameters  `homeTeam` and `awayTeam`. They are required and can't be `null`.

**You can call this method several times with the same parameters, but only first match will be successfully added , next ones will be ignored.**

`T updateMatch(T match, int homeScore, int awayScore)` - This method updates existing Match in the board.

**All parameters in method are required.** 

**If scoreboard doesn't contain the Match, `TeamDoesNotExistException` exception will be triggered .**


`boolean finishMatch(T match)` - This method removed the Match from the board. Returns true when match was in the board and `TeamDoesNotExistException` exception if it wasn't.

`List<T> getSummary()` - Get a summary of matches in progress ordered by their total score. The matches with the  same total score will be returned ordered by the most recently started match in the scoreboard.


Right now this library contains only implementation of `FootBallScoreBoard`.
I did assumption that `List<T> getSummary()` will be called more often, then others.
That is why I have decided to use the TreeSet as the match holder and sort the board during the update and start match. 

As a result method `List<T> getSummary()` will have time complexity O(1).
Methods:
 - `T startMatch(String homeTeam, String awayTeam)`,
 - `T updateMatch(T match, int homeScore, int awayScore)`,
 - `boolean finishMatch(T match)`

will have O(log(N)).

`FootballMatch` - class contains validation of its parameters.
For example: 
- `homeTeam` and `awayTeam` - can't be empty.
- `homeTeam` and `awayTeam` - can't contain the same names. 
- `homeScore` and `awayScore` - can't be negative.



