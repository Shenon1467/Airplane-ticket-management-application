package Course_work;

import java.util.InputMismatchException;
import java.util.Scanner;

public class w2052065_PlaneManagement {
        public static int[][] seats = new int[4][];
        static {
            seats[0] = new int[14];
            seats[1] = new int[12];
            seats[2] = new int[12];
            seats[3] = new int[14];
        }
        public static Ticket[] tickets=new Ticket[52];
        static Scanner sc=new Scanner(System.in);
        public static void main(String[] args) {
            System.out.println("\nWelcome to the Plane Management application\n");
            boolean check;
            int choice = 0;
            boolean valid = false;
            while (!valid) {
                check = false;

                while (!check) {
                    System.out.println("*".repeat(46));
                    System.out.println("*               MENU OPTIONS                 *");
                    System.out.println("*".repeat(46));
                    System.out.println("1. Buy a seat");
                    System.out.println("2. Cancel a seat");
                    System.out.println("3. Find first available seat");
                    System.out.println("4. Show seating plan");
                    System.out.println("5. Print ticket information");
                    System.out.println("6. Search ticket");
                    System.out.println("0. Exit.");

                    try {
                        System.out.println("Please select an option (0 - 6): ");
                        choice = sc.nextInt();
                        System.out.println();
                        if (choice < 0 || choice > 6) {
                            System.out.println("Please enter a valid option");
                        } else {
                            check = true;
                        }
                    } catch (InputMismatchException ex) {
                        System.out.println("Please enter a valid integer");
                        sc.nextLine();
                    }
                }
                switch (choice) {
                    case 0:
                        valid = true;
                        System.out.println("Exiting the application...");
                        break;
                    case 1:
                        buySeat();
                        break;
                    case 2:
                        cancelSeat();
                        break;
                    case 3:
                        findFirstAvailable();
                        break;
                    case 4:
                        showSeatingPlan();
                        break;
                    case 5:
                        printTicketInfo();
                        break;
                    case 6:
                        searchTicket();
                        break;
                }
            }
        }

        //Implementing the seat row letter input with validation.
        static String SeatRowLetter(){
            String letter;
            do{
                System.out.println("Please enter seat letter (A,B,C,D): ");
                letter= sc.next().toUpperCase();
            }while (!letter.equals("A") && !letter.equals("D") && !letter.equals("B") && !letter.equals("C"));
            return letter;
        }

        //Implementing the seat number input with validation.
        static int SeatNumber(String letter){
            Scanner input=new Scanner(System.in);
            int seat = 0;
            boolean check = false;
            while (!check) {
                try {
                    System.out.print("Enter the seat number: ");
                    seat = input.nextInt();
                    // Check seat validity based on row
                    if (((letter.equals("B")) || (letter.equals("C"))) && ((seat < 1) || (seat > 12))) {
                        System.out.println("Please enter a valid seat number!!");
                    } else if (((letter.equals("A")) || (letter.equals("D"))) && ((seat < 1 || seat > 14))) {
                        System.out.println("Please enter a valid seat number!!");
                    } else {
                        check = true;
                    }
                } catch (InputMismatchException ex) {
                    System.out.println("Please enter an integer!!");
                    input.nextLine();
                }
            }
            return seat-1;
        }

