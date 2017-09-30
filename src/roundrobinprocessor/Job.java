/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roundrobinprocessor;

/**
 * This class defines the functionality of a Job
 * @author melodytribble
 */
public class Job {
    
    // These are the job's specific times
    private double arrivalTime, finishTime, processingTime, loadingTime, startTime;
    private int jobNumber;
    
    /**
     * Creates a new Job instance setting the arrival and processing times
     * @param arrive time the job arrives to be processed
     * @param processing time the job will take to finish one started processing
     */
    Job(int num, double arrive, double processing)
    {
        this.jobNumber = num;
        this.arrivalTime = arrive;
        this.startTime = 0;
        this.loadingTime = 0;
        this.processingTime = processing;
        this.finishTime = -1;
    }
    
    /**
     * This returns the jobs processing time
     * @return processing time
     */
    public double getProcessingTime()
    {
        return this.processingTime;
    }
    
    /**
     * This sets the job's processing time
     * @param time new processing time 
     */
    public void setProcessingTime(double time)
    {
        this.processingTime = time;
    }
    
    /**
     * This returns the jobs loading time
     * @return loading time
     */
    public double getLoadingTime()
    {
        return this.loadingTime;
    }
    
    /**
     * This sets the job's loading time
     * @param time new loading time 
     */
    public void setLoadingTime(double time)
    {
        this.loadingTime = time;
    }
    
    /**
     * This returns the job's number
     * @return processing time
     */
    public int getJobNumber()
    {
        return this.jobNumber;
    }
    
    /**
     * This sets the job's number
     * @param num new processing time 
     */
    public void setjobNumber(int num)
    {
        this.jobNumber = num;
    }

    /**
     * This returns the jobs arrival time
     * @return arrival time
     */
    public double getArrivalTime()
    {
        return this.arrivalTime;
    }
    
    /**
     * This sets the job's arrival time
     * @param time new arrival time 
     */
    public void setArrivalTime(double time)
    {
        this.arrivalTime = time;
    }
    
    /**
     * This returns the jobs finish time
     * @return finish time
     */
    public double getFinishTime()
    {
        return this.finishTime;
    }
    
    /**
     * This sets the job's finish time
     * @param time new finish time 
     */
    public void setFinishTime(double time)
    {
        this.finishTime = time;
    }
 
    /**
     * This returns the jobs start time
     * @return start time
     */
    public double getStartTime()
    {
        return this.startTime;
    }
    
    /**
     * This sets the job's start time
     * @param time new start time 
     */
    public void setStartTime(double time)
    {
        this.startTime = time;
    }
}
