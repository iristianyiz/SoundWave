package musicapplication.model;

public class Tracks {
	protected String trackId;
	protected String trackName;
	protected int albumId;
	protected int genreId;
	protected int popularity;
	protected int durationMs;
	protected boolean explicit;
	protected double danceability;
	protected double energy;
	protected int pitch;
	protected double loudness;
	protected int modality;
	protected double speechiness;
	protected double acousticness;
	protected double instrumentalness;
	protected double liveness;
	protected double valence;
	protected double tempo;
	protected int timeSignature;
	
	public Tracks(String trackId, String trackName, int albumId, int genreId, int popularity, int durationMs,
			boolean explicit, double danceability, double energy, int pitch, double loudness, int modality,
			double speechiness, double acousticness, double instrumentalness, double liveness, double valence,
			double tempo, int timeSignature) {
		this.trackId = trackId;
		this.trackName = trackName;
		this.albumId = albumId;
		this.genreId = genreId;
		this.popularity = popularity;
		this.durationMs = durationMs;
		this.explicit = explicit;
		this.danceability = danceability;
		this.energy = energy;
		this.pitch = pitch;
		this.loudness = loudness;
		this.modality = modality;
		this.speechiness = speechiness;
		this.acousticness = acousticness;
		this.instrumentalness = instrumentalness;
		this.liveness = liveness;
		this.valence = valence;
		this.tempo = tempo;
		this.timeSignature = timeSignature;
	}

	public Tracks(String trackId) {
		this.trackId = trackId;
	}

	public String getTrackId() {
		return trackId;
	}

	public void setTrackId(String trackId) {
		this.trackId = trackId;
	}

	public String getTrackName() {
		return trackName;
	}

	public void setTrackName(String trackName) {
		this.trackName = trackName;
	}

	public int getAlbumId() {
		return albumId;
	}

	public void setAlbumId(int albumId) {
		this.albumId = albumId;
	}

	public int getGenreId() {
		return genreId;
	}

	public void setGenreId(int genreId) {
		this.genreId = genreId;
	}

	public int getPopularity() {
		return popularity;
	}

	public void setPopularity(int popularity) {
		this.popularity = popularity;
	}

	public int getDurationMs() {
		return durationMs;
	}

	public void setDurationMs(int durationMs) {
		this.durationMs = durationMs;
	}

	public boolean isExplicit() {
		return explicit;
	}

	public void setExplicit(boolean explicit) {
		this.explicit = explicit;
	}

	public double getDanceability() {
		return danceability;
	}

	public void setDanceability(double danceability) {
		this.danceability = danceability;
	}

	public double getEnergy() {
		return energy;
	}

	public void setEnergy(double energy) {
		this.energy = energy;
	}

	public int getPitch() {
		return pitch;
	}

	public void setPitch(int pitch) {
		this.pitch = pitch;
	}

	public double getLoudness() {
		return loudness;
	}

	public void setLoudness(double loudness) {
		this.loudness = loudness;
	}

	public int getModality() {
		return modality;
	}

	public void setModality(int modality) {
		this.modality = modality;
	}

	public double getSpeechiness() {
		return speechiness;
	}

	public void setSpeechiness(double speechiness) {
		this.speechiness = speechiness;
	}

	public double getAcousticness() {
		return acousticness;
	}

	public void setAcousticness(double acousticness) {
		this.acousticness = acousticness;
	}

	public double getInstrumentalness() {
		return instrumentalness;
	}

	public void setInstrumentalness(double instrumentalness) {
		this.instrumentalness = instrumentalness;
	}

	public double getLiveness() {
		return liveness;
	}

	public void setLiveness(double liveness) {
		this.liveness = liveness;
	}

	public double getValence() {
		return valence;
	}

	public void setValence(double valence) {
		this.valence = valence;
	}

	public double getTempo() {
		return tempo;
	}

	public void setTempo(double tempo) {
		this.tempo = tempo;
	}

	public int getTimeSignature() {
		return timeSignature;
	}

	public void setTimeSignature(int timeSignature) {
		this.timeSignature = timeSignature;
	}

}