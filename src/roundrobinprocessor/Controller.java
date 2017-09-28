/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roundrobinprocessor;

import java.util.Scanner;

/**
 *
 * @author melodytribble
 */
public class Controller {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        int number, randomRuns;
        try (Scanner in = new Scanner(System.in)) {
            System.out.println("Enter the last 4 digits of your student ID number.");
            number = in.nextInt();
            System.out.println("Enter the number of random runs");
            randomRuns = in.nextInt();
            in.close();
        }
        
        ProcessManager pm = new ProcessManager(number, randomRuns);
    }
    
}
