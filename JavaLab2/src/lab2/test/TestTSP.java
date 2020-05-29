package lab2.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.text.DecimalFormat;
import java.util.Scanner;

public class TestTSP {
    public static boolean test(String algorithm, int example, int cost, int graphDimension, FileWriter fw){
        boolean passed = false;
        try {
			File myObj = new File("solutions.txt");
            Scanner myReader = new Scanner(myObj);
            String line = myReader.nextLine();
            for(int i = 0; i < example; i++)
                line = myReader.nextLine();

            Integer solution = Integer.valueOf(line.split(" ")[1]);

            switch (algorithm) {
                case "HeldKarp":
                    if(cost == solution)
                        passed = true;
                    break;
                case "Heuristic":
                    if(cost <= solution*graphDimension*Math.log(graphDimension) && cost >= solution)
                        passed = true;
                    break;
                case "2Approx":
                    if(cost <= solution*2 && cost >= solution)
                        passed = true;
                    break;
                default:
                    myReader.close();
                    throw new InvalidParameterException("Wrong choice of algorithm");
            }

            if(passed)
                System.out.println("Test passed!");
            else
                System.out.println("Test NOT passed.");

            myReader.close();
            double percent = (cost - solution)/(double) solution * 100;
            DecimalFormat f = new DecimalFormat("##.00");
            fw.write(f.format(percent)+ "\n");
            
        } catch (IOException e) {
            e.printStackTrace();
		}
        return passed;
    }
}