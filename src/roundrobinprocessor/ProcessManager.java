/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roundrobinprocessor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
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
    private final double[] ttTimes;
    
    /**
     * This is the total number of processors determined by user input
     */
    private int totalProcessors;
    
    /**
     * This instantiates a new process manager
     * Creates a list of available processors
     * assigns and runs a given list of jobs
     * @param stud_no user specified student number
     * @param randomRuns number of time to run the random jobs to get stats
     */
    public ProcessManager(int stud_no, int randomRuns)
    {
        this.randomJobs = new ArrayList<>();
        this.jobList = new ArrayList<>();
        this.processorList = new ArrayList();
        this.ttTimes = new double[randomRuns];
        
        // Create Processor and Job lists
        CreateProcessors(stud_no);
        CreateRandomJobs();
        CreateTwelveJobs();
        
        // Run both job lists
        for(int i = 0; i< randomRuns; i++)
        {
            this.ttTimes[i] = this.run(jobList);
         }
        
//        for(int i = 0; i< randomRuns; i++)
//        {
//            ttTimes[i] = this.run(randomJobs);
//        }
        
        this.min( ttTimes );
        this.max( ttTimes );
        double average = this.average( ttTimes );
        this.stdDev( ttTimes, average );
        
    }
    
    /**
     * This assumes the job list is sorted by arrival time. 
     * @param jobs, the list of jobs to run
     */
    private double run(ArrayList<Job> jobs)
    {
        Job currentJob = jobs.get(0);
        double arrivalTime = currentJob.getArrivalTime();
        int currentProcessor = 0;
        double finishTime;

        System.out.println("Number of processors = "+ currentProcessor);
        finishTime = this.processorList.get(0).addJob(currentJob);

        for(int i = 1; i< jobs.size(); i++)
        {
            // SET JOB AND PROCESSOR
            currentJob = jobs.get(i);
            currentProcessor = (currentProcessor + 1)%totalProcessors;
            System.out.println("job # "+ currentJob.getJobNumber()+" is running on processor # "+ currentProcessor);
            
            // RUN
            double jobFinishTime = this.processorList.get(currentProcessor).addJob(currentJob);
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
    private void CreateRandomJobs()
    {
        for(int i = 0; i< 1000; i++)
        {
            int jobNum = i+1;
            double arrivalTime = i;
            double randomProcessingTime = ThreadLocalRandom.current().nextInt(1, 501);
            this.randomJobs.add(new Job(jobNum, arrivalTime, randomProcessingTime));
        }
//        System.out.println("Random Jobs\n");
//        this.printJobs(this.randomJobs);
    }
    
    /**
     * This creates 12 jobs specified by the assignment guidelines
     */
    private void CreateTwelveJobs()
    {
        jobList.add(new Job(1, 4, 9));
        jobList.add(new Job(2, 15, 2));
        jobList.add(new Job(3, 18, 16));
        jobList.add(new Job(4, 20, 3));
        jobList.add(new Job(5, 26, 29));
        jobList.add(new Job(6, 29, 198));
        jobList.add(new Job(7, 35, 7));
        jobList.add(new Job(8, 45, 170));
        jobList.add(new Job(9, 57, 180));
        jobList.add(new Job(10, 83, 178));
        jobList.add(new Job(11, 88, 73));
        jobList.add(new Job(12, 95, 8));
//        System.out.println("Twelve Jobs\n");
//        this.printJobs(this.jobList);
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
    
    private double totalTurnaroundTime(double arrival, double finish)
    {
        return finish - arrival;
    }
    
    private ArrayList<Job> sortByArrivalTime(ArrayList<Job> jobs)
    {
        //Arrays.sort(jobs);
//        Collections.sort(jobs, Ordering.natural().onResultOf(
//    new Function<Person, String>() {
//      public String apply(Person from) {
//        return from.getFirstName();
//      }
//    }));
        return jobs;
    }
    
    /**
     * This calculates the minimum turnaround time
     */
    private double min(double[]t)
    {
        Arrays.sort(t);
        double min = t[0];
        System.out.println("Min = " + min);
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
        System.out.println("Average = " + average);

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
    private void stdDev(double[] t, double avg)
    {
        double sd = 0;
        for(double i : t) {sd += Math.pow((i-avg), 2.0)/t.length;}
        sd = Math.sqrt(sd);
        System.out.println("Standard Deviation = " + sd);
    }
}
