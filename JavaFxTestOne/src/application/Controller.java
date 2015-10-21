package application;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import static java.util.Comparator.comparing;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
/**
 * Music Manager 
 * Controller.java
 *
 *This is the Controller class for the GUI
 *that uses FX Scene Builder to generate FXML 
 *
 * @author Benjamin Mazenko
 */
public class Controller 
{
	//testing to see if this shows up
	// For the Third  update
	public JFileChooser selectFileLocation;
	File dir;
	ArrayList<ArrayList<Song>> artistsLists;
	ArrayList<Song> songs = new ArrayList<Song>();
	ArrayList<Album> albums = new ArrayList<Album>();
	ArrayList<Artist> artists = new ArrayList<Artist>();
	public ListView list; //List view container in GUI.
	
	
	public void playBtnClicked()
	{
		System.out.println("Playing");
	}
	
	public void artistBtn()
	{
		System.out.println("ARTIST");
	}
	
	/**
	 * Opens a JFile Chooser and assigns the selected
	 * folder to dir
	 */
	/*Currently this method contains call to findSongs and instantiateObjects
	 * this is for testing purposes. at some point there will be buttons
	 * associated with these methods and they will not need to me in SelectFile()
	*/
	public void selectFile()
	{
		// Instantiate new JFile Chooser
		selectFileLocation = new JFileChooser();
		selectFileLocation.setCurrentDirectory( new java.io.File("."));
		selectFileLocation.setDialogTitle("Select Library");
		selectFileLocation.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY);
		selectFileLocation.setAcceptAllFileFilterUsed(false);
		// If they select a file assign it to dir. else print NO SELECTION
		if (selectFileLocation.showOpenDialog(selectFileLocation) == JFileChooser.APPROVE_OPTION )
		{
			dir = selectFileLocation.getSelectedFile();
		}
		else
		{
			System.out.println("No Selection");
		}
		//Sets the directory array to the files in the selected dir
		File[] directory = dir.listFiles();
		// Digs into every file of dir for mp3 Songs
		for( File file: directory )
		{
			findSongs(file);
		}
		// make an ArrayList for each Artist Containing The songs for that artist
		artistsLists = makeArtistLists(songs);
		instantiateObjects(artistsLists);
		System.out.println(artists.size());
		System.out.println(albums.size());
		System.out.println(songs.size());
	// Test to see if Artist, Album, and Song Objects are working properly	
		for( Artist artist: artists )
		{
			System.out.println("-----------------------------------------------------");
			System.out.println("Artist Name: " + artist.getArtistName());
			for( Album album: artist.getAlbums() )
			{
				
				System.out.println("Album :" + album.getAlbumName());
				for( Song song: album.getSongs() )
				{
					System.out.println(song.getSongTag().getTrack() + " " + song.getSongTag().getTitle());
				}
			}
		}
		ObservableList<Artist> items =FXCollections.observableArrayList (artists);
		list.setItems(items);
		
