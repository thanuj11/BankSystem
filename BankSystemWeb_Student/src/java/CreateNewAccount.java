/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import java.sql.*;
import java.text.DecimalFormat;

/**
 *
 * @author thanuj
 */
@Named(value = "createNewAccount")
@RequestScoped
public class CreateNewAccount {

    /**
     * Creates a new instance of CreateNewAccount
     */
    private String ssn;
    private double balance;
    public String createAccount()
    {
        //load the driver
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch(Exception e)
        {
            return("Internal error,please try again later!");
        }
        final String DB_URL = "XXXX";
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        String accountID = "";
        try
        {
            conn = DriverManager.getConnection(DB_URL,"thanuj","thanuj");
            stat = conn.createStatement();
            rs = stat.executeQuery("select * from nextAccountNumber");
            if(rs.next())
            {
                accountID = ""+rs.getInt(1);
            }
            else
            {
                return("Internal error,please try again later!");
            }
             int j = stat.executeUpdate("Update nextAccountNumber set nextID ='"+(rs.getInt(1)+1)+"'");
            //insert a new record into bank account table
            DecimalFormat df = new DecimalFormat("##.00");
            String s = DateAndTime.DateTime()+":Account opened with an "+"inital balance $"+df.format(balance)+"\n";
            int r = stat.executeUpdate("insert into BankAccount values ('"+accountID+"','"+ssn+"','"+balance+"','"+s+"')");
            
            //update new one 
           
            
            return("Congratualtions you have created a new bank account. The new account number is "+accountID+".");
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return("Internal Error!,Please try again later!");
        }
        finally
        {
            try
            {
                rs.close();
                stat.close();
                conn.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    public String getSsn() {
        return ssn;
    }

    public double getBalance() {
        return balance;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
    
    public CreateNewAccount() {
    }
    
}
