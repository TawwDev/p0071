
package controller;

import java.util.ArrayList;
import java.util.List;
import model.Task;

public class Manager {
    private List<Task> listTask = new ArrayList<>();
    private Validation v = new Validation();
    //add
    public void addTask(){
        int id;
        if(listTask.isEmpty()){
            id = 1;
        } else{
            id = listTask.get(listTask.size() - 1).getId() + 1;
        }
        String requirementName = v.getString("Requirement Name:");
        int taskTypeID = v.getInt("Task Type:", 1, 4);
        String date = v.getDate("Date:");
        double planFrom = v.getDouble("From:", 8, 17);
        double planTo = v.getDouble("To:", 8.5, 17.5);
        while(!checkTime(planFrom, planTo)){
            System.err.println("Plan from must be less than Plan To, Please re-input.");
            planFrom = v.getDouble("From", 8, 17);
            planTo = v.getDouble("To:", 8.5, 17.5);
        }
        String assignee = v.getString("Assignee:");
        String reviewer = v.getString("Reviewer:");
        Task newTask = new Task(id, requirementName, taskTypeID, date, planFrom, planTo, assignee, reviewer);
        if(!checkDuplicate(newTask)){
            listTask.add(newTask);
            System.out.println("Add successfully");
        } else{
            System.out.println("Dupolicate task");
        }
    }
    
    public boolean checkTime(double from, double to){
        if(from < to){
            return true;
        }
        return false;
    }
    
    public boolean checkDuplicate(Task newTask){
        for (Task oldTask : listTask) {
            if(oldTask.getAssignee().equalsIgnoreCase(newTask.getAssignee()) && oldTask.getDate().equalsIgnoreCase(newTask.getDate())
               && oldTask.getPlanFrom() == newTask.getPlanFrom() && oldTask.getPlanTo() == newTask.getPlanTo() && 
               oldTask.getRequirementName().equalsIgnoreCase(newTask.getRequirementName()) && oldTask.getReviewer().equalsIgnoreCase(newTask.getReviewer())
               && oldTask.getTaskTypeID() == newTask.getTaskTypeID()
                    ){
                return true;
            }
        }
        return false;
    }
    
    //delete
    public void deleteTask(){
        int idDelete = v.getInt("ID:", 1, Integer.MAX_VALUE);
        Task task = getTaskById(idDelete);
        if(task == null){
            System.out.println("NOT FOUND");
            return;
        } else{
            listTask.remove(task);
            System.out.println("Delete successfully");
        }
    }
    
    public Task getTaskById(int id){
        for (Task task : listTask) {
            if(task.getId() == id){
                return task;
            }
        }
        return null;
    }
    
    public void getDataTasks(){
        if(listTask.isEmpty()){
            System.out.println("List task is empty");
            return;
        }
        System.out.println("----------------------------------Task--------------------------------------");
        System.out.printf("%-5s | %-12s | %-10s | %-12s | %-10s | %-10s | %-10s", "Id", "Name", "Task Type", "Date", "Time", "Assignee", "Reviewer");
        System.out.println("");
        for (Task task : listTask) {
            double time = getTime(task.getPlanFrom(), task.getPlanTo());
            String taskType = getTaskType(task.getTaskTypeID());
            System.out.printf("%-5d | %-12s | %-10s | %-12s | %-10s | %-10s | %-10s", task.getId(), task.getRequirementName(), taskType, task.getDate(), time, task.getAssignee(), task.getReviewer());
            System.out.println("");
        }          
    }
    
    public double getTime(double from, double to){
        double time = to - from;
        return time;
    }
    
    public String getTaskType(int choice){
        String s = null;
        switch(choice){
            case 1:
                s = "Code";
                break;
            case 2:
                s = "Test";
                break;
            case 3:
                s = "Design";
                break;
            case 4:
                s = "Review";
                break;
        }
        return s;
    }
}
