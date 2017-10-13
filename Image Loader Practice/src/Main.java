
/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

/**
 * This class demonstrates how to load an Image from an external file
 * Supposed to cut out background
 */
@SuppressWarnings("serial")
public class Main extends Component {

	BufferedImage img, img2, img3;

	/**
	 * General method to draw picture
	 */
	public void paint(Graphics g) {

		int x1 = 0;
		int y1 = 0;
		int w = img.getWidth();
		int h = img.getHeight();
		boolean res = false;

		// Width removal
		
		//While x < width of image
		for (int x = 0; x <= w - 1; x++) {
			
			//While y < height of image
			for (int y = 0; y <= h - 1; y++) {
				
				
				if (new Color(img.getRGB(x, y)).getRGB() == -1) {
					res = false;
				} else if (new Color(img.getRGB(x, y)).getRGB()<-1000000)  {
					res = true;
				}
				//If color isn't part of background
				if (res) {
					//from y to the end of picture, remove background
					for (int p = y; p <= h - 1; p++) {
						//img2.setRGB(x1,p,new Color(img.getRGB(x,p)).getRGB()); //crash
					}
					x1++;
					res = false;
					break;
				}
			}
		}
		img2 = new BufferedImage(x1, h, BufferedImage.TYPE_INT_RGB);
		x1 = 0;
		for (int x = 0; x <= w - 1; x++) {
			for (int y = 0; y <= h - 1; y++) {
				if (new Color(img.getRGB(x, y)).getRGB() == -1) {
					res = false;
				} else if (new Color(img.getRGB(x, y)).getRGB()<-1000000)  {
					res = true;
				}
				if (res) {
					for (int p = 0; p <= h - 1; p++) {
						img2.setRGB(x1, p, new Color(img.getRGB(x, p)).getRGB());
					}
					x1++;
					res = false;
					break;
				}
			}
		}

		// height removal

		for (int y = 0; y <= h - 1; y++) {
			System.out.println("Y = " + y);
			for (int x = 0; x <= x1 - 1; x++) {
				System.out.println("(" + x + "," + y + ") : " + img2.getRGB(x, y));
				if (new Color(img2.getRGB(x, y)).getRGB()<-1000000)  {
					res = true;
				}
				if (res) {
					for (int p = 0; p <= x1 - 1; p++) {
					//	img3.setRGB(p, y1, new Color(img2.getRGB(p, y)).getRGB()); // crash

					}
					y1++;
					res = false;
					break;
				}
			}
		}
		img3 = new BufferedImage(x1, y1, BufferedImage.TYPE_INT_RGB);
		y1 = 0;
		for (int y = 0; y <= h - 1; y++) {
			for (int x = 0; x <= x1 - 1; x++) {
				if (new Color(img2.getRGB(x, y)).getRGB()<-1000000)  {
					res = true;
				}
				if (res) {
					for (int p = 0; p <= x1 - 1; p++) {
						img3.setRGB(p, y1, new Color(img2.getRGB(p, y)).getRGB());
					}
					y1++;
					res = false;
					break;
				}
			}
		}

		img = img3;

		System.out.println("in formatImage");

		g.drawImage(img, 0, 0, null);

	}

	public Main() {
		try {
			img = ImageIO.read(new File("strawberry.jpg"));
		} catch (IOException e) {
			System.out.println("Image unable to be read in");
		}

	}

	/**
	 * sets the frame to the same size of the image. Later on should have image
	 * change size with the frame.
	 */
	public Dimension getPreferredSize() {
		if (img == null) {
			return new Dimension(100, 100);
		} else {
			return new Dimension(img.getWidth(null), img.getHeight(null));
		}
	}

	public static void main(String[] args) {

		//JFrame is the "frame" of the window
		JFrame f = new JFrame("Load Image Sample");

		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		f.add(new Main());
		f.pack();
		f.setVisible(true);
	}
}
