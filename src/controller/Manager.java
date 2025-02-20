package controller;

import java.util.ArrayList;
import java.util.List;
import model.Task;
import model.TaskType;

public class Manager {

    private List<Task> listTask = new ArrayList<>();
    private Validation v = new Validation();

    private int gettaskTypeID() {
        System.out.println("-----Description of taskType----- ");
        System.out.println("1. Code");
        System.out.println("2. Test");
        System.out.println("3. Design");
        System.out.println("4. Review");
        int taskTypeID = v.getInt("Choose task type id:", 1, 4);
        return taskTypeID;
    }

    public String getTaskType(int choice) {
        String s = null;
        switch (choice) {
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

    //add
    public void addTask() {
        int id;
        if (listTask.isEmpty()) {
            id = 1;
        } else {
            id = listTask.get(listTask.size() - 1).getId() + 1;
        }
        TaskType taskType = new TaskType();
        String requirementName = v.getString("Requirement Name:");
        int taskTypeID = gettaskTypeID();
        String nameTaskType = getTaskType(taskTypeID);
        String date = v.getDate();
        double planFrom = v.getDouble("From:", 8, 17.5);
        double planTo = v.getDouble("To:", 8, 17.5);
        while (!checkTime(planFrom, planTo)) {
            System.err.println("Plan from must be less than Plan To, Please re-input.");
            planFrom = v.getDouble("From", 8, 17.5);
            planTo = v.getDouble("To:", 8, 17.5);
        }
        String assignee = v.getString("Assignee:");
        String reviewer = v.getString("Reviewer:");
        Task newTask = new Task(taskTypeID, nameTaskType, id, requirementName, date, planFrom, planTo, assignee, reviewer);
        if (!checkWorkingHours(newTask)) {
            System.out.println("Worker cannot work more than 8 hours per day.\nAdd fail!");
            return;
        }
        if (isTimeOverlap(newTask)) {
            System.out.println("Task time overlaps with another task.\nAdd fail!");
            return;
        }
        if (checkWorkingHours(newTask) && !isTimeOverlap(newTask)) {
            listTask.add(newTask);
            System.out.println("Add successfully");
        }
    }

    public boolean checkTime(double from, double to) {
        if (from < to) {
            return true;
        }
        return false;
    }

    public boolean checkWorkingHours(Task newTask) {
        double totalHours = 0;
        totalHours += (newTask.getPlanTo() - newTask.getPlanFrom());
        return totalHours <= 8.0;
    }

    public boolean isTimeOverlap(Task newTask) {
        for (Task task : listTask) {
            if (task.getAssignee().equalsIgnoreCase(newTask.getAssignee())
                    && task.getDate().equalsIgnoreCase(newTask.getDate())) {
                if (newTask.getPlanFrom() < task.getPlanTo() && newTask.getPlanTo() > task.getPlanFrom()) {
                    return true; // Trùng thời gian
                }
            }
        }
        return false;// Không trùng thời gian
    }

    //delete
    public void deleteTask() {
        int idDelete = v.getInt("ID:", 1, Integer.MAX_VALUE);
        Task task = getTaskById(idDelete);
        if (task == null) {
            System.out.println("NOT FOUND");
            return;
        } else {
            listTask.remove(task);
            System.out.println("Delete successfully");
        }
    }

    public Task getTaskById(int id) {
        for (Task task : listTask) {
            if (task.getId() == id) {
                return task;
            }
        }
        return null;
    }

    public void getDataTasks() {
        if (listTask.isEmpty()) {
            System.out.println("List task is empty");
            return;
        }
        System.out.println("----------------------------------Task--------------------------------------");
        System.out.printf("%-5s | %-12s | %-10s | %-12s | %-10s | %-10s | %-10s", "Id", "Name", "Task Type", "Date", "Time", "Assignee", "Reviewer");
        System.out.println("");
        for (Task task : listTask) {
            double time = task.getPlanTo() - task.getPlanFrom();
            System.out.printf("%-5d | %-12s | %-10s | %-12s | %-10.1f | %-10s | %-10s", task.getId(), task.getRequirementName(), task.getTaskType().getName(), task.getDate(), time, task.getAssignee(), task.getReviewer());
            System.out.println("");
        }
    }

}
