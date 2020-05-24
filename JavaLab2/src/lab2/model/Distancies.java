package lab2.model;

public class Distancies {
    public static Integer euclidean(double xa, double ya, double xb, double yb){
        return (int) Math.sqrt(Math.pow(xa - xb, 2) - Math.pow(ya - yb, 2));
    }

    public static Integer geo(double xa, double ya, double xb, double yb){
        double RRR = 6378.388;

        double q1 = Math.cos( ya - yb );
        double q2 = Math.cos( xa - xb );
        double q3 = Math.cos( xa + xb );
        return (int) ( RRR * Math.acos( 0.5*((1.0+q1)*q2 - (1.0-q1)*q3) ) + 1.0);
    }
}