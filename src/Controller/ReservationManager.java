package Controller;

import Entities.Reservation;
import Entities.Restaurant;
import Entities.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class ReservationManager {

    public static int addNewReservation(String name, String contact, int numOfPax,LocalDate date, LocalTime time){
        boolean resultD=validateDateTime(date,time);
        if (!resultD){
            System.out.println("The date and time is before current time. Invalid date.");
            return -1;
        }
        Table resultT=getReservationTable(date,time,numOfPax);
        if (resultT==null){
            System.out.println("Table assignment failed.");
            return -1;
        }
        resultT.setReserved(true);
        resultT.addToReservationList(LocalDateTime.of(date,time));
        Reservation newR= new Reservation(name,numOfPax,contact,resultT,date,time);
        Restaurant.reservationList.add(newR);
        return 1;
    }

    public static Table lookForNewTable(LocalDate date,LocalTime time,int numOfPax){
        Table resultT=ReservationManager.getReservationTable(date,time,numOfPax);
        if (resultT==null){
            System.out.println("No table is available. Cannot change table.");
            return null;
        }
        return resultT;
    }

    public static void updateTable(Reservation r,LocalDate newDate,LocalTime newTime){
        if(!TableManager.checkTableReserve(newDate,newTime,r.getTable())){ //need change table
            Table newTable=ReservationManager.lookForNewTable(newDate,newTime,r.getNoOfPax());
            if(newTable==null){
                System.out.println("No new table is available.");
                return;
            }
            //New Table found. Need to remove reservation in the previous table.
            TableManager.removeReservation(r.getLocaldate(),r.getLocaltime(),r.getTable());
            r.setTable(newTable);
            System.out.println("New table assigned.");
            return;
        }
        //no need to change table
        TableManager.removeReservation(r.getLocaldate(),r.getLocaltime(),r.getTable());
        r.getTable().addToReservationList(LocalDateTime.of(newDate,newTime));
        System.out.println("Date and time updated successfully.");

    }

    public static Table getReservationTable(LocalDate date, LocalTime time,int numOfPax){
        ArrayList<Table> availableTables=TableManager.getToReserveTables(date,time);
        if(availableTables.size()==0){
            System.out.println("No table is available to be reserved in your chosen time and date.");
            return null;
        }
        int bestCapacity=999,idx=-1;
        for (int i=0;i<availableTables.size();i++){
            Table curTable=availableTables.get(i);
            if(curTable.getCapacity()<bestCapacity && curTable.getCapacity()>=numOfPax){
                bestCapacity=curTable.getCapacity(); //choose the best-fit table
                idx=i;
            }
        }
        if (idx==-1){
            System.out.println("No table is big enough to be reserved in your chosen time and date.");
            return null;
        }
        return availableTables.get(idx);
    }

    public static void printAllReservations() {
        if (Restaurant.reservationList.size()==0){
            System.out.println("Reservation list is empty.");
            return;
        }
        System.out.println("All reservations: ");
        for (int i=0;i<Restaurant.reservationList.size();i++){
            Reservation curR=Restaurant.reservationList.get(i);
            System.out.println("INDEX "+i);
            curR.printInfo();
        }
    }

    public static void updateName(Reservation r, String name){
        r.setName(name);
        System.out.println("Customer name is updated successfully!");
    }

    public static void updateContact(Reservation r, String contact){
        r.setContactNumber(contact);
        System.out.println("Contact number is updated successfully!");
    }

    public static void removeReservationFromList(Reservation r){
        int i=0;
        if(Restaurant.reservationList.size()==0){
            System.out.println("Reservation list is empty!");
            return;
        }
        for(Reservation reserv:Restaurant.reservationList){
            if (reserv.getName().equals(r.getName()) && reserv.getLocaldate().equals(r.getLocaldate()) && reserv.getLocaltime().equals(r.getLocaltime())) {
                Restaurant.reservationList.remove(i);
                System.out.println("Reservation is removed from list successfully!");
                return;
            }
            i++;
        }
    }

    public static void cancelReservation(int index){
        if(Restaurant.reservationList.size()==0){
            System.out.println("Error! No reservation is in the list.");
            return;
        }
        Reservation rToRemove=Restaurant.reservationList.get(index-1);
        TableManager.removeReservation(rToRemove.getLocaldate(),rToRemove.getLocaltime(),rToRemove.getTable());
        Restaurant.reservationList.remove(index-1);
        System.out.println("Reservation is cancelled.");
    }

    public static boolean validateDateTime(LocalDate date,LocalTime time){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime setDateTime = LocalDateTime.of(date,time);
        if(setDateTime.isBefore(now)){
            return false;
        }
        return true;

    }
}
