package hello;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.jhlabs.image.BlurFilter;
import com.jhlabs.image.BoxBlurFilter;

@RestController
public class QRCodeDetectorController {

	@RequestMapping("/qrcodedetector")
	public String detect(@RequestParam("file") MultipartFile uploadedFileRef) throws IOException {

		BufferedImage img = prepareImage(uploadedFileRef);
		
		String result = null;
		try {
			result = readQRCode(img);
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
		
		return "File uploaded successfully! Result = "+result;
	}

	public BufferedImage prepareImage(MultipartFile uploadedFileRef) throws IOException {
		ImageIO.scanForPlugins();
		
		InputStream in = new ByteArrayInputStream(uploadedFileRef.getBytes());
		BufferedImage img = ImageIO.read(in);
		// pass to grayscale
		
//		GrayscaleFilter grayScaleFilter = new GrayscaleFilter();
//		BufferedImage graysScaleImage = grayScaleFilter.filter(img, null);
		
//		UnsharpFilter unsharpFilter = new UnsharpFilter();
//		BufferedImage unsharpedImage = unsharpFilter.filter(img, null);
		BlurFilter blurFilter = new BlurFilter();
		BufferedImage blurredImage = blurFilter.filter(img, null);
		
//		dumpToFile(blurredImage,"c:/temp/upload/blurred_"+uploadedFileRef.getOriginalFilename());
		
//		File outputfile = new File("c:/temp/upload/blured_"+uploadedFileRef.getOriginalFilename());
//		ImageIO.write(bluredImage, "tif", outputfile);
		
//		float[] matrix = {
//		        0.111f, 0.111f, 0.111f, 
//		        0.111f, 0.111f, 0.111f, 
//		        0.111f, 0.111f, 0.111f, 
//		    };
//
//		    BufferedImageOp op = new ConvolveOp( new Kernel(3, 3, matrix) );
//		    BufferedImage blurredImage = op.filter(img, null);
//			File outputfile = new File("c:/temp/upload/blurred_"+uploadedFileRef.getOriginalFilename());
//			ImageIO.write(blurredImage, "tif", outputfile);
			
//			float[] matrix2 = new float[100];
//			for (int i = 0; i < 10; i++)
//				matrix2[i] = 1.0f/400.0f;
//
//		    BufferedImageOp op2 = new ConvolveOp( new Kernel(10, 10, matrix2), ConvolveOp.EDGE_NO_OP, null );
//		    BufferedImage blurredImage2  = op2.filter(img, null);
//			File outputfile2 = new File("c:/temp/upload/blurred2_"+uploadedFileRef.getOriginalFilename());
//			ImageIO.write(blurredImage2, "tif", outputfile2);
//			float[] matrix2 = new float[100];
//			for (int i = 0; i < 10; i++)
//				matrix2[i] = 1.0f/400.0f;

		    BufferedImageOp op2 = new BoxBlurFilter(4, 4, 2);
		    BufferedImage blurredImage2  = op2.filter(img, null);
//		    dumpToFile(blurredImage2,"c:/temp/upload/blurred_box_4_4_2_"+uploadedFileRef.getOriginalFilename());
		    
		    op2 = new BoxBlurFilter(3, 3, 1);
		    BufferedImage blurredImage3 = op2.filter(img, null);
//		    dumpToFile(blurredImage3,"c:/temp/upload/blurred_box_3_3_1_"+uploadedFileRef.getOriginalFilename());
		    // sharpen
		    
		    // resize
		    
		    // decodee
		    
			return blurredImage3;
			
	}

	public String readQRCode(BufferedImage img) 			throws FileNotFoundException, IOException, NotFoundException {
		
		BinaryBitmap binaryBitmap = new BinaryBitmap(
				new HybridBinarizer(new BufferedImageLuminanceSource(img)));
		Result qrCodeResult = new MultiFormatReader().decode(binaryBitmap );
		return qrCodeResult.getText();
	}
	
	private void dumpToFile(BufferedImage img, String outputFilePath) throws IOException {
		File outputFile = new File(outputFilePath);
		ImageIO.write(img, "tif", outputFile);
	}
}







































