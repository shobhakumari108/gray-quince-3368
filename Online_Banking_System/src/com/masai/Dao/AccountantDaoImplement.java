package com.masai.Dao;

//import java.security.DomainCombiner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

//import javax.security.auth.login.AccountException;

import com.masai.exception.AccountntException;
import com.masai.exception.CustomerException;
import com.masai.exception.TransactionException;
import com.masai.module.Customer;
import com.masai.module.Transaction;
import com.masai.utility.DBUtil;

public class AccountantDaoImplement implements AccountantDao {
   boolean flag = false;
   @Override
	public boolean LoginAccountant(String Username, String Password) throws AccountntException {
		// TODO Auto-generated method stub
	   
	   try(Connection conn = DBUtil.provideConnection()){
			PreparedStatement ps = conn.prepareStatement("select * from Accountant where Username=? and password=?");
			ps.setString(1, Username);
			ps.setString(2, Password);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				flag = true;
			}
			
		} catch (SQLException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new AccountntException(e.getMessage());
		}
	   
		return flag;
	}


	@Override
	public boolean InsertCustomer(String Name, String Username, String password, int AccountNo, int Amount)
			throws CustomerException {
		// TODO Auto-generated method stub
		boolean flag = false;
		try(Connection conn = DBUtil.provideConnection()){
			PreparedStatement ps = conn.prepareStatement("insert into Customer (name,username,password,AccountNo,Amount) values(?,?,?,?,?");
			ps.setString(1, Name);
			ps.setString(2, Username);
			ps.setString(3, password);
			ps.setInt(4, AccountNo);
			ps.setInt(5, Amount);
			
			int A = ps.executeUpdate();
			
			if(A > 0) {
		       flag = true;
			}else {
				throw new CustomerException("username or passwod is wrong...");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new CustomerException(e.getMessage());
		}
		return flag;
	}

	@Override
	public boolean UpdateCusomer(String Name, String Username, String password, int AccountNo, int Amount, int Trans_ID)
			throws CustomerException {
		// TODO Auto-generated method stub
		boolean flag = false;
		
		
		
		try(Connection conn = DBUtil.provideConnection()) {
			PreparedStatement ps = conn.prepareStatement(
					
					"update Customer setName = ?, Username = ?, password = ?, "
					+ "AccountNo = ?, Amount = ? where AccountNo=?");
			ps.setString(1, Name);
			ps.setString(2, Username);
			ps.setString(3, password);
			ps.setInt(4, AccountNo);
			ps.setInt(5, Amount);
			ps.setInt(6, Trans_ID);
			
			int A = ps.executeUpdate();
			
			if(A >0) {
				flag=true;
			}			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new CustomerException(e.getMessage());
		}
		return flag;
	}

	@Override
	public int delectCustomer(int AccountNo) throws CustomerException {
		// TODO Auto-generated method stub
		int res =0;
		try(Connection conn = DBUtil.provideConnection()) {
			PreparedStatement ps = conn.prepareStatement("delete from Customer where AccountNo = ?");//***************************
			
			ps.setInt(1, AccountNo);
			int A = ps.executeUpdate();
			
			if(A > 0) {
				res++;
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new CustomerException(e.getMessage());
		}
		return res;
		
	}

	@Override
	public int FindParticularAccountDetail(int AccountNo) throws CustomerException {
		// TODO Auto-generated method stub
		int res =0;
		try(Connection conn = DBUtil.provideConnection()) {
			PreparedStatement ps = conn.prepareStatement("select * from Customer where AccountNo= ?");
			
			ps.setInt(1, AccountNo);
			
			ResultSet rs = ps.executeQuery();
			
			if(res > 0) {
				res++;
				System.out.println(" ContomerId is" + rs.getInt("CustomerID") 
				+ "name is : " + rs.getString("Name") 
				+ " username is :" + rs.getString("Username")
				+ "password is " +rs.getString("password")
				+ " AccountNo is :" + rs.getInt("AccountNO")
				+ " Amount is :" + rs.getInt("Amount")
				);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new CustomerException(e.getMessage());
		}
		return res;

		
	}

	
	@Override
	public List<Customer> FindAllCustomerDetail() throws CustomerException {
		// TODO Auto-generated method stub
		List<Customer> list = new ArrayList<>();
		
		try(Connection conn = DBUtil.provideConnection()) {
			PreparedStatement ps = conn.prepareStatement("select * from Customer");
			
			
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				int CustomerId = rs.getInt("CustomerID");
				String Name = rs.getString("Name");
				String Username = rs.getString("Username");
				String password = rs.getString("password");
				int AccountNo = rs.getInt("AccountNo");
				int Amount = rs.getInt("Amount");//*******************koun sa cid**************
				
				Customer customer = new Customer(CustomerId , Name, Username, password, AccountNo, Amount);
				list.add(customer);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		return list;
	}

	@Override
	public List<Transaction> FindTransactionList() throws TransactionException {
		// TODO Auto-generated method stub
		List<Transaction> list = new ArrayList<>();
		try(Connection conn = DBUtil.provideConnection()) {
			PreparedStatement ps = conn.prepareStatement("select * from Transaction");
			
			
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				int Trans_ID = rs.getInt("Trans_ID");
				String Sender = rs.getString("Sender");
				String Receiver = rs.getString("Receiver");
				int Amount = rs.getInt("Amount");
				Timestamp transactionTime = rs.getTimestamp("transactionTime");
				
				Transaction transaction = new Transaction( Trans_ID, Sender, Receiver, Amount,transactionTime);
				list.add(transaction);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		return list; 
	}

	
}
