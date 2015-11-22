import com.sun.java.swing.plaf.motif.resources.motif;

/**
 * Created by saratkiran on 4/1/14.
 */

import java.util.Arrays;
import java.util.Comparator;

public class main {
    public static int pow = 6;
    public static int length = (int)Math.pow(2,pow);
    // Class to hold the points
    private class Points /* implements Comparable<Points> */ {
        public double pointx;
        public double pointy;

        //constructor
        public Points(double pointx,double pointy){
            this.pointx = pointx;
            this.pointy = pointy;
        }
        /*
        @Override
        public int compareTo(Points o) {
            return new Double(this.pointx).compareTo(new Double(o.pointx));
        }
        */
    }

    public static void main(String[] args) {
        for(int l =0;l<15;l++){ // Number of trails

            Points[] input_points = new Points[length]; // declare the objects with specified length
            main d = new main();

            // input method 1 - random points   ----------------------------

            //d.uniform_hex(input_points, length);

            // --------------------------------------------------------------

            // input method 2 - hexagonal points  ----------------------------

            d.random(input_points, 0, 100,0, length);

            // --------------------------------------------------------------

            // input method 3 - mixed random (5%) - hexagonal (95%)  --------------------------
            /*double d_index = 0.95*length;
            int index = (int) d_index;
            //System.out.println(index);
            d.uniform_hex(input_points,index);
            d.random(input_points,0,100,index,length);*/
            // -----------------------------------------------------------------------------

            // List the input points ----------------------------------------------------
           /* System.out.println("Input");
            for(int k =0;k<length;k++){
                System.out.println(input_points[k].pointx + "   "+ input_points[k].pointy);
            }*/

            long startTime = System.nanoTime();    //Start the timer

            // Naive algorithm  ----------------------------------------------------------------
          // double ntimesnanswer = ntimesn(input_points,length);
            //System.out.println("Answers");
            //System.out.println(ntimesnanswer);
            // --------------------------------------------------------------------------------



            // Sort the inputs with respect to x axis

            Arrays.sort(input_points, new Comparator<Points>() {
                @Override
                public int compare(Points o1, Points o2) {
                    return Double.valueOf(o1.pointx).compareTo(o2.pointx);
                }
            });


/*System.out.println("After sort:");
            for(int k =0;k<length;k++){
                System.out.println(input_points[k].pointx + "   "+ input_points[k].pointy);
            }
*/

            // Divide and conquor slow algorithm ---------------------------------------------------
            //double slowalgo = slowalgo(input_points,0,length);
            //System.out.println("slow algo:");
            //System.out.println(slowalgo);
            // ------------------------------------------------------------------------------------

            // Divide and conquor fast algorithm ---------------------------------------------------
            double fastalgo = fastalgo(input_points,0,length);
            //System.out.println("fast algo");
            //System.out.println(fastalgo);
            //------------------------------------------------------------------------------------


            long stopTime = System.nanoTime();
            float elapsedTime = stopTime - startTime;
            System.out.println(elapsedTime/1000000);    // Calculate the time taken.

        }

    }

    // producing the uniform hexagonal points
    private  void uniform_hex(Points[] input_points, int len) {

        int cal;            // number of rows/columns
        if(pow%2 == 0 )
            cal = length/pow;
        else
            cal = length/(pow-1);

        double value = 100.0/cal;
        double offx = value/2;
        double offy = 100-98;
        double ini_x,ini_y;

        // select randomly with respective to number of points
        ini_x = (value/2 + Math.random( ) * offx);
        ini_y = (98 + Math.random( ) * offy);
        double distance= Math.min((100-ini_x/cal),(ini_y-0)/cal);
        int k=1;

        // save the points objects
        for (int i=0;i<cal && k <len;i++){
            for(int j=0;j<cal && k <len;j++){
                if(i==0 && j==0){
                    input_points[0] = new Points(ini_x,ini_y);
                }
                else{
                input_points[k] = new Points(ini_x+(distance*(j)),ini_y);
                k++;
                }
            }
            //System.out.println(k);
            if(i%2 == 0){
            ini_x = ini_x - distance/2;
            ini_y = ini_y - (Math.sqrt(3)/2)*distance;
            }
            else{
                ini_x = ini_x + distance/2;
                ini_y = ini_y - (Math.sqrt(3)/2)*distance;
            }
        }
    }

