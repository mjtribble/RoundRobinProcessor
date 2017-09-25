/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roundrobinprocessor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author melodytribble
 */
public class ProcessManager {

    /**
     * This is the list of processor's available for jobs
     */
    private List<Processor> processorList; 

    /**
     * This is the list of jobs to be processed
     */ 
    private List<Job> jobList1;
    private List<Job> jobList2;
    
    /**
     * This instantiates a new process manager
     * Creates a list of available processors
     * assigns and runs a given list of jobs
     * @param stud_no user specified student number
     */
    public ProcessManager(int stud_no)
    {
        this.jobList1 = new ArrayList<>();
        this.jobList2 = new ArrayList<>();
        CreateProcessors(stud_no);
        CreateRandomJobs();
        CreateTwelveJobs();
    }
    
    /**
     * This creates a list of processor based on your student number
     * @param studentNo user input student number
     */
    private void CreateProcessors(int studentNo)
    {
        int processorNumber = (studentNo % 3) + 2;
        System.out.println("Processor Number = " +  processorNumber);
        
        for(int i = 0; i< processorNumber; i++)
        {
            this.processorList.add(new Processor());
        }
    }
    
    /**
     * This creates 1000 Jobs with random processing times
     */
    private void CreateRandomJobs()
    {
        for(int i = 0; i< 1000; i++)
        {
            int jobNum = i+1;
            int arrivalTime = i;
            int randomProcessingTime = ThreadLocalRandom.current().nextInt(1, 501);
            this.jobList1.add(new Job(jobNum, arrivalTime, randomProcessingTime));
        }
    }
    
    /**
     * This creates 12 jobs specified by the assignment guidelines
     */
    private void CreateTwelveJobs()
    {
        jobList2.add(new Job(1, 4, 9));
        jobList2.add(new Job(2, 15, 2));
        jobList2.add(new Job(3, 18, 16));
        jobList2.add(new Job(4, 20, 3));
        jobList2.add(new Job(5, 26, 29));
        jobList2.add(new Job(6, 29, 198));
        jobList2.add(new Job(7, 35, 7));
        jobList2.add(new Job(8, 45, 170));
        jobList2.add(new Job(9, 57, 180));
        jobList2.add(new Job(10, 83, 178));
        jobList2.add(new Job(11, 88, 73));
        jobList2.add(new Job(12, 95, 8));
    }
}
