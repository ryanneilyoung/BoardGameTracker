package ca.ryanneilyoung.boardgametracker;

import android.graphics.Bitmap;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Ryan on 2017-10-13.
 */
public class BoardGameTest
{
	private final int BGG_ID = 13;
	private final String TYPE = "boardgame";
	private final String NAME = "Catan";
	private final Bitmap IMAGE = null;
	private final String HREF = "/boardgame/13/catan";
	private BoardGame boardGame;

	@Before
	public void setUp() throws Exception
	{
		boardGame = new BoardGame(BGG_ID, TYPE, NAME, IMAGE, HREF);
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
		assertEquals("Wrong Image", IMAGE, boardGame.getImage());
	}

	@Test
	public void canChangeImageID() throws Exception
	{
		//given
		Bitmap newImage = null;
		boardGame.setImage(newImage);

		//then
		assertEquals("ImageID was not changed", newImage, boardGame.getImage());
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