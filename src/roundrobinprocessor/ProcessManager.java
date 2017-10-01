/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roundrobinprocessor;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class creates the jobs sequences and processor lists
 * It assigns a job to a processor
 * It keeps track of the turn around time statistics
 * @author melodytribble
 */
public class ProcessManager {

    /**
     * This is the list of processor's available for jobs
     */
    private final ArrayList<Processor> processorList; 

    /**
     * This will hold the 1000 randomly generated jobs
     */ 
    private final ArrayList<Job> randomJobs;
    
    /**
     * This will hold the 12 hard coded jobs for testing purposes
     */
    private final ArrayList<Job> jobList;
    
    /**
     * This will hold the turnaround times for multiple jobs 
     */
    private final double[] circularTTimes;
    
    /**
     * This will hold the turnaround times for multiple jobs 
     */
    private final double[] optimizedTTimes;
    
    /**
     * This holds the current clock values for each processor
     */
    private final double[] processorClocks;
    
    /**
     * This is the total number of processors determined by user input
     */
    private int totalProcessors;
    
    /**
     * This instantiates a new process manager
     * Creates a list of available processors
     * assigns and runs a given list of jobs
     * @param stud_no user specified student number
     * @param randomRuns number of time to run the random jobs to get statistics
     */
    public ProcessManager(int stud_no, int randomRuns)
    {
        this.randomJobs = new ArrayList<>();
        this.jobList = new ArrayList<>();
        this.processorList = new ArrayList();
        this.circularTTimes = new double[randomRuns];
        this.optimizedTTimes = new double[randomRuns];

        for(int i = 0; i<1000; i++)
        {
            randomJobs.add(new Job());
        }
        
        // Create Processor and Job lists
        CreateProcessors(stud_no);
        CreateTwelveJobs();
        
        this.processorClocks = new double[totalProcessors];

        
        // Run both job lists using the CIRCULAR method
        double circularTT = this.circularRun(jobList);
        
        for(int i = 0; i< randomRuns; i++)
        {
            CreateRandomJobs(this.randomJobs);
            circularTTimes[i] = this.circularRun(randomJobs);
        }
        
         // Run both job lists using the OPTIMIZED method
         double optimizedTT = this.optimizedRun(jobList);
        
        for(int i = 0; i< randomRuns; i++)
        {
            CreateRandomJobs(this.randomJobs);
            optimizedTTimes[i] = this.optimizedRun(randomJobs);
        }
        
        try {
            PrintWriter out = new PrintWriter(new FileWriter("output.txt"));

            out.println("Melody Tribble \nRound Robin Program Stats: \n\n");
            out.println("******** Twelve Jobs' Circular Turnaround Time ************* \n" + circularTT);
            out.println("\n******** Twelve Jobs' Optimized Turnaround Time  ************* \n" + optimizedTT);

            out.println("\n******** Circular Stats *************");
            out.println(getStats(circularTTimes));
            out.println("******** Optimized Stats *************");
            out.println(getStats(optimizedTTimes));

            out.close();
        }catch(IOException e1) {
            System.out.println("Error during reading/writing");
        }
        
    }
    
    /**
     * This assumes the job list is sorted by arrival time. 
     * @param jobs, the list of jobs to run
     */
    private double circularRun(ArrayList<Job> jobs)
    {
        Job currentJob = jobs.get(0);
        Processor currentProcessor = this.processorList.get(0);
        double arrivalTime = currentJob.getArrivalTime();
        double finishTime;
        int processorNumber = 0;

        System.out.println("Number of processors = "+ processorNumber);
        finishTime = currentProcessor.addJob(currentJob);

        for(int i = 1; i< jobs.size(); i++)
        {
            // SET JOB AND PROCESSOR
            currentJob = jobs.get(i);
            processorNumber = (processorNumber + 1)%totalProcessors;
            System.out.println("job # "+ currentJob.getJobNumber()+" is running on processor # "+ processorNumber);
            
            // RUN
            double jobFinishTime = this.processorList.get(processorNumber).addJob(currentJob);
            
            finishTime = jobFinishTime > finishTime ? jobFinishTime : finishTime;
            
            System.out.println("Jobs finish time  = " + finishTime);
        }
        
        processorList.forEach((p) -> { p.resetClock();});
        double turnaroundTime = this.totalTurnaroundTime(arrivalTime, finishTime);
        System.out.println("Turnaround Time  = " + turnaroundTime);
        return turnaroundTime;
    }
    
    /**
     * This assumes the job list is sorted by arrival time. 
     * @param jobs, the list of jobs to run
     */
    private double optimizedRun(ArrayList<Job> jobs)
    {
        Job currentJob = jobs.get(0);
        Processor currentProcessor;
        double arrivalTime = currentJob.getArrivalTime();
        double finishTime;
        int processorNum = 0;
        

        System.out.println("Number of processors = "+ processorNum);
        
        // STARTS the FIRST job on the FIRST PROCESSOR
        finishTime = this.processorList.get(0).addJob(currentJob);
        this.processorClocks[0] = finishTime;

        for(int i = 1; i< jobs.size(); i++)
        {
            // SET JOB AND PROCESSOR
            currentJob = jobs.get(i);
            processorNum = (processorNum + 1)%totalProcessors;
            currentProcessor = this.processorList.get(processorNum);
            
            // CHECKS IF THE PROCESSOR IS BUSY
            // if true, looks for the processor that is ready first
            if(currentProcessor.isBusy(currentJob.getArrivalTime()))
            {
                System.out.println("Processor # "+ processorNum+" is busy");
                System.out.println("Processor # "+ processorNum+"'s clock = "+ currentProcessor.getClock());                
                System.out.println("Job # "+ currentJob.getJobNumber()+"'s arrival time = "+ currentJob.getArrivalTime());

                processorNum = getMinClockTime();
                currentProcessor = this.processorList.get(processorNum);
            }
            
            System.out.println("job # "+ currentJob.getJobNumber()+" is running on processor # "+ processorNum);
            
            // RUN
            double jobFinishTime = currentProcessor.addJob(currentJob);
            this.processorClocks[processorNum] = currentProcessor.getClock();
            
            finishTime = jobFinishTime>finishTime ? jobFinishTime : finishTime;
            
            System.out.println("Jobs finish time  = " + finishTime);
        }
        
        processorList.forEach((p) -> { p.resetClock();});
        double turnaroundTime = this.totalTurnaroundTime(arrivalTime, finishTime);
        System.out.println("Turnaround Time  = " + turnaroundTime);
        return turnaroundTime;
    }
    