		list.setCellFactory( new Callback<ListView<Artist>, ListCell<Artist>>()
				{
					@Override
					public ListCell<Artist> call(ListView<Artist> list )
					{
						return new ArtistListName();
					}
				});
		
		
		
	}
	
	/**
	 * This finds all Mp3 Files in a folder and
	 * instantiates a Song Object for each one. 
	 * The Object is then added to the ArrayList songs
	 * @param folder
	 */
	private void findSongs(File folder)
	{
		// Recursive if statement to find all mp3s in a folder
		if( folder.isDirectory() )
		{
			File[] temp = folder.listFiles();
			for( File x: temp )
			{
				findSongs(x);
			}
		}
		else
		{
			// If the File is an Mp3 Instantiate a new Song object
		    if( folder.getName().endsWith(".mp3"))
		    {
		    	Mp3File mp3;
				try 
				{
					mp3 = new Mp3File(folder);
					ID3v2 idTag = mp3.getId3v2Tag();			
					Song song = new Song(idTag.getAlbum(), idTag.getAlbumArtist(), mp3.getFilename(), idTag);
					songs.add(song);
				} 
				catch (UnsupportedTagException e) 
				{
					e.printStackTrace();
				} 
				catch (InvalidDataException e)
				{
					e.printStackTrace();
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
		    }
		}
	}
	
	/**
	 * makeArtistLists is used to take an Array of Songs(songs), Sort it By Artist
	 * and then make an ArrayList of Songs for each Artist. These ArrayLists 
	 * are then put into an ArrayList and returned.
	 * @param songs
	 * @return ArrayList<ArrayList<Song>> 
	 */
	private ArrayList<ArrayList<Song>> makeArtistLists(ArrayList<Song> songs)
	{
		//Sort by Artist (song artist is currently Album Artist)
		songs.sort(Comparator.comparing(Song::getSongArtist));
		ArrayList<Song> temp = new ArrayList<Song>();
		ArrayList<ArrayList<Song>> list = new ArrayList<ArrayList<Song>>();
		//Base case
		for( int i =0 ; i < songs.size() ; i++ )
		{
			if( i == 0 )
			{
				temp.add(songs.get(i));
				
			}
			// If this song has the same artist as the last. add it to temp
			if( i != 0 && songs.get(i).getSongArtist().equals( songs.get(i - 1).getSongArtist()) )
			{
				temp.add( songs.get(i) );
			
			}
			// If the artist has changed. add all songs of the artist to list. clear temp and start
			// the new artist
			if( i != 0 && !(songs.get(i).getSongArtist().equals( songs.get(i - 1).getSongArtist())) )
			{
				list.add(new ArrayList<Song>(temp));
				temp.clear();
				temp.add( songs.get(i) );
			}
			//At the ends create the last artist.
			if( i == songs.size() - 1 )
			{
				list.add(new ArrayList<Song>(temp));
			}
		}
		return list;
	}
	
	/**
	 * This Method takes an Array List of ArrayLists of the type Song 
	 * and creates Artist, and Album objects. 
	 * @param list
	 */
	private void instantiateObjects(ArrayList<ArrayList<Song>> list )
	{
		for( ArrayList<Song> songs: list )
		{
			// Sort by album
			songs.sort(Comparator.comparing(Song::getSongAlbum));
			ArrayList<Album> tempAlbums = new ArrayList<Album>();
			ArrayList<Song> tempSongs = new ArrayList<Song>();
	
			for( int i = 0; i < songs.size(); i++ )
			{
				// base case
				if( i == 0 )
				{
					tempSongs.add(songs.get(i));
				}
				// if the song has the same album as the last add to temp songs
				if( i != 0 && songs.get(i).getSongAlbum().equals( songs.get(i - 1).getSongAlbum()) )
				{
					tempSongs.add(songs.get(i));
				}
				// If this is the start of a new album. Instantiate the prev album and start a new list.
				if( i != 0 && !(songs.get(i).getSongAlbum().equals( songs.get(i - 1).getSongAlbum())) )
				{
					Album album = new Album( new ArrayList<Song>(tempSongs), tempSongs.get(0).getSongTag().getAlbum(),  
					                         tempSongs.get(0).getSongTag().getAlbumArtist(), tempSongs.get(0).getSongTag().getGenreDescription(), 
					                         tempSongs.get(0).getSongTag().getYear());
					tempAlbums.add(album);
					albums.add(album);
					tempSongs.clear();
					tempSongs.add(songs.get(i));
				}
				// if this is the last song and its a new album. instantiate the new album
				if ( (i == songs.size() - 1) && songs.get(i).getSongAlbum().equals( songs.get(i - 1).getSongAlbum()) )
				{
					Album album = new Album( new ArrayList<Song>(tempSongs), tempSongs.get(0).getSongTag().getAlbum(),  
	                         tempSongs.get(0).getSongTag().getAlbumArtist(), tempSongs.get(0).getSongTag().getGenreDescription(), 
	                         tempSongs.get(0).getSongTag().getYear());
					tempAlbums.add(album);
					albums.add(album);
					Artist artist = new Artist( new ArrayList<Album>(tempAlbums), tempAlbums.get(0).getAlbumArtist());
					artists.add(artist);
					tempAlbums.clear();
					tempSongs.clear();
				}
				
			}
		}
	}
	
	
	//another random comment
	static class ArtistListName extends ListCell<Artist>
	{
		@Override
		public void updateItem(Artist item, boolean empty)
		{
			super.updateItem(item, empty);
			if (item != null )
			{
				setText(item.getArtistName());
			}
		}
	}
}

		  

