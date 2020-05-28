package lab2.test;

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Scanner;

public class TestTSP {
    public static void test(String algorithm, int example, int cost, int graphDimension){
        try {
			File myObj = new File("solutions.txt");
            Scanner myReader = new Scanner(myObj);
            String line = myReader.nextLine();
            for(int i = 0; i < example; i++)
                line = myReader.nextLine();

            Integer solution = Integer.valueOf(line.split(" ")[1]);
            boolean passed = false;

            switch (algorithm) {
                case "TSP":
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
                System.out.println("Test non passed.");

            myReader.close();
            
        } catch (IOException e) {
			e.printStackTrace();
		}
    }
}