import java.io.*;
import java.io.File;//This will be used to create File object
import java.io.IOException;//This will be used to catch IO Exception that may occur while reading/writing an image file
import java.awt.image.BufferedImage;//This will be used to create image object that will hold the image
import javax.imageio.ImageIO;//This will be used to perform read and write operation
import java.awt.*;

class encoder {
	public static void main(String args[]) throws IOException {
		int width, height, pix_count, k = 0, count = 0, flag, dis_count = 0, i, j;
		BufferedImage img = null;
		File f = null;
		try// read image file
		{
			f = new File("input/encoder_input.png");// image file path
			img = ImageIO.read(f);
			System.out.println("Reading image complete.");
		} catch (IOException e) {
			System.out.println("Error: " + e);
		}
		width = img.getWidth();// stores the width of the image
		height = img.getHeight();// stores the height of the image
		System.out.println("The width of input is " + width);
		System.out.println("The height of input is " + height);
		pix_count = width * height;
		int pixel[][] = new int[pix_count][5];
		for (j = 0; j < height; j++) {
			for (i = 0; i < width; i++) {

				Color c = new Color(img.getRGB(i, j));
				pixel[k][0] = count;
				pixel[k][1] = c.getRed();
				pixel[k][2] = c.getGreen();
				pixel[k][3] = c.getBlue();
				pixel[k++][4] = c.getAlpha();
				count++;
			}
		}
		System.out.println("Pixel Colors Found.");
		int discrete[][] = new int[pix_count][5];
		for (i = 0; i < pix_count; i++) {
			flag = 1;
			for (j = 0; j < dis_count; j++) {
				if ((pixel[i][1] == discrete[j][1]) && (pixel[i][2] == discrete[j][2])
						&& (pixel[i][3] == discrete[j][3]) && (pixel[i][4] == discrete[j][4])) {
					flag = 0;
					break;
				}

			}
			if (flag == 1) {
				discrete[dis_count][0] = dis_count;
				discrete[dis_count][1] = pixel[i][1];
				discrete[dis_count][2] = pixel[i][2];
				discrete[dis_count][3] = pixel[i][3];
				discrete[dis_count++][4] = pixel[i][4];
			}
		}
		System.out.println("Discrete Colors Found.");
		String content = width + "\n" + height + "\n" + dis_count + "\n";
		for (i = 0; i < dis_count; i++) {
			System.out.println("Discrete stuff:" + discrete[i][0] + " " + discrete[i][1] + " " + discrete[i][2] + " "
					+ discrete[i][3] + " " + discrete[i][4]);
			content = content + discrete[i][0] + " " + discrete[i][1] + " " + discrete[i][2] + " " + discrete[i][3]
					+ " " + discrete[i][4] + "\n";
		}
		count = 0;
		int j_prev = -1;
		for (i = 0; i < pix_count; i++) {
			for (j = 0; j < dis_count; j++) {
				if ((pixel[i][1] == discrete[j][1]) && (pixel[i][2] == discrete[j][2])
						&& (pixel[i][3] == discrete[j][3]) && (pixel[i][4] == discrete[j][4])) {
					if (i == 0) {
						j_prev = j;
						count++;
						break;
					} else if (j_prev == j) {
						count++;
						break;
					} else {
						content = content + j_prev + "$" + count + "\n";
						count = 1;
						j_prev = j;
						break;
					}
				}
			}
			if (i == (pix_count - 1)) {
				content = content + j_prev + "$" + count + "\n";
			}
		}
		System.out.println("Content prepared.");
		String fileName = "output/encoder_output.ajh";
		try {
			FileWriter fileWriter = new FileWriter(fileName);
			BufferedWriter bw = new BufferedWriter(fileWriter);
			bw.write(content);
			bw.close();
		} catch (IOException e) {
			System.out.println("Error writing to file '" + fileName + "'");
		}
		System.out.println("Content written");
	}
}
