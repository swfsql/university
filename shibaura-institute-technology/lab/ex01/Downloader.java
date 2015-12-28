public interface Downloader {
	public String download (LinkStack ln, String url, String pathf, boolean blocking, String httpReg, String pre) throws Exception;
}
