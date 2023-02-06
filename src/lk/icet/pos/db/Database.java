package lk.icet.pos.db;

import lk.icet.pos.entity.Customer;
import lk.icet.pos.entity.Item;
import lk.icet.pos.entity.Order;
import lk.icet.pos.entity.User;
import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.Arrays;


public class Database {
    public static ArrayList<User> Users =new ArrayList<User>();
    public static ArrayList<Customer> customers=new ArrayList<Customer>();
    public static ArrayList<Item> items=new ArrayList<Item>();

    public static  ArrayList<Order> orders=new ArrayList<>();


    static {
        Users.add(new User("linda",encryptpassword("1234")));
        Users.add(new User("anna",encryptpassword("1234")));
        Users.add(new User("tom",encryptpassword("1234")));

        customers.add(new Customer("c01","Kamal","Panadura", 80000.0));
        customers.add(new Customer("c02","Sumudu","Galle", 950000.0));
        customers.add(new Customer("c03","Nipun","Colombo", 78000.0));

        Item item1=new Item("C01","Desc-01",50,350);
        Item item2=new Item("C02","Desc-02",80,2850);
        Item item3=new Item("C03","Desc-03",90,45074);

        items.addAll(
                Arrays.asList(item1,item2,item3)
        );

    }

    private static String encryptpassword(String rowPassword){
        return BCrypt.hashpw(rowPassword,BCrypt.gensalt());
    }
}
