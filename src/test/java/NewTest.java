import static org.testng.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.json.JSONArray;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.qainfotech.tap.training.snl.api.Board;
import com.qainfotech.tap.training.snl.api.GameInProgressException;
import com.qainfotech.tap.training.snl.api.MaxPlayersReachedExeption;
import com.qainfotech.tap.training.snl.api.PlayerExistsException;

public class NewTest extends Board {
	public NewTest() throws FileNotFoundException, UnsupportedEncodingException, IOException {
		super();
	}

	Board board;

	@BeforeMethod
	public void create_board_object() throws FileNotFoundException, UnsupportedEncodingException, IOException {
		board = new Board();
	}

	@Test
	public void registeration_of_a_user() throws FileNotFoundException, UnsupportedEncodingException,
			PlayerExistsException, GameInProgressException, MaxPlayersReachedExeption, IOException {
		board.registerPlayer("bhavya");
	}

	@Test
	public void not_able_to_add_more_than_four_users() throws FileNotFoundException, UnsupportedEncodingException, PlayerExistsException, GameInProgressException, MaxPlayersReachedExeption, IOException {
		try {
		board.registerPlayer("xyz");
		board.registerPlayer("abc");
		board.registerPlayer("qaz");
		board.registerPlayer("plm");
		board.registerPlayer("fgj");
		}
		catch(MaxPlayersReachedExeption e) {
			Assert.assertEquals(e.getClass(), new MaxPlayersReachedExeption(null).getClass());
		}
	}
	@Test 
	public void adding_use_with_same_name_again() throws FileNotFoundException, UnsupportedEncodingException, PlayerExistsException, GameInProgressException, MaxPlayersReachedExeption, IOException {
		try {
		board.registerPlayer("surya");
		board.registerPlayer("surya");
		}
		catch( PlayerExistsException e) {
			Assert.assertEquals(e.getClass(), new PlayerExistsException(null).getClass());
		}
	}
	//@Test
	public void adding_user_when_game_has_already_been_started() {
		
	}
}