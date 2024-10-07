/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Manager;
import controller.Validation;

public class Menu {
    private void menu(){
        System.out.println("========Task Program========");
        System.out.println("1.Add task");
        System.out.println("2.Delete task");
        System.out.println("3.Display task");
        System.out.println("4.Exit");
    }
    
    public void display(){
        Validation v = new Validation();
        Manager m = new Manager ();
        int n;
        
        do{
            menu();
            n = v.getInt("Your choice:", 1, 4);
            switch(n){
                case 1:
                    m.addTask();
                    break;
                case 2:
                    m.deleteTask();
                    break;
                case 3:
                    m.getDataTasks();
                    break;
                case 4:
                    break;
            }
        } while(n != 4);
    }
}
