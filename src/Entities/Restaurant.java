package Entities;

import java.util.*;
import Enums.Gender;
import Enums.JobTitle;
import Enums.FoodType;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import Controller.ReservationManager;

public class Restaurant {
    Scanner scan = new Scanner(System.in);
    private static ArrayList<Staff> stafflist;
    private static ArrayList<Table> tablelist;
    private static ArrayList<MenuItem> menulist;
    private static ArrayList<Order> orderList;
    private static ArrayList<Reservation> reservationList;


    public static void initialRestaurant(){
        initialTable();
        initialStaff();
        initialMenu();
        initialOrders();
        initialReservations();
    }

    public static ArrayList<Order> getOrderList() {
        return orderList;
    }

    public static ArrayList<MenuItem> getMenulist() {
        return menulist;
    }

    public static ArrayList<Reservation> getReservationList() {
        return reservationList;
    }

    public static ArrayList<Staff> getStafflist() {
        return stafflist;
    }

    public static ArrayList<Table> getTablelist() {
        return tablelist;
    }

    public static void addMenuItem(MenuItem menuItem){
        menulist.add(menuItem);
    }

    public static void addMenuItem(int index, MenuItem menuItem){
        menulist.add(index,menuItem);
    }

    public static void removeMenuItem(int index){menulist.remove(index);}

    public static void addStaffList(Staff staff){
        stafflist.add(staff);
    }

    public static void removeStaff(int index){stafflist.remove(index);}

    public static void addTableList(Table table){
        tablelist.add(table);
    }

    public static void addReservationList(Reservation reservation){
        int[] numReservationsForTables = new int[10];
        int idx = 0;
        for (int i = 0; i < Restaurant.reservationList.size(); i++) {
            if (!Restaurant.reservationList.get(i).getHasExpired()) {
                numReservationsForTables[(Restaurant.reservationList.get(i).getTable().getTableID())-1]++;
            }
        }
        for (int i = 1; i <= reservation.getTable().getTableID(); i++) {
            idx = idx + numReservationsForTables[i-1];
        }
        reservationList.add(idx, reservation);
    }

    public static void removeReservation(int index){reservationList.remove(index);}

    public static void addOrderList(Order order){
        orderList.add(order);
    }

    public static void removeOrder(int index){orderList.remove(index);}
    //initial setup of tables;
    public static void initialTable(){
        int tableID = 1;
        tablelist = new ArrayList<Table>();
        //Table[] tables = new Table[25];
        for(int i = 0; i < 2; i++){
            tablelist.add(new Table(tableID, 2));
            tableID++;
        }
        for(int i = 0; i < 2; i++){
            tablelist.add(new Table(tableID, 4));
            tableID++;
        }
        for(int i = 0; i < 2; i++){
            tablelist.add(new Table(tableID, 6));
            tableID++;
        }
        for(int i=0; i < 2; i++){
            tablelist.add(new Table(tableID, 8));
            tableID++;
        }
        for(int i=0;i<2;i++){
            tablelist.add(new Table(tableID, 10));
            tableID++;
        }
    }
    //initial set up of the staff
    public static void initialStaff(){
        int staffID;
        stafflist = new ArrayList<Staff>();
        Staff s1 = new Staff(IDGenerator.generateUniqueId(),"Ernest", JobTitle.WAITER,Gender.MALE);
        Staff s2 = new Staff(IDGenerator.generateUniqueId(), "Yaxin Wan", JobTitle.SUPERVISOR, Gender.FEMALE);
        Staff s3 = new Staff(IDGenerator.generateUniqueId(), "John", JobTitle.MANAGER, Gender.MALE);
        stafflist.add(s1);
        stafflist.add(s2);
        stafflist.add(s3);
    }