    /**
     * This creates a list of processor based on your student number
     * @param studentNo user input student number
     */
    private void CreateProcessors(int studentNo)
    {
        this.totalProcessors = (studentNo % 3) + 2;
        System.out.println("Processor Number = " +  totalProcessors);
        
        for(int i = 0; i< totalProcessors; i++)
        {
            this.processorList.add(new Processor());
        }
    }
    
    /**
     * This creates 1000 Jobs with random processing times from 1-500 ms
     */
    private void CreateRandomJobs(ArrayList<Job> jobs)
    {
        for(int i = 0; i< 1000; i++)
        {
            Job j = randomJobs.get(i);
            j.setjobNumber(i+1);
            j.setArrivalTime(i);
            j.setProcessingTime(ThreadLocalRandom.current().nextInt(1, 501));
        }
        
        this.sortByArrivalTime(this.randomJobs);
    }
    
    /**
     * This creates 12 jobs specified by the assignment guidelines
     */
    private void CreateTwelveJobs()
    {
        jobList.add(new Job(3, 18, 16));
        jobList.add(new Job(4, 20, 3));
        jobList.add(new Job(5, 26, 29));
        jobList.add(new Job(6, 29, 198));
        jobList.add(new Job(1, 4, 9));
        jobList.add(new Job(2, 15, 2));
        jobList.add(new Job(7, 35, 7));
        jobList.add(new Job(8, 45, 170));
        jobList.add(new Job(9, 57, 180));
        jobList.add(new Job(10, 83, 178));
        jobList.add(new Job(11, 88, 73));
        jobList.add(new Job(12, 95, 8));
        
        this.sortByArrivalTime(jobList);
    }
    
    private void printJobs(ArrayList<Job> jobs)
    {
        for(int i = 0; i < jobs.size(); i++)
        {
            Job currentJob = jobs.get(i);
            System.out.println("Job "+ currentJob.getJobNumber()
                               +", arrives - "+ currentJob.getArrivalTime()
                                     +", processes - " + currentJob.getProcessingTime()
                                        + ", finishes - "+ currentJob.getFinishTime());
        }
    }
    
    /**
     * This calculates a job sequence's turnaround time
     * @param arrival arrival time of the first job run
     * @param finish finish time of the last job run
     * @return turnaround time
     */
    private double totalTurnaroundTime(double arrival, double finish)
    {
        return finish - arrival;
    }
    
    /**
     * This sorts a job sequence by a job's arrival time
     * @param jobs the job sequence to be sorted
     * @return the sorted job sequence
     */
    private ArrayList<Job> sortByArrivalTime(ArrayList<Job> jobs)
    {
        Collections.sort(jobs);
        return jobs;
    }
    
    /**
     * This calculates the min, max, average, and standard deviation for the turnaround time data
     * @param ttTimes = array of turn around times each job list. 
     */
    private String getStats(double [] ttTimes)
    {
        double average = this.average( ttTimes );

        String stats = "Min = " + this.min( ttTimes )+"\n"
                     + "Max = " + this.max( ttTimes )+"\n"
                     + "Average = " + average+"\n"
                     + "Standard Deviation = " + this.stdDev( ttTimes, average )+"\n";
        return stats;
    }
    
    /**
     * This calculates the minimum turnaround time
     */
    private double min(double[]t)
    {
        Arrays.sort(t);
        double min = t[0];
        return min;
    }
    
    /**
     * This calculates the average turnaround time
     */
    private double average(double[] t)
    {
        double total = 0;
        double average;
        for(double i : t){ total += i; }
        average = total / (t.length);
        return average;
    }
    
    /**
     * This calculates the maximum turnaround time
     */
    private double max(double[] t)
    {
        Arrays.sort(t);
        double max = t[t.length -1];
        System.out.println("Max = " + max);
        return max; 
    }
 
    /**
     * This calculates the standard deviation in turnaround times
     */
    private double stdDev(double[] t, double avg)
    {
        double sd = 0;
        for(double i : t) {sd += Math.pow((i-avg), 2.0)/t.length;}
        sd = Math.sqrt(sd);
        return sd;
    }
    
    /**
     * @return This returns the processor number with the minimum clock time.
     */
    private int getMinClockTime()
    {
        int minIndex = 0;
        double minValue = this.processorClocks[0];
        
        for(int i = 0; i< this.processorClocks.length; i++)
        {
            if( processorClocks[i] < minValue )
            {
                minIndex = i;
            }
        }
        
        System.out.println("Processor # "+ minIndex + " has MIN clocktime at " + processorClocks[minIndex]);
        return minIndex;
    }
}