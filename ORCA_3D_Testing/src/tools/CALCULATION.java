/*******************************************************************************
 * /* *************************************************************************************
 *  * Copyright (C) Xueyi Zou - All Rights Reserved
 *  * Written by Xueyi Zou <xz972@york.ac.uk>, 2015
 *  * You are free to use/modify/distribute this file for whatever purpose!
 *  -----------------------------------------------------------------------
 *  |THIS FILE IS DISTRIBUTED "AS IS", WITHOUT ANY EXPRESS OR IMPLIED
 *  |WARRANTY. THE USER WILL USE IT AT HIS/HER OWN RISK. THE ORIGINAL
 *  |AUTHORS AND COPPELIA ROBOTICS GMBH WILL NOT BE LIABLE FOR DATA LOSS,
 *  |DAMAGES, LOSS OF PROFITS OR ANY OTHER KIND OF LOSS WHILE USING OR
 *  |MISUSING THIS SOFTWARE.
 *  ------------------------------------------------------------------------
 *  **************************************************************************************/
/*******************************************************************************/
/**
 * 
 */
package tools;


import sim.util.Double2D;

/**
 * @author Xueyi
 *
 */
public class CALCULATION 
{
	
	public final static double epsilon = 1.0e-6;

	/**providing calculation tool by some static methods
	 * 
	 */
	public CALCULATION() 
	{
		// TODO Auto-generated constructor stub
	}	
	
/****************************************************************************************************************************************/
		
	public static double det(Double2D v1, Double2D v2)
    {
        return v1.x * v2.y - v1.y * v2.x;
    }
	
    /*
     * a first point of a line
     * b second point of a line
     * c testing point
     */
	public static boolean leftOf(Double2D a, Double2D b, Double2D c)
    {
        return (-det(a.subtract(c), b.subtract(a))>epsilon);
    }
	
	public static boolean rightOf(Double2D a, Double2D b, Double2D c)
    {
        return (det(a.subtract(c), b.subtract(a))>epsilon);
    }
      
   public static void main(String[] args) 
	{

//		Double2D v1= new Double2D(1,1);
		Double2D v2= new Double2D(-1,1);
		Double2D v3= new Double2D(-1,-1);
//		Double2D v4= new Double2D(1,-1);
//		
//		Double2D v5= new Double2D(1,0);
//		Double2D v6= new Double2D(0,1);
//		Double2D v7= new Double2D(-1,0);
//		Double2D v8= new Double2D(0,-1);
//		
//		Double2D v= v3 ;

		
		System.out.println(rightOf(new Double2D(),v2, v3));
		
	}

}
