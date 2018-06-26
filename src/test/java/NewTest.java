import static org.testng.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.qainfotech.tap.training.snl.api.Board;
import com.qainfotech.tap.training.snl.api.GameInProgressException;
import com.qainfotech.tap.training.snl.api.InvalidTurnException;
import com.qainfotech.tap.training.snl.api.MaxPlayersReachedExeption;
import com.qainfotech.tap.training.snl.api.NoUserWithSuchUUIDException;
import com.qainfotech.tap.training.snl.api.PlayerExistsException;

public class NewTest extends Board {
	public NewTest() throws FileNotFoundException, UnsupportedEncodingException, IOException {
		super();
	}

	Board board = new Board();

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
	@Test
	public void delete_user_without_registering() throws FileNotFoundException, UnsupportedEncodingException, NoUserWithSuchUUIDException {
		try {
		board.deletePlayer(UUID.randomUUID());
		}
		catch(NoUserWithSuchUUIDException e) {
			Assert.assertEquals(e.getClass(), new NoUserWithSuchUUIDException(null).getClass());
		}
		
	}
	@Test
	public void roll_dice_without_registering_player() throws FileNotFoundException, UnsupportedEncodingException, InvalidTurnException {
		try {
		board.rollDice(null);
		}
		catch(JSONException e) {
			Assert.assertEquals(e.getClass(),new JSONException(e).getClass());
		}
	}
	@Test
	public void roll_dice_with_random_uuid() throws InvalidTurnException, PlayerExistsException, GameInProgressException, MaxPlayersReachedExeption, IOException {
		board.registerPlayer("xyz");
		board.registerPlayer("abc");
		board.registerPlayer("qaz");
		board.registerPlayer("plm");
		UUID random = UUID.randomUUID();
		try {
		JSONObject uuid = board.rollDice(random);
		}
		catch(InvalidTurnException e) {
			Assert.assertEquals(e.getClass(),new InvalidTurnException(random).getClass());
		}
	}
	@Test
	public void game_is_working_or_not() throws FileNotFoundException, UnsupportedEncodingException, PlayerExistsException, GameInProgressException, MaxPlayersReachedExeption, IOException, InvalidTurnException {
		board.registerPlayer("xyz");
		board.registerPlayer("abc");
		board.registerPlayer("qaz");
		board.registerPlayer("plm");
		for(int i=0;i<4;i++) {
			UUID uid = (UUID) board.getData().getJSONArray("players").getJSONObject(i).get("uuid");
//			System.out.println("board is "+board.getData().getJSONArray("players"));
			board.rollDice(uid);
		}
	}
	@Test
	public void registration_of_user_after_game_has_begun() throws InvalidTurnException, PlayerExistsException, GameInProgressException, MaxPlayersReachedExeption, IOException {
		board.registerPlayer("xyz");
		board.registerPlayer("abc");
		board.registerPlayer("qaz");
		try {
		for(int i=0;i<4;i++) {
			UUID uid = (UUID) board.getData().getJSONArray("players").getJSONObject(i).get("uuid");
//			System.out.println("board is "+board.getData().getJSONArray("players"));
			board.rollDice(uid);
			try {
			board.registerPlayer("qwe");
			}
			catch(GameInProgressException e) {
				Assert.assertEquals(e.getClass(),new GameInProgressException().getClass());
			}
		}
		}
		catch(JSONException e) {
			Assert.assertEquals(e.getClass(),new JSONException(e).getClass());
		}
	}
	 
}