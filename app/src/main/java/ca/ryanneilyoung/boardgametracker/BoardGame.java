package ca.ryanneilyoung.boardgametracker;

import android.graphics.Bitmap;

/**
 * Created by Ryan on 2017-09-24.
 */

class BoardGame
{
	private int bggID;
	private String type;
	private String name;
	private Bitmap image;
	private String href;

	public BoardGame(int bggID, String type, String name, Bitmap image, String href) throws InvalidBoardGameException
	{
		validBggID(bggID);
		validType(type);
		validName(name);
		validImage(image);
		validHref(href);

		this.bggID = bggID;
		this.type = type;
		this.name = name;
		this.image = image;
		this.href = href;
	}

	public int getBggID()
	{
		return bggID;
	}

	public void setBggID(int bggID) throws InvalidBoardGameException
	{
		validBggID(bggID);
		this.bggID = bggID;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type) throws InvalidBoardGameException
	{
		validType(type);
		this.type = type;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name) throws InvalidBoardGameException
	{
		validName(name);
		this.name = name;
	}

	public Bitmap getImage()
	{
		return image;
	}

	public void setImage(Bitmap image) throws InvalidBoardGameException
	{
		validImage(image);
		this.image = image;
	}

	public String getHref()
	{
		return href;
	}

	public void setHref(String href) throws InvalidBoardGameException
	{
		validHref(href);
		this.href = href;
	}

	private void validBggID(int bggID) throws InvalidBoardGameException
	{
		if(bggID <= 0)
		{
			throw new InvalidBoardGameException("BGG ID must be greater than 0");
		}
	}

	private void validType(String type) throws InvalidBoardGameException
	{
		switch(type)
		{
			case "boardgame":
				break;
			case "boardgameexpansion":
				break;
			case "boardgameaccessory":
				break;
			default:
				throw new InvalidBoardGameException("Invalid type of BoardGame");
		}
	}

	private void validName(String name) throws InvalidBoardGameException
	{
		if(name == null)
		{
			throw new InvalidBoardGameException("Name must have value");
		}
	}

	private void validImage(Bitmap image) throws InvalidBoardGameException
	{
		//TODO
	}

	private void validHref(String href) throws InvalidBoardGameException
	{
		if(href == null)
		{
			throw new InvalidBoardGameException("HREF must have value");
		}
		else if(href.split("/").length != 4)
		{
			throw new InvalidBoardGameException("Invalid HREF format");
		}
	}


	@Override
	public String toString()
	{
		return getName();
	}

	public class InvalidBoardGameException extends Exception
	{
		InvalidBoardGameException(String message)
		{
			super(message);
		}
	}
}

