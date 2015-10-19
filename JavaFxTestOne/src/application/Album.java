/**
 * Music Manager 
 * Album.java
 *
 *This class is used to create an Album object
 *Albums have a ArrayList of Songs which contains
 *a Song object for every track on the Album.
 *as well as some String attributes for 
 *sorting purposes
 *
 * @author Benjamin Mazenko
 */
package application;
import java.util.ArrayList;

public class Album
{
	private ArrayList<Song> songs;
	private String albumName;
	private String albumArtist;
	private String albumGenre;
	private String albumYear;
	
	// Constructor
	public Album(ArrayList<Song> songs, String albumName, String albumArtist, String albumGenre, String albumYear)
	{
		super();
		this.songs = songs;
		this.albumName = albumName;
		this.albumArtist = albumArtist;
		this.albumGenre = albumGenre;
		this.albumYear = albumYear;
	}
	
	public ArrayList<Song> getSongs()
	{
		return songs;
	}

	public String getAlbumName()
	{
		return albumName;
	}

	public String getAlbumArtist()
	{
		return albumArtist;
	}

	public String getAlbumGenre()
	{
		return albumGenre;
	}

	public String getAlbumYear()
	{
		return albumYear;
	}
}
