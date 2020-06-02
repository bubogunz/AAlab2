package lab2.model;

public class Distancies {
    public static Integer euclidean(double xa, double ya, double xb, double yb){
        return Integer.valueOf((int) (Math.sqrt(Math.pow(xa - xb, 2) + Math.pow(ya - yb, 2)) + 0.5));
    }

    public static Integer geo(double xa, double ya, double xb, double yb){
    	xa = toRad(xa);
    	ya = toRad(ya);
    	xb = toRad(xb);
    	yb = toRad(yb);
    	
        double RRR = 6378.388;

        double q1 = Math.cos( ya - yb );
        double q2 = Math.cos( xa - xb );
        double q3 = Math.cos( xa + xb );
        return (int) ( RRR * Math.acos( 0.5*((1.0+q1)*q2 - (1.0-q1)*q3) ) + 1.0);
    }
    
    private static Double toRad(double x) {
    	double PI = 3.141592;
    	int deg = (int) x;
    	double min = x - deg;
    	return PI * (deg + 5.0 * min/3.0)/180.0;
    }
}