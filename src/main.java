import java.sql.Connection;
import java.sql.DriverManager;

public class main {

    public static void main(String[] args) {
        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/myCompany", "root", "12345");
        System.out.println("Connected");
        }catch(Exception e){
            System.out.println(e);
        }

    }
}
