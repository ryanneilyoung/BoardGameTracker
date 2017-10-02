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

	public BoardGame(int bggID, String type, String name, int imageID, String href)
	{
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

	public void setBggID(int bggID)
	{
		this.bggID = bggID;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getImageID()
	{
		return imageID;
	}

	public void setImageID(int imageID)
	{
		this.imageID = imageID;
	}

	public String getHref()
	{
		return href;
	}

	public void setHref(String href)
	{
		this.href = href;
	}

	@Override
	public String toString()
	{
		return getName() + " " + "https://www.boardgamegeek.com" + getHref() + "";
	}
}
