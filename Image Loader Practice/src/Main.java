
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

	BufferedImage img, img2;
	private JTextField txtPath;

	/**
	 * Where the picture given is drawn and Cropped.
	 */
	public void paint(Graphics g) {

		img = Crop(img);	//Crops given picture.

		g.drawImage(img, 0, 100, getWidth(), getHeight() - 100, this);
		
		getPreferredSize();

	}

	public Main(JFrame f) {
		BufferedImage imgloader = null;
		
		txtPath = new JTextField();
	    txtPath.setBounds(10, 10, 414, 21);
	    f.getContentPane().add(txtPath);
	    txtPath.setColumns(10);
	    
		
		JButton btnBrowse = new JButton("Browse");
		btnBrowse.setBounds(10, 41, 87, 23);
		f.getContentPane().add(btnBrowse);
		btnBrowse.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent e) {
		        JFileChooser fileChooser = new JFileChooser();
		 
		        // For Directory
		        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		 
		        // For File
		        //fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		 
		        fileChooser.setAcceptAllFileFilterUsed(false);
		 
		        int rVal = fileChooser.showOpenDialog(null);
		        if (rVal == JFileChooser.APPROVE_OPTION) {
		          txtPath.setText(fileChooser.getSelectedFile().toString());
		        }
		      }
		    });
		
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

		f.add(new Main(f));
		f.pack();
		f.setVisible(true);
	}
	
	/**
	 * Takes a picture given and crops white around it.
	 * @param i Picture to be cropped.
	 * @return Cropped picture.
	 */
	public BufferedImage Crop(BufferedImage i) {
		BufferedImage i2, i3;
		int x1 = 0;
		int y1 = 0;
		int w = i.getWidth();
		int h = i.getHeight();
		boolean res = false;

		// Width removal
		
		//While x < width of image
		for (int x = 0; x <= w - 1; x++) {
			
			//While y < height of image
			for (int y = 0; y <= h - 1; y++) {
				
				
				if (new Color(i.getRGB(x, y)).getRGB() == -1) {
					res = false;
				} else if (new Color(i.getRGB(x, y)).getRGB()<-1000000)  {
					res = true;
				}
				//If color isn't part of background
				if (res) {
					x1++;
					res = false;
					break;
				}
			}
		}
		i2 = new BufferedImage(x1, h, BufferedImage.TYPE_INT_RGB);
		x1 = 0;
		for (int x = 0; x <= w - 1; x++) {
			for (int y = 0; y <= h - 1; y++) {
				if (new Color(i.getRGB(x, y)).getRGB() == -1) {
					res = false;
				} else if (new Color(i.getRGB(x, y)).getRGB()<-1000000)  {
					res = true;
				}
				if (res) {
					for (int p = 0; p <= h - 1; p++) {
						i2.setRGB(x1, p, new Color(i.getRGB(x, p)).getRGB());
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
				System.out.println("(" + x + "," + y + ") : " + i2.getRGB(x, y));
				if (new Color(i2.getRGB(x, y)).getRGB()<-1000000)  {
					res = true;
				}
				if (res) {
					
					y1++;
					res = false;
					break;
				}
			}
		}
		i3 = new BufferedImage(x1, y1, BufferedImage.TYPE_INT_RGB);
		y1 = 0;
		for (int y = 0; y <= h - 1; y++) {
			for (int x = 0; x <= x1 - 1; x++) {
				if (new Color(i2.getRGB(x, y)).getRGB()<-1000000)  {
					res = true;
				}
				if (res) {
					for (int p = 0; p <= x1 - 1; p++) {
						i3.setRGB(p, y1, new Color(i2.getRGB(p, y)).getRGB());
					}
					y1++;
					res = false;
					break;
				}
			}
		}

		System.out.println("in formatImage");
		
		return i3;
	}
}
