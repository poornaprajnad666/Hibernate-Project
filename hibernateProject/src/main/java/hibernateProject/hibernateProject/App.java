package hibernateProject.hibernateProject;

import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import org.hibernate.service.ServiceRegistry;

public class App {

	private static SessionFactory sessionFactory;

	public App() {
		sessionFactory = new Configuration().configure().addAnnotatedClass(mypojoClass.class).buildSessionFactory();
	}

	public static mypojoClass singlecustomer(mypojoClass customer, int accNo) {
		System.out.println("SINGLE CUSTOMER DETAILS:");
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();
		customer = (mypojoClass) session.get(mypojoClass.class, accNo);
		transaction.commit();
		System.out.println(customer);
		return customer;

	}

	public static void updateAccountBalance(int customerId, int amount) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();

			mypojoClass customer = session.get(mypojoClass.class, customerId);
			if (customer != null) {

				int newBalance = customer.getBalance() + amount;
				customer.setBalance(newBalance);
				session.update(customer);
				transaction.commit();
				System.out.println("Updated account balance for customer " + customerId + " to " + newBalance);
			} else {
				System.out.println("Customer with ID " + customerId + " not found.");
			}
		} catch (Exception ex) {
			if (transaction != null) {
				transaction.rollback();
			}
			ex.printStackTrace();
		} finally {
			session.close();
		}
	}

	public static void addCustomer(mypojoClass customer) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.save(customer);
			transaction.commit();
		} catch (Exception ex) {
			if (transaction != null) {
				transaction.rollback();
			}
			ex.printStackTrace();
		} finally {
			session.close();
		}
	}

	public static void deleteCustomersWithLowBalance() {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();

			int deletedCount = session.createQuery("DELETE FROM my_hibernate_tbl WHERE balance < 100").executeUpdate();
			transaction.commit();
			System.out.println("Deleted " + deletedCount + " customers with low balance.");
		} catch (Exception ex) {
			if (transaction != null) {
				transaction.rollback();
			}
			ex.printStackTrace();
		} finally {
			session.close();
		}
	}

	public static List<mypojoClass> getAllCustomers() {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		List<mypojoClass> customers = null;
		try {
			transaction = session.beginTransaction();
			customers = (List<mypojoClass>) session.createQuery("FROM my_hibernate_tbl").list();
			transaction.commit();
		} catch (Exception ex) {
			if (transaction != null) {
				transaction.rollback();
			}
			ex.printStackTrace();
		} finally {
			session.close();
		}
		return customers;
	}

	public static void main(String[] args) {

		App app = new App();

		mypojoClass customer = new mypojoClass();

		
		Scanner scan = new Scanner(System.in);
		
		int b = 1;
		while( b==(1)) {
		System.out.println("1.Insert single customer\n2.Diplay Single Customer\n3.Display all customer\n4.Update account Balance\n5.Delete customer with balance less than 100\n");
		int a = scan.nextInt();
			
		if (a == 1) {
			System.out.println("Enter the customer account number: ");
			int num = scan.nextInt();
			scan.nextLine(); 

			System.out.println("Enter the customer name: ");
			String name = scan.nextLine();

			System.out.println("Enter the balance to be added or ( - ) to deduct the balance : ");
			int bal = scan.nextInt();
			scan.nextLine(); 
			
			System.out.println("Enter the branch: ");
			String branch = scan.nextLine();
			

			customer.setAccNo(num);
			customer.setName(name);
			customer.setBranch(branch);
			customer.setBalance(bal);
			
			
//			customer.setAccNo(3);
//	        customer.setName("prajna");
//	        customer.setBranch("banglore");
//	        customer.setBalance(250);

			addCustomer(customer);
		}else if (a==2) {
			System.out.println("Enter the customer account number: ");

			int num1 = scan.nextInt();

			mypojoClass cust = singlecustomer(customer, num1);
			if(cust!= null) {
			System.out.println("Name: " + cust.getName());
			System.out.println("Account Number: " + cust.getAccNo());
			System.out.println("Branch: " + cust.getBranch());
			System.out.println("Balance: " + cust.getBalance());
			System.out.println("--------------------");
			}else {
				System.out.println("No customer found!!!");
			}
			
		} else if (a == 3) {
			List<mypojoClass> customers = getAllCustomers();
			for (mypojoClass cust : customers) {

				System.out.println("Name: " + cust.getName());
				System.out.println("Account Number: " + cust.getAccNo());
				System.out.println("Branch: " + cust.getBranch());
				System.out.println("Balance: " + cust.getBalance());
				System.out.println("--------------------");
			}
		} else if (a == 4) {
			System.out.println("Enter the customer account number and add balance: ");
			int num4 = scan.nextInt();
			int bal4 = scan.nextInt();
			
			updateAccountBalance(num4, bal4);
		}else if (a==5) {
			deleteCustomersWithLowBalance();
		}
		System.out.println("Do you want to continue? (1 or 0)");
        b = scan.nextInt();
	}



		

//        customer.setAccNo(4);
//        customer.setName("prajna");
//        customer.setBranch("banglore");
//        customer.setBalance(250);
//        
//        Configuration con = new Configuration().configure().addAnnotatedClass(mypojoClass.class);

//        ServiceRegistry reg = new ServiceRegistryBuilder().applySettings(con.getProperties()).buildServiceRegistry();

//        SessionFactory sf = con.buildSessionFactory();
//        
//        Session session = sf.openSession();
//        
//        Transaction tx = session.beginTransaction();
//        
//        session.save(customer);
//        
//        tx.commit();
	}
}
