import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class UpdateAgent extends HttpServlet 
{
    public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException,IOException
    {        
			Statement state4 = null;
			ResultSet result = null;
			String query="";        
			Connection con=null; 
            
                String PersonId = request.getParameter("PersonId");
				String LicenseNum = request.getParameter("LicenseNum");
 
		try
		{			
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver()); 
            con = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:orcl", "CSIPROJECT", "Mohammed1");
	       	System.out.println("Congratulations! You are connected successfully.");      		
     	}
        catch(SQLException e)
		{	
			System.out.println("Error: "+e);		
		}
		catch(Exception e) 
		{
			System.err.println("Exception while loading  driver");		
		}
	    try 
		{
        	state4 = con.createStatement();
		} 
		catch (SQLException e) 	
		{
			System.err.println("SQLException while creating statement");			
		}
		
		response.setContentType("text/html");
		PrintWriter out = null ;
		try
		{
			out =  response.getWriter();
		}
		catch (IOException e) 
		{
  			e.printStackTrace();
		}
		
		query = "update agents set PersonId = '"+PersonId+"' where LicenseNum  = '"+LicenseNum+"'";												
		out.println("<html><head><title>  Record has been updated</title>");	 
		out.println("</head><body>");
		out.print( "<br /><b><center><font color=\"RED\"><H2>The following record has been updated in the database:</H2></font>");		
        out.print( PersonId );
		out.print( " " );
		out.print( LicenseNum );
		
        out.println( "</center><br />" );
       	try 
		{ 
			result=state4.executeQuery(query);
				
	  	}
		catch (SQLException e) 
		{
			System.err.println("SQLException while executing SQL Statement."); 
		}
		
		try 
		{ 
   			result.close(); 
			state4.close(); 	
			con.close();
    		System.out.println("Connection is closed successfully.");
 	   	}
		catch (SQLException e) 
		{
			e.printStackTrace();	
		}

  		out.println("</body></html>");
    } 
}