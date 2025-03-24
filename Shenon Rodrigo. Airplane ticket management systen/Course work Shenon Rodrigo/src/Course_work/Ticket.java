package Course_work;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Ticket {
    private String row;
    private int price;
    private int seat;
    private Person person;

    public Ticket(String row, int seat, int price, Person person){
        this.row=row;
        this.price=price;
        this.seat=seat+1;
        this.person=person;
    }
    public String getRow(){return row;}
    public int getPrice(){return price;}
    public  int getSeat(){return seat;}
    public Person getPerson(){return person;}
    public void setRow( String row){this.row = row;}
    public void setPrice( int price){this.price = price;}
    public void setSeat( int seat){this.seat = seat;}
    public void setPerson( Person person){this.person = person;}
    public void printTicketInfo(){
        System.out.println("Ticket Information:");
        System.out.println("Row: " + getRow());
        System.out.println("Seat: " + getSeat());
        System.out.println("Price: $" + getPrice());
        System.out.println("Person Information:");
        System.out.println("Name: " + getPerson().getName());
        System.out.println("Surname: "+ getPerson().getSurname());
        System.out.println("Email: " + getPerson().getEmail());
    }

    public String toString() {
        return "Ticket{" +
                "person=" + person.getName() + " " + person.getSurname() +
                ", row=" + (getRow()) +
                ", seatNumber=" + (getSeat() + 1) +
                ", price= $" + getPrice() +
                '}';
    }

    public void save(){
        String name = (this.row + this.seat) + ".txt";
        try {
            // if the file is already existing when the program was run last time the files gets overwritten
            FileWriter writer = new FileWriter(name,false);
            writer.write("--Persons info--\n \n");
            writer.write("Name: " + this.person.getName() +"\n" + "Surname: " + this.person.getSurname() +"\n" + "Email: " + person.getEmail() +"\n\n");
            writer.write("--Ticket info--\n \n");
            writer.write("Row: " + this.row + "\n" + "Seat: " + this.seat + "\n" + "Price: " + this.price);
            writer.close();
        } catch (IOException ex) {
            System.out.println("An error occurred");
        }
    }

    public void delete_file(){
        String name = (this.row + this.seat) + ".txt";
        File file = new File(name);
        if(!file.delete()){
            System.out.println("The file relevant to the ticket couldn't be deleted ");
        }
    }



}
