/**
 * Music Manager 
 * Song.java
 *
 *This class is used to create a Song object.
 *Song Objects have a file name and a songTag
 *the SongTag contains the Mp3 tag with metadata info
 *also Songs have some String attributes for sorting purposes 
 *
 * @author Benjamin Mazenko
 */
package application;
import com.mpatric.mp3agic.ID3v2;

public class Song
{
	private String songAlbum;
	private String songArtist;
	private String songFilename;
	private ID3v2 songTag;
	
	
	// Constructor
	public Song(String songAlbum, String songArtist, String songFilename, ID3v2 songId3v2Tag)
	{
		super();
		this.songAlbum = songAlbum;
		this.songArtist = songArtist;
		this.songFilename = songFilename;
		this.songTag = songId3v2Tag;
	}


	public String getSongAlbum()
	{
		return songAlbum;
	}


	public String getSongArtist()
	{
		return songArtist;
	}

	
	public String getSongFilename()
	{
		return songFilename;
	}


	public ID3v2 getSongTag()
	{
		return songTag;
	}
	
	
	
}
