import java.awt.image.BufferedImage;

public class ObjectTracker {
	private RegionOfInterest ROI;
	private int[] colors;
	private int threshold;

	public ObjectTracker(RegionOfInterest ROI, int[] rgb, int threshold) {
		this.ROI = ROI;
		this.threshold = threshold;
		colors = rgb;
	}

	public void trackObject(BufferedImage image) {
		// take the average position of all the pixels that
		// are close enough to the colors array in this class
		int x = 0;
		int y = 0;
		int counter = 0;
		for (int r = ROI.getTop(); r < ROI.getSize() + ROI.getTop(); r++) {
			for (int c = ROI.getLeft(); c < ROI.getSize() + ROI.getLeft(); c++) {
				if (isPixel(image.getRGB(r, c))) {
					x += c;
					y += r;
					counter++;
				}
			}
		}
		if (counter == 0) {
			System.out.println("lost object");
		} else {
			ROI.setX(x/counter);
			ROI.setY(y/counter);
		}

		System.out.println(ROI);
	}

	public void isPixel(int rgb) {
		int red = (rgb >> 16) & 0xFF;
		int green = (rgb >> 8) & 0xFF;
		int blue = (rgb & 0xFF);

		// should be the difference of the individual ones
		int difference = Math.abs(red - colors[0]) +
			Math.abs(green - colors[1]) +
		   	Math.abs(blue - colors[2]);

		if (difference > threshold * 3) {
			return false;
		}

		return true;
	}
}