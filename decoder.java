import java.io.*;
import java.awt.image.BufferedImage;//This will be used to create image object that will hold the image
import javax.imageio.ImageIO;//This will be used to perform read and write operation
import java.awt.Color;

public class decoder {
	public static void main(String[] args) {
		String line = null, sub;
		char a;
		int width, height, dis_count, pix_count, j = 0, l, r, m, i, x = 0, y = -1, counter = 0;
		BufferedImage img;
		try {
			BufferedReader br = new BufferedReader(new FileReader("output/encoder_output.ajh"));// taking .ajh file as input
			line = br.readLine();// Now we will read the width,height and the no. of colors in our image
			width = Integer.parseInt(line);
			line = br.readLine();
			height = Integer.parseInt(line);
			line = br.readLine();
			dis_count = Integer.parseInt(line);
			pix_count = width * height;// calculating no. of pixels
			int discrete[][] = new int[dis_count][5];// array that stores color no. and argb values
			int pixel[][] = new int[pix_count][2];// array that stores pixel no. and the color no.
			int jixel[][] = new int[(width * height)][6];// array that stores x y and argb of each pixel
			img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			while (j < dis_count)// storing of the color mapping in an array
			{
				l = 0;
				r = 0;
				line = br.readLine();
				for (i = 0; i < line.length(); i++) {
					a = line.charAt(i);
					if (a == ' ') {
						m = i;
						sub = line.substring(l, m);
						discrete[j][r++] = Integer.parseInt(sub);
						l = m + 1;
					}
				}
				sub = line.substring(l, i);
				j++;
			}
			j = 0;
			line = br.readLine();
			while (line != null)// reading the full file and storing the value of each pixel and the
								// corresponding color no.
			{
				for (i = 0; i < line.length(); i++) {
					l = 0;
					a = line.charAt(i);
					if (a == '$') {
						m = i;
						sub = line.substring(l, m);
						pixel[j][0] = Integer.parseInt(sub);
						l = m + 1;
						sub = line.substring(l, line.length());
						pixel[j++][1] = Integer.parseInt(sub);
						break;
					}
				}
				line = br.readLine();
			}
			br.close();// closing the file
			m = 0;
			for (i = 0; i < j; i++) {
				for (l = 0; l < dis_count; l++) {
					if (pixel[i][0] == discrete[l][0]) {
						counter = pixel[i][1];
						for (r = 0; r < counter; r++) {
							if (m % width == 0) {
								y++;
								x = 0;
							}
							jixel[m][0] = x;
							jixel[m][1] = y;
							jixel[m][2] = discrete[l][1];
							jixel[m][3] = discrete[l][2];
							jixel[m][4] = discrete[l][3];
							jixel[m++][5] = discrete[l][4];
							x++;
						}
					}
				}
			}
			for (i = 0; i < m; i++) {
				Color clr = new Color(jixel[i][2], jixel[i][3], jixel[i][4], jixel[i][5]);
				img.setRGB(jixel[i][0], jixel[i][1], clr.getRGB());
			}
			ImageIO.write(img, "png", new File("output/decoder_output.png"));

			System.out.println("complete");
		} catch (FileNotFoundException e) {
			System.out.println("Unable to open file");
		} catch (IOException e) {
			System.out.println("Error reading file");
		}
	}
}
