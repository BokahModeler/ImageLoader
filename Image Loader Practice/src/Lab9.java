// BE SURE TO COMMENT YOUR CODE
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * 
 * @author 
 */
public class Lab9 extends Application {
   /**
    * Invert an image by subtracting each RGB component from its max value
    * For example:
    *    rbg( 255, 255, 255 ) -- invert --> rbg(   0,   0,   0 )
    *    rbg(   0,   0,   0 ) -- invert --> rbg( 255, 255, 255 )
    *    rbg( 255, 110,  63 ) -- invert --> rbg(   0, 145, 192 )
    *    rbg(   0, 145, 192 ) -- invert --> rbg( 255, 110,  63 )
    * @param image - the image to be inverted, do not modify!
    * @return a new inverted image
    */
   public Image invertImage( Image image ) {
      int width = ( int ) image.getWidth( );
      int height = ( int ) image.getHeight( );
      WritableImage temp = new WritableImage( width, height );
      PixelWriter px = temp.getPixelWriter( );
      PixelReader pr = image.getPixelReader( );
      for ( int y = 0; y < height; y++ ) {
         for ( int x = 0; x < width; x++ ) {
            // YOUR CODE HERE
         }
      }
      return temp;
   }

   /**
    * Optional: BONUS 5 TECH POINTS
    * 
    * Save the image in the PPM P3 format
    * @see http://netpbm.sourceforge.net/doc/ppm.html
    * 
    * Don't forget to add a save button to the application!
    * 
    * @param filename
    * @param image
    * @throws FileNotFoundException
    */
   public void saveImage( String filename, Image image ) throws FileNotFoundException {
      // YOUR CODE HERE
   }

   /**
    * Load a PPM P3 file into an image
    * @see http://netpbm.sourceforge.net/doc/ppm.html
    * 
    * @param filename
    * @return
    * @throws FileNotFoundException
    */
   public Image loadImage( String filename ) throws FileNotFoundException {
      Scanner scanner = new Scanner( new File( filename ) );
      String magicNumber = scanner.next( ); // Should be P3
      scanner.nextLine( );
      if ( scanner.hasNext( ) && !scanner.hasNextInt( ) ) {
         scanner.nextLine( );
      }
      int width = scanner.nextInt( );
      int height = scanner.nextInt( );
      int colorScale = scanner.nextInt( );
      WritableImage image = new WritableImage( width, height );
      PixelWriter px = image.getPixelWriter( );
      for ( int y = 0; y < height; y++ ) {
         for ( int x = 0; x < width; x++ ) {
            px.setColor( x, y, Color.rgb( scanner.nextInt( ),
                  scanner.nextInt( ), scanner.nextInt( ) ) );
         }
      }
      return image;
   }

   /**
    * Fill in the Button Event Handlers!
    * 
    * @param primaryStage
    */
   public void start( Stage primaryStage ) throws Exception {
      // Get filename from command line arguments
      Parameters parameters = getParameters( );
      List<String> args = parameters.getRaw( );
      final String filename;
      if ( !args.isEmpty( ) ) {
         filename = args.get( 0 );
      } else {
         // default to baboon.ppm
         filename = "baboon.ppm";
      }
        
      // Layout buttons in a horizontal row
      HBox box = new HBox( );
      box.setAlignment( Pos.CENTER );
      Button reloadBtn = new Button( "Reload" );
      box.getChildren( ).add( reloadBtn );
      Button invertBtn = new Button( "Invert" );
      box.getChildren( ).add( invertBtn );
      Button grayBtn = new Button( "Grayscale" );
      box.getChildren( ).add( grayBtn );
      Button pixelateBtn = new Button( "Pixelate" );
      box.getChildren( ).add( pixelateBtn );

      // Load the specified image
      Image image = loadImage( filename );
      ImageView imageView = new ImageView( image );
      imageView.setSmooth( true );

      // Arrange ImageView and HBox in Border Layout
      BorderPane borderPane = new BorderPane( );
      borderPane.setCenter( imageView );
      borderPane.setBottom( box );
      BorderPane.setAlignment( box, Pos.CENTER );

      // Set the scene and make the window visible.
      Scene scene = new Scene( borderPane, Color.BLACK );
      primaryStage.setScene( scene );
      primaryStage.show( );
      
      // Create and register the button event handlers
      reloadBtn.setOnAction(e -> {
         try {
            imageView.setImage( loadImage( filename ) );
         } catch ( FileNotFoundException e1 ) {
            e1.printStackTrace();
         }
      });

      // IMPORTANT
      // Add button event handlers for invertBtn, grayBtn, and pixelateBtn
      
      // YOUR CODE HERE
      
   }

   /**
    * Launch the JavaFX application
    * @param args
    */
   public static void main( String [ ] args ) {
      launch( args );
   }
}
