package A1Q2;

/**
 * Represents an integer integral image, which allows the user to query the mean
 * value of an arbitrary rectangular subimage in O(1) time.  Uses O(n) memory,
 * where n is the number of pixels in the image.
 *
 * @author jameselder
 */
public class IntegralImage {

    private final int[][] integralImage;
    private final int imageHeight; 	// height of image (first index)
    private final int imageWidth;	// width of image (second index)

    /**
     * Constructs an integral image from the given input image.  
     *
     * @author jameselder
     * @param image The image represented
     * @throws InvalidImageException Thrown if input array is not rectangular
     */
    public IntegralImage(int[][] image) throws InvalidImageException {
        //implement this method.
    	  	
    	imageHeight = image.length;
    	
    	for (int i = 1; i <= imageHeight; i++)
    	{
    		if (image[0].length != image[i - 1].length)
    		{
    			throw new InvalidImageException();
    		}
    	}
    	
    	imageWidth = image[0].length;
    	integralImage = new int[imageHeight][imageWidth];
    	
    	for (int i = 0; i < imageHeight; i++)
    	{
    		for (int j = 0; j < imageWidth; j++)
    		{
    			//image[0][0]
    			if (i == 0 && j == 0)
    			{
    				integralImage[i][j] = image[i][j]; 
    			}
    			else
    			{	//at top row
    				if (i == 0)
    				{
    					integralImage[i][j] = image[i][j] + integralImage[i][j - 1];
    				}
    				else
    				{	//at left column
    					if (j == 0)
    					{
    						integralImage[i][j] = image[i][j] + integralImage[i - 1][j];
    					}
    					else
    					{	//everywhere else		actual			top				left				top-left
    						integralImage[i][j] =	image[i][j] + 	integralImage[i-1][j] +	integralImage[i][j - 1] -	integralImage[i - 1][j - 1];
    					}
    				}
    			}
    		}
    	}
    }

    /**
     * Returns the mean value of the rectangular sub-image specified by the
     * top, bottom, left and right parameters. The sub-image should include
     * pixels in rows top and bottom and columns left and right.  For example,
     * top = 1, bottom = 2, left = 1, right = 2 specifies a 2 x 2 sub-image starting
     * at (top, left) coordinate (1, 1).  
     *
     * @author jameselder
     * @param top top row of sub-image
     * @param bottom bottom row of sub-image
     * @param left left column of sub-image
     * @param right right column of sub-image
     * @return 
     * @throws BoundaryViolationException if image indices are out of range
     * @throws NullSubImageException if top > bottom or left > right
     */
    public double meanSubImage(int top, int bottom, int left, int right) throws BoundaryViolationException, NullSubImageException {
        //implement this method
    	
    	if (bottom > imageHeight || top < 0 || left < 0 || right > imageWidth)
    	{
    		throw new BoundaryViolationException();
    	}
    	if (top > bottom || left > right)
    	{
    		throw new NullSubImageException();
    	}
    	
    	int tL = 0;
    	int tR = 0;
    	int bL = 0;
    	int bR = integralImage[bottom][right];
    	double actual = 0;
    	double divider = 0;
    	
    	actual = (double) bR + tL - bL - tR;
    	
    	if (top != 0 && left != 0)
    	{
	    	tL = integralImage[top - 1][left - 1];
	    	tR = integralImage[top - 1][right];
	    	bL = integralImage[bottom][left - 1];
	    	actual = (double) bR + tL - bL - tR;
	    	divider = (bottom - top + 1) * (right - left + 1);
    	}
    	
    	if (top == 0 && left == 0)
    	{
    		actual = (double) bR;
    		divider = (bottom + 1) * (right + 1);
    	}
    	
    	if (top == 0 && left != 0)
    	{
    		tL = integralImage[top][left - 1];
    		tR = integralImage[top][right];
    		bL = integralImage[bottom][left];
        	actual = (double) bR + tL - bL - tR;
    		divider = (bottom + 1) * (right - left + 1);
    	}
    	
    	if (top != 0 && left == 0)
    	{
    		tL = integralImage[top - 1][left];
    		tR = integralImage[top - 1][right];
    		bL = integralImage[bottom][left];
        	actual = (double) bR + tL - bL - tR;
    		divider = (bottom - top + 1) * (right + 1);
    	}

    	return actual/divider; 
    }
}