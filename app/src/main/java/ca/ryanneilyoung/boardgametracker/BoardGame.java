package ca.ryanneilyoung.boardgametracker;

/**
 * Created by Ryan on 2017-09-24.
 */

public class BoardGame
{
	private int bggID;
	private String type;
	private String name;
	private int imageID;
	private String href;

	public BoardGame(int bggID, String type, String name, int imageID, String href) throws IllegalArgumentException
	{
		validBggID(bggID);
		validType(type);
		validName(name);
		validImageID(imageID);
		validHref(href);

		this.bggID = bggID;
		this.type = type;
		this.name = name;
		this.imageID = imageID;
		this.href = href;
	}

	public int getBggID()
	{
		return bggID;
	}

	public void setBggID(int bggID) throws IllegalArgumentException
	{
		validBggID(bggID);
		this.bggID = bggID;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type) throws IllegalArgumentException
	{
		validType(type);
		this.type = type;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name) throws IllegalArgumentException
	{
		validName(name);
		this.name = name;
	}

	public int getImageID()
	{
		return imageID;
	}

	public void setImageID(int imageID) throws IllegalArgumentException
	{
		validImageID(imageID);
		this.imageID = imageID;
	}

	public String getHref()
	{
		return href;
	}

	public void setHref(String href) throws IllegalArgumentException
	{
		validHref(href);
		this.href = href;
	}

	private void validBggID(int bggID) throws IllegalArgumentException
	{
		if(bggID <= 0)
		{
			throw new IllegalArgumentException("BGG ID must be greater than 0");
		}
	}

	private void validType(String type) throws IllegalArgumentException
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
				throw new IllegalArgumentException("Invalid type of BoardGame");
		}
	}

	private void validName(String name) throws IllegalArgumentException
	{
		if(name == null)
		{
			throw new IllegalArgumentException("Name must have value");
		}
	}

	private void validImageID(int imageID) throws IllegalArgumentException
	{
		if(imageID <= 0)
		{
			throw new IllegalArgumentException("Image ID must be greater than 0");
		}
	}

	private void validHref(String href) throws IllegalArgumentException
	{
		if(href == null)
		{
			throw new IllegalArgumentException("HREF must have value");
		}
		else if(href.split("/").length != 4)
		{
			throw new IllegalArgumentException("Invalid HREF format");
		}
	}


	@Override
	public String toString()
	{
		return getName();
	}
}
