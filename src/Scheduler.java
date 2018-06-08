
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author gabriel
 */
public class Scheduler extends Thread{
    
    Set<Process> processes = new TreeSet<>();
    private Boolean running = true;
    private volatile Boolean run = true;
    private Integer quantum = 0;
    private Process runningProcess;
    private Process runningProcessIndex;
    private Integer nextPid = 0;
    private Integer currentTime = 0;
    private final Integer TICK_TIME = 500;
    
    private long tempoTotalEspera = 0;
    private long totalProcessos = 0;
    
    
    private Integer maxCurrentPriority = 0;
    private Integer maxPriorityProcessIndex = 0;


    public void addProcess(Process p){
        processes.add(p);
        totalProcessos++;
        if(p.getPriority() > maxCurrentPriority){
            maxCurrentPriority = p.getPriority();
            maxPriorityProcessIndex = processes.size() - 1;
        }
        updateCounter();
    }

    public void setRun(Boolean run) {
        this.run = run;
    }
    
    public Integer getCurrentTime(){
        return currentTime;
    }
    
    private void updateCounter(){
        SISOPInterface.labelProcessCount.setText("Processes Count: " + processes.size());
        SISOPInterface.labelCurrentTime.setText("Current Time: " + currentTime);
    }
    
    public void setQuantum(Integer quantum) {
        // Caso mudou o quantum
        if (!this.quantum.equals(quantum)){
            // Reinicia a contagem de quantum de todos os processos
            processes.forEach((p) -> p.setCurrentQuantumCount(0));
        }
        this.quantum = quantum;
    }
    
    public void stopScheduler(){
        running = false;
    }
    
    public Integer nextPid(){
        return nextPid++;
    }
    
    @Override
    public void run() {
        while(running){
            try {
                runningProcess = null;
                // Procura o próximo processo da lista que esteja esperando
                for(Process p:processes){
                    if(!p.isFinished()){
                        runningProcess = p;
                        break;
                    }
                }
                runProccessNP();
                doTick();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    // Roda um o processo atual de maneira não preemptiva, até terminá-lo
    private int runProccessNP() throws Exception {
        if(runningProcess == null) return 0;
        int ticksCount = 0;
        // Enquanto não terminar esses processo, continua rodando ele
        while(!runningProcess.isFinished()){
            while(!run);
            doTick();
            runningProcess.runProcess();
            if(quantum > 0){
                if(runningProcess.getCurrentQuantumCount() >= quantum){
                    runningProcess.setCurrentQuantumCount(0);
                }
                runningProcess.
            }
        }
        processes.remove(runningProcess);
        return ticksCount;
    }
    
    private void nextRunningProccess(){
        processes.
    }
    
    private void doTick() throws Exception{
        processes.stream().filter((p) -> (!p.getPid().equals(runningProcess.getPid())))
                .forEach((p2) -> {
                    p2.incTempoEspera();
                    tempoTotalEspera++;
                });
        Thread.sleep(TICK_TIME);
        currentTime++;
        updateCounter();
        updtInfo();
    }
    
    private void updtInfo(){
        if(runningProcess == null){
            SISOPInterface.outputTextArea.setText("IDLE!"
                    + (totalProcessos == 0 ? "" : (" Tempo médio de espera: "+String.format("%.3f",(double)tempoTotalEspera/totalProcessos)+" ticks"))
            );
        }else{
            SISOPInterface.outputTextArea
                    .setText("RUNNING PROCESS PID = " 
                    + runningProcess.getPid());

            SISOPInterface.outputTextArea.append("\n");

            SISOPInterface.outputTextArea.append("INSERTION TIME = " 
                    + runningProcess.getInsertionTime());

            SISOPInterface.outputTextArea.append("\n");

            SISOPInterface.outputTextArea.append("REMAINING TIME = " 
                    + runningProcess.getRemainingTime());

            DefaultTableModel pTable = (DefaultTableModel)SISOPInterface.pList.getModel();
            pTable.setRowCount(0);
            processes.stream().sorted((p1,p2) -> {
                return p1.getPid() - p2.getPid();
            }).forEach(p -> {
                pTable.addRow(new Object[]{
                    p.getPid().equals(runningProcess.getPid()), 
                    p.getPid(),
                    p.getPriority(),
                    p.getInsertionTime(),
                    p.getRemainingTime(),
                    p.getTempoEspera(),
                    p.isFinished()}
                );
            });
        }
    }
    
    
}
