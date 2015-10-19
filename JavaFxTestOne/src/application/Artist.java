/**
 * Music Manager 
 * Artist.java
 *
 *This class is used to create an Artist object
 *Artists have an ArrayList of Albums as well
 *as a name.
 *
 * @author Benjamin Mazenko
 */
package application;
import java.util.ArrayList;

public class Artist
{
	private ArrayList<Album> albums;
	private String artistName;
	
	
	public Artist(ArrayList<Album> albums, String artistName)
	{
		super();
		this.albums = albums;
		this.artistName = artistName;
	}


	public ArrayList<Album> getAlbums()
	{
		return albums;
	}


	public String getArtistName()
	{
		return artistName;
	}
	
	
}