        // Implementation of buying a seat method
        private static void buySeat() {
            int price;
            do {
                String letter=SeatRowLetter();
                int num=SeatNumber(letter);
                int rep=switch (letter){
                    case "A"->0;
                    case "B"->1;
                    case "C"->2;
                    case "D"->3;
                    default -> -1;
                };
                if (seats[rep][num]==1) {
                    System.out.println("Seat is already booked. Try again");
                }else {
                    if (num <= 5) {
                        price = 200;
                    } else if (num <= 9) {
                        price = 150;
                    } else {
                        price = 180;
                    }
                    System.out.println("Enter your name: ");
                    String name = sc.next();
                    System.out.println("Enter your surname: ");
                    String surname = sc.next();
                    boolean check = false;
                    String email = null;
                    while (!check) {
                        System.out.print("Enter your email: ");
                        email = sc.next();
                        // Checks if input email contains the symbol "@" and "."
                        if (email.contains("@") && email.contains(".")) {
                            // Checks if the symbols appear together.
                            if (email.indexOf("@") != (email.indexOf(".") - 1) && (email.indexOf("@") - 1) != email.indexOf(".")) {
                                check = true;
                            } else {
                                System.out.println("Please enter an valid email!!\n");
                            }
                        } else {
                            System.out.println("Please enter an valid email!!\n");
                        }
                    }
                    Person person = new Person(name, surname, email);
                    Ticket ticket = new Ticket(letter, num, price, person);
                    for (int i = 0; i < tickets.length; i++) {
                        if (tickets[i] == null) {
                            tickets[i] = ticket;
                            break;
                        }
                    }
                    ticket.save();
                    seats[rep][num] =1;
                    System.out.println("You have successfully booked a seat");
                    break;
                }
            }while (true);

        }

        // Implementation of canceling a seat method
        private static void cancelSeat() {
            do{
                String letter=SeatRowLetter();
                int num=SeatNumber(letter);
                int rep=switch (letter){
                    case "A"->0;
                    case "B"->1;
                    case "C"->2;
                    case "D"->3;
                    default -> -1;
                };
                if (seats[rep][num]==0) {
                    System.out.println("Seat is already available. Please try another seat.");
                } else {
                    for(int i=0; i< tickets.length;) {
                        Ticket ticket = tickets[i];
                        ticket.delete_file();
                        tickets[i] = null;
                        seats[rep][num] = 0;
                        System.out.println("You have successfully unbooked and cancelled a seat");
                        return;
                    }

                    System.out.println("No ticket has been booked.");
                    return;

                }            }while(true);
        }

        // Implementation of finding the first available seat method
        private static void findFirstAvailable() {
            boolean found = false;
            for(int row = 0; row < 4; row++){
                if(found){
                    break;
                }
                for(int seat = 0; seat < seats[row].length; seat++){
                    if(seats[row][seat] == 0){
                        char rowLetter = (char) (row + 65);
                        System.out.println("Seat available at : " + rowLetter + (seat+1) + "\n");
                        found = true;
                        break;
                    }
                }
            }
            if(!found){
                System.out.println("All the seats are bought!!\n");
            }

        }

        // Implementation of show seating plan method
        private static void showSeatingPlan() {
            System.out.println("'X'=booked seats.");
            System.out.println("'O'=seats available.\n");
            for(int row = 0; row < 4; row++){
                for(int element : seats[row]){
                    if(element == 0){
                        System.out.print("O ");
                    }
                    else{
                        System.out.print("X ");
                    }
                }
                System.out.println();
            }
            System.out.println();
        }

        //Implementing the ticket printing information method
        private static void printTicketInfo() {
            double total = 0;
            System.out.println("--Details of all the tickets sold--");
            for(Ticket element : tickets){
                if(element != null) {
                    element.printTicketInfo();
                    total += element.getPrice();
                    System.out.println();
                }
            }
            if(total == 0){
                System.out.println("\nNo tickets were sold!\n");
            }
            else{
                System.out.println("The total amount of  tickets is : " + total + "\n");
            }
        }

        //Implementing the search ticket method.
        private static void searchTicket() {
            String row_letter = SeatRowLetter();
            int row= row_letter.charAt(0)-65;
            int seatIndex = SeatNumber(row_letter);
            int seatNumber = seatIndex + 1;
            if(seats[row][seatIndex] == 0){
                System.out.println("Seat already available");
            }
            else{
                for (Ticket searchTicket : tickets) {
                    if (searchTicket != null && searchTicket.getRow().equals(row_letter) && searchTicket.getSeat() == seatNumber) {
                        System.out.println("Ticket already booked");
                        searchTicket.printTicketInfo();
                    }
                }
            }

        }
}