    // Function to generate random points
    public void random(Points[] input_points,double min, double max,int index,int length ){
        double diff = max - min;
        for(int i=index;i<length;i++){
            input_points[i] = new Points((min + Math.random( ) * diff),(min + Math.random( ) * diff));
        }
    }

    // Function for D and C slow with naive algorithm
    private static double slowalgo(Points[] input_points,int low, int high) {


        //Arrays.sort(input_points);
        //base case
        if(high <= low ){
            return Double.POSITIVE_INFINITY;
        }
        int midpoint = low +(high-low)/2;
        Points mid = input_points[midpoint];

        // recursive calls
        double left = slowalgo(input_points, low, midpoint);
        double right = slowalgo(input_points, midpoint+1, high);

        // find the minimum distance
        double smalldistance = Math.min(left,right);

        // get the slice and find the closest points using naive algorithm
        Points[] slice = new Points[high-low];
        int j =0;
        for (int i = low;i<high;i++){
            if((input_points[i].pointx - mid.pointx )<smalldistance){
                slice[j] = input_points[i];
                j++;
            }
        }
        // naive algorithm
        double ntimesnsol = ntimesn(slice,j);
        // finally get the minimum of all
        return Math.min(smalldistance,ntimesnsol);

    }

    // Function for D and C fast with 6 point algorithm
    private static double fastalgo(Points[] input_points,int low, int high) {
        //Arrays.sort(input_points);
        //base case
        if(high <= low ){
            return Double.POSITIVE_INFINITY;
        }

        int midpoint = low +(high-low)/2;
        Points mid = input_points[midpoint];
        // recursive calls
        double left = fastalgo(input_points, low, midpoint);
        double right = fastalgo(input_points, midpoint + 1, high);

        // find the minimum distance
        double smalldistance = Math.min(left,right);

        // get the slice and find the closest points using 6 point algorithm
        Points[] slice = new Points[high-low];
        int j =0;
        for (int i = low;i<high;i++){
            if((input_points[i].pointx - mid.pointx )<smalldistance){
                slice[j] = input_points[i];
                j++;
            }
        }
        Points[] slice_sort = new Points[j];
        for(int u =0;u<j;u++)
            slice_sort[u] = slice[u];

        // sort points objects with respect to y_points
        Arrays.sort(slice_sort, new Comparator<Points>() {
            @Override
            public int compare(Points o1, Points o2) {
                return Double.valueOf(o1.pointy).compareTo(o2.pointy);
            }
        });
        /*
        System.out.println("After sort:");
        for(int z =0;z<j;z++){
            System.out.println(slice_sort[z].pointx + "   "+ slice_sort[z].pointy);

        }
        */

        //find the distance between neighbouring points .. by geometric analysis inner loop runs only max of 6 times
        //so it is not o(n^2)
        double mindistance = smalldistance;
        //int count =0;
        for (int l = 0; l < j; l++){
            //count =0;
            for(int k=l+1;k<j && (slice_sort[l].pointy-slice_sort[k].pointy) < mindistance;k++){
               // count++;
                //System.out.println(count);
                mindistance = Math.min(mindistance,distance(slice_sort[l],slice_sort[k]));
            }

        }
        //System.out.println(count);
        //double ntimesnsol = ntimesn(slice,j);

        // return the distance
        return mindistance;

    }

    // Naive algorithm
    private static double ntimesn(Points[] input_points, int length) {
        double mindistance = Double.POSITIVE_INFINITY;

        for(int i=0;i<length;i++){
            for(int j=i+1;j<length;j++){
                mindistance = Math.min(distance(input_points[i],input_points[j]),mindistance);
            }
        }
        return mindistance;
    }

    // Method to find the distance of two points
    public static double distance(Points one,Points two){

        double ans = Math.sqrt(Math.pow((one.pointx-two.pointx),2) + Math.pow((one.pointy-two.pointy),2));
        //System.out.println(ans);
        return ans;
    }
    // Random values generation

}
