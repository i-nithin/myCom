import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;

public class Employee {
    private JPanel Main;
    private JTextField txtName;
    private JTextField txtSalary;
    private JTextField txtMobile;
    private JButton saveButton;
    private JTable table_1;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton searchButton;
    private JTextField txtid;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Employee");
        frame.setContentPane(new Employee().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


    Connection con ;
    PreparedStatement pst;

    public void connect(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/MyCom", "root", "root");
            System.out.println("Sucess");
        }catch(ClassNotFoundException ex){
            ex.printStackTrace();
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }


    void table_load() {
        try {
            pst = con.prepareStatement("select * from employee");
            ResultSet rs = pst.executeQuery();
            table_1.setModel(DbUtils.resultSetToTableModel(rs));
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }





    public Employee() {
        connect();
        table_load();
    saveButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            String empname, salary, mobile;

            empname = txtName.getText();
            salary = txtSalary.getText();
            mobile = txtMobile.getText();

            try{
                pst = con.prepareStatement("insert into employee(empname, salary, mobile)values(?,?,?)");
                pst.setString(1, empname);
                pst.setString(2, salary);
                pst.setString(3, mobile);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Record Added");
                table_load();
                txtName.setText("");
                txtSalary.setText("");
                txtMobile.setText("");
                txtName.requestFocus();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

        }
    });


        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String id = txtid.getText();
                    pst = con.prepareStatement("select empname,salary,mobile from employee where id = ?");
                    pst.setString(1, id);
                    ResultSet rs = pst.executeQuery();

                    if(rs.next()==true){
                        String empname = rs.getString(1);
                        String salary = rs.getString(2);
                        String mobile = rs.getString(3);
                        txtName.setText(empname);
                        txtSalary.setText(salary);
                        txtMobile.setText(mobile);
                    } else{
                        txtName.setText("");
                        txtSalary.setText("");
                        txtMobile.setText("");
                        JOptionPane.showMessageDialog(null, "Invalid Employee no");
                    }

                }catch(SQLException ex){

                }
            }
        });


        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String empname, salary, mobile, empid;

                empname = txtName.getText();
                salary = txtSalary.getText();
                mobile = txtMobile.getText();
                empid = txtid.getText();

                try {
                    pst = con.prepareStatement("update employee set empname = ?, salary = ?, mobile = ? where id = ?");
                    pst.setString(1, empname);
                    pst.setString(2, salary);
                    pst.setString(3, mobile);
                    pst.setString(4, empid);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record updated!");
                    table_load();
                    txtName.setText("");
                    txtSalary.setText("");
                    txtMobile.setText("");
                    txtName.requestFocus();
                    txtid.setText("");

                } catch(SQLException e1) {
                    e1.printStackTrace();
                }

            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String empid;

                empid = txtid.getText();

                try{
                    pst = con.prepareStatement("delete from employee where id = ?");
                    pst.setString(1, empid);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Deleted!");
                    table_load();
                    txtName.setText("");
                    txtSalary.setText("");
                    txtMobile.setText("");
                    txtName.requestFocus();
                    txtid.setText("");
                } catch (SQLException e1){
                    e1.printStackTrace();
                }


            }
        });
    }
}