    //initial setup the menu & set packages
    public static void initialMenu(){
        menulist = new ArrayList<MenuItem>();
        MenuItem f1 = new ALaCarte("Cheeseburger","Burger with 2 beef patties, cheese, with our original sauce",6.5,FoodType.MAIN);
        MenuItem f2 = new ALaCarte("Fish&Chips", "Fried fish served with a side of fries and coleslaw",7.0,FoodType.MAIN);
        MenuItem f3 = new ALaCarte("Grilled Ribeye Steak","Premium steak imported from Japan, served with a side of onion rings and wedges", 15.4, FoodType.MAIN);
        MenuItem f4 = new ALaCarte("Coke","500ml of Coca-cola",1.5, FoodType.DRINKS);
        MenuItem f5 = new ALaCarte("Sprite","500ml of Sprite", 1.5, FoodType.DRINKS);
        MenuItem f6 = new ALaCarte("Ice Lemon Tea","500ml of our own in-house Iced Lemon Tea", 2.0, FoodType.DRINKS);
        MenuItem f7 = new ALaCarte("Chocolate Ice Cream","8oz of chocolate ice cream topped with rainbow sprinkles", 3.5, FoodType.DESSERT);
        MenuItem f8 = new ALaCarte("Strawberry Sundae","A cup of vanilla ice cream topped with fresh strawberries",3.2,FoodType.DESSERT);
        MenuItem f9 = new ALaCarte("Burnt Cheesecake", "Our own in-house burnt cheesecake", 4.5, FoodType.DESSERT);

        menulist.add((MenuItem) f1);
        menulist.add((MenuItem) f2);
        menulist.add((MenuItem) f3);
        menulist.add((MenuItem) f4);
        menulist.add((MenuItem) f5);
        menulist.add((MenuItem) f6);
        menulist.add((MenuItem) f7);
        menulist.add((MenuItem) f8);
        menulist.add((MenuItem) f9);

        SetPackage s1 = new SetPackage("Set 1", "Cheeseburger + Coke + Chocolate Ice Cream", 10.99);
        SetPackage s2 = new SetPackage("Set 2", "Grilled Ribeye Steak + Ice Lemon Tea + Burnt Cheesecake", 19.99);
        s1.addItem(f1);
        s1.addItem(f4);
        s1.addItem(f7);

        s2.addItem(f3);
        s2.addItem(f6);
        s2.addItem(f9);

        menulist.add((MenuItem) s1);
        menulist.add((MenuItem) s2);
    }

    //int orderID, MenuItem item, int qty, Staff staff, Table table, boolean membership
    public static void initialOrders(){
        orderList = new ArrayList<Order>();

        Order o1 = new Order(IDGenerator.generateUniqueId(),stafflist.get(0), tablelist.get(1),true,1, LocalDateTime.of(LocalDate.of(2021,10,5),LocalTime.of(9,00)));
        o1.addFood(menulist.get(1), 1);
        o1.addFood(menulist.get(2), 2);
        o1.addFood(menulist.get(3), 3);
        o1.setCompleted();
        o1.getTable().setOccupied(false);
        orderList.add(o1);
        Order o2 = new Order(IDGenerator.generateUniqueId(),stafflist.get(1), tablelist.get(2),true,2, LocalDateTime.of(LocalDate.now(),LocalTime.of(10,30)));
        o2.addFood(menulist.get(4), 4);
        o2.addFood(menulist.get(5), 5);
        o2.addFood(menulist.get(1), 1);
        orderList.add(o2);
        Order o3 = new Order(IDGenerator.generateUniqueId(),stafflist.get(2), tablelist.get(4),true,3, LocalDateTime.of(LocalDate.of(2021,10,6),LocalTime.of(13,21)));
        o3.addFood(menulist.get(2), 4);
        o3.addFood(menulist.get(3), 3);
        o3.addFood(menulist.get(4), 2);
        orderList.add(o3);
        Order o4 = new Order(IDGenerator.generateUniqueId(),stafflist.get(1), tablelist.get(6),true,5, LocalDateTime.of(LocalDate.of(2021,10,6),LocalTime.of(15,20)));
        o4.addFood(menulist.get(1), 2);
        o4.addFood(menulist.get(3), 3);
        o4.addFood(menulist.get(9), 1);
        orderList.add(o4);
        Order o5 = new Order(IDGenerator.generateUniqueId(),stafflist.get(0), tablelist.get(9),true,10, LocalDateTime.now());
        o5.addFood(menulist.get(2), 2);
        o5.addFood(menulist.get(5), 4);
        o5.addFood(menulist.get(10), 1);
        orderList.add(o5);
    }

    public static void initialReservations() {
        reservationList = new ArrayList<Reservation>();
        ReservationManager.addNewReservation("John", "91882888", 3, LocalDate.now(), LocalTime.now().plusSeconds(5), false);
        ReservationManager.addNewReservation("Sally", "90203202", 4, LocalDate.now(), LocalTime.now().plusSeconds(7), false);
        ReservationManager.addNewReservation("Tom", "95939392", 5, LocalDate.now(), LocalTime.now().plusSeconds(9), false);
        ReservationManager.addNewReservation("swag", "94314311", 5, LocalDate.now(), LocalTime.now().plusSeconds(15), false);
    }

}




