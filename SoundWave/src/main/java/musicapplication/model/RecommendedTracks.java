package musicapplication.model;

public class RecommendedTracks {
	private String trackId;
	private String trackName;
	private String artistName;
	private int popularity;
	private double similarityScore;

	public RecommendedTracks(String trackId, String trackName, String artistName, int popularity,
			double similarityScore) {
		this.trackId = trackId;
		this.trackName = trackName;
		this.artistName = artistName;
		this.popularity = popularity;
		this.similarityScore = similarityScore;
	}

	// Getters
	public String getTrackId() {
		return trackId;
	}

	public String getTrackName() {
		return trackName;
	}

	public String getArtistName() {
		return artistName;
	}

	public int getPopularity() {
		return popularity;
	}

	public double getSimilarityScore() {
		return similarityScore;
	}

	// Convert popularity to stars (1-5)
	public int getStars() {
		return Math.min(5, Math.max(1, (int) Math.ceil(popularity / 20.0)));
	}
}
