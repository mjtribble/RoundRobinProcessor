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
    private List<Job> jobs; 

    /**
     * This is the current time. 
     * The time the last job finished and the time the new job will start.
     */
    int currentTime;
    
    /**
     * This creates a new processor and List of jobs
     */    
    public Processor() {
        this.jobs = new ArrayList<>();
    }
    
    /**
     * This adds a new job to the processor
     * Adds 1 ms to the processing time
     * Runs the job
     * @param j 
     */
    private void AddJob(Job j)
    {
        j.setProcessingTime(j.getProcessingTime() + 1);   
        this.jobs.add(j);
        RunJob(j);
    }
    
    /**
     * This runs a job till job has completed
     * Sets the job's finish time.
     * @param j 
     */
    private void RunJob(Job j)
    {
        this.currentTime = currentTime + j.getProcessingTime();
        j.setFinishTime(currentTime);
    }
}
