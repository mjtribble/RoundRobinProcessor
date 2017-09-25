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
     * @param stud_no 
     */
    public ProcessManager(int stud_no)
    {
        this.jobList1 = new ArrayList<>();
        this.jobList2 = new ArrayList<>();
        CreateProcessors(stud_no);
        CreateRandomJobs();
        CreateTwelveJobs();
    }
    
    private void CreateProcessors(int studentNo)
    {
        int processorNumber = (studentNo % 3) + 2;
        System.out.println("Processor Number = " +  processorNumber);
        
        for(int i = 0; i< processorNumber; i++)
        {
            this.processorList.add(new Processor());
        }
    }
    
    private void CreateRandomJobs()
    {
        for(int i = 0; i< 1000; i++)
        {
            int randomNum = ThreadLocalRandom.current().nextInt(1, 501);
            this.jobList1.add(new Job(i, randomNum));
        }
    }
    
    private void CreateTwelveJobs()
    {
        jobList2.add(new Job(4, 9));
        jobList2.add(new Job(15, 2));
        jobList2.add(new Job(18, 16));
        jobList2.add(new Job(20, 3));
        jobList2.add(new Job(26, 29));
        jobList2.add(new Job(29, 198));
        jobList2.add(new Job(35, 7));
        jobList2.add(new Job(45, 170));
        jobList2.add(new Job(57, 180));
        jobList2.add(new Job(83, 178));
        jobList2.add(new Job(88, 73));
        jobList2.add(new Job(95, 8));
    }
    
    
    
}
