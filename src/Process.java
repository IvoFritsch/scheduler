/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author gabriel
 */
public class Process implements Comparable<Process>{
    private Integer pid;
    private Integer priority;
    private Integer totalTime;
    private Integer remainingTime;
    private Integer insertionTime;
    private Boolean finished;
    private int tempoEspera = 0;
    private int currentQuantumCount = 0;
    private boolean finishedQuantumCicle = false;

    public Process(Integer pid, Integer priority, Integer totalTime, Integer currentTime) {
        this.pid = pid;
        this.priority = priority;
        this.totalTime = totalTime;
        this.remainingTime = totalTime;
        this.finished = false;
        this.insertionTime = currentTime;
    }

    public void setCurrentQuantumCount(int currentQuantumCount) {
        this.currentQuantumCount = currentQuantumCount;
    }

    public int getCurrentQuantumCount() {
        return currentQuantumCount;
    }
    
    /**
     * @return the pid
     */
    public Integer getPid() {
        return pid;
    }

    /**
     * @return the priority
     */
    public Integer getPriority() {
        return priority;
    }

    /**
     * @return the totalTime
     */
    public Integer getTotalTime() {
        return totalTime;
    }

    /**
     * @return the remainingTime
     */
    public Integer getRemainingTime() {
        return remainingTime;
    }
    
    public Integer getInsertionTime(){
        return insertionTime;
    }
    
    public void runProcess(){
        if(remainingTime > 0){
            remainingTime--;
        }
        currentQuantumCount++;
        finished = remainingTime == 0;
    }
        
    public Boolean isFinished(){
        return finished;
    }

    public Integer getTempoEspera() {
        return tempoEspera;
    }
    
    public void incTempoEspera(){
        tempoEspera++;
    }

    @Override
    public int compareTo(Process p) {
        // Compara a prioridade
        if(priority < p.getPriority())
            return -1;
        if(priority > p.getPriority())
            return 1;
        // Caso as prioridades são iguais, compara a o PID
        if(pid - p.getPid() < 0) 
            return -1;
        else if(pid - p.getPid() > 0) 
            return 1;
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) return true;
        if(!(obj instanceof Process)) return false;
        return this.pid.equals(((Process)obj).getPid());
    }
    
    
}
