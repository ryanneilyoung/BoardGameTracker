package ca.ryanneilyoung.boardgametracker;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Ryan on 2017-10-13.
 */
public class BoardGameTest
{
	private int BGG_ID = 13;
	private String TYPE = "boardgame";
	private String NAME = "Catan";
	private int IMAGE_ID = 2419375;
	private String HREF = "/boardgame/13/catan";
	private BoardGame boardGame;

	@Before
	public void setUp() throws Exception
	{
		boardGame = new BoardGame(BGG_ID, TYPE, NAME, IMAGE_ID, HREF);
	}

	@Test
	public void canGetBggID() throws Exception
	{
		assertEquals("Wrong ID", BGG_ID, boardGame.getBggID());

	}

	@Test
	public void canChangeBggID() throws Exception
	{
		//given
		int newBGGID = 100;
		boardGame.setBggID(newBGGID);

		//then
		assertEquals("ID was not changed", newBGGID, boardGame.getBggID());
	}

	@Test(expected = IllegalArgumentException.class)
	public void cantChangeToInvalidBggID() throws Exception
	{
		//given
		int newBGGID = -100;
		boardGame.setBggID(newBGGID);
	}

	@Test
	public void canGetType() throws Exception
	{
		assertEquals("Wrong Type", TYPE, boardGame.getType());
	}

	@Test
	public void canChangeType() throws Exception
	{
		//given
		String newType = "boardgameexpansion";
		boardGame.setType(newType);

		//then
		assertEquals("Type was not changed", newType, boardGame.getType());
	}

	@Test(expected = IllegalArgumentException.class)
	public void cantChangeToInvalidType() throws Exception
	{
		//given
		String newType = "TEST";
		boardGame.setType(newType);
	}

	@Test
	public void canGetName() throws Exception
	{
		assertEquals("Wrong Name", NAME, boardGame.getName());
	}

	@Test
	public void canChangeName() throws Exception
	{
		//given
		String newName = "TEST";
		boardGame.setName(newName);

		//then
		assertEquals("Name was not changed", newName, boardGame.getName());
	}

	@Test(expected = IllegalArgumentException.class)
	public void cantChangeToInvalidName() throws Exception
	{
		//given
		String newName = null;
		boardGame.setName(newName);
	}

	@Test
	public void canGetImageID() throws Exception
	{
		assertEquals("Wrong ImageID", IMAGE_ID, boardGame.getImageID());
	}

	@Test
	public void canChangeImageID() throws Exception
	{
		//given
		int newImageId = 100;
		boardGame.setImageID(newImageId);

		//then
		assertEquals("ImageID was not changed", newImageId, boardGame.getImageID());
	}

	@Test(expected = IllegalArgumentException.class)
	public void cantChangeToInvalidImageID() throws Exception
	{
		//given
		int newImageID = -100;
		boardGame.setImageID(newImageID);
	}

	@Test
	public void canGetHref() throws Exception
	{
		assertEquals("Wrong HREF", HREF, boardGame.getHref());
	}

	@Test
	public void canChangeHref() throws Exception
	{
		//given
		String newHref = "/boardgame/13/TEST";
		boardGame.setHref(newHref);

		//then
		assertEquals("HREF was not changed", newHref, boardGame.getHref());
	}

	@Test(expected = IllegalArgumentException.class)
	public void cantChangeToInvalidHref() throws Exception
	{
		//given
		String newHREF = "TEST";
		boardGame.setHref(newHREF);
	}

}