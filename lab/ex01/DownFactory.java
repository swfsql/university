public class DownFactory {

	public Downloader create() {
		return new ResourceDown();
	}
}
