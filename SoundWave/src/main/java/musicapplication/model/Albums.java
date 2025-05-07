package musicapplication.model;



public class Albums {
	protected int albumId;
	protected String albumName;
	
	public Albums(int albumId, String albumName) {
		this.albumId = albumId;
		this.albumName = albumName;
	}
	
	public Albums(int albumId) {
		this.albumId = albumId;
	}
	
	public Albums(String albumName) {
		this.albumName = albumName;
	}

	public int getAlbumId() {
		return albumId;
	}

	public void setAlbumId(int albumId) {
		this.albumId = albumId;
	}

	public String getAlbumName() {
		return albumName;
	}

	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}
	
}