/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roundrobinprocessor;

import java.util.ArrayList;
import java.util.List;

/**
 * This class defines a processor and it's functionality
 * @author melodytribble
 */
public class Processor {
    
    /**
     * This is the list of jobs the processor is currently running
     */
    private final List<Job> jobs; 

    /**
     * This is the current time. 
     * The time the last job finished and the time the new job will start.
     */
    private double processorClock;
    
    
    private double jobLoadingTime;
    
    /**
     * This creates a new processor and List of jobs
     */    
    public Processor() {
        this.jobs = new ArrayList<>();
        this.processorClock = 0;
        this.jobLoadingTime = 1;
    }
    
    /**
     * This adds a new job to the processor
     * Adds 1ms to the processing time
     * Runs the job
     * @param j the job to be run
     * @return double, the job's finish time
     */
    public double addJob(Job j)
    {
        this.jobs.add(j);
        j.setLoadingTime(this.jobLoadingTime);
        return runJob(j);
    }
    
    /**
     * This runs a job till job has completed
     * Sets the job's finish time.
     * @param j is the job to be run
     * @return integer, finish time for the job.
     */
    private double runJob(Job j)
    {
        ///Check to see that the job's arrival time is less than the processors running time or if we have to wait. 
        if(this.processorClock < j.getArrivalTime())
        {
            this.processorClock = j.getArrivalTime();
        }
        
        // Start Job
        j.setStartTime(processorClock);
        System.out.println("Job #" + j.getJobNumber()+ "'s start time =  " + j.getStartTime());

        //Finish Job
        this.processorClock += j.getProcessingTime() + 1;
        j.setFinishTime(processorClock);
        System.out.println("Job #" + j.getJobNumber()+ "'s finish time =  " + j.getFinishTime());
        
        //Return to ProcessManager for next job
        return j.getFinishTime();
    }
    
    public void resetClock()
    {
        this.processorClock = 0;
    }
}
