import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class Registration extends HttpServlet {
  // Use a prepared statement to store a student into the database
  private PreparedStatement pstmt;

  /** Initialize variables */
  public void init() throws ServletException {
    initializeJdbc();
  }

  /** Process the HTTP Get request */
  public void doGet(HttpServletRequest request, HttpServletResponse
      response) throws ServletException, IOException {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();

    // Obtain data from the form
    String lastName = request.getParameter("lastName");
    String firstName = request.getParameter("firstName");
    String mi = request.getParameter("mi");
    String telephone = request.getParameter("telephone");
    String email = request.getParameter("email");
    String street = request.getParameter("street");
    String city = request.getParameter("city");
    String state = request.getParameter("state");
    String zip = request.getParameter("zip");

    if (lastName.length() == 0 || firstName.length() == 0) {
      out.println("Last Name and First Name are required");
      return; // End the method
    }

    // Ask for confirmation
	out.println("<center>");
    out.println("You entered the following data");
    out.println("<p>Last name: " + lastName);
    out.println("<br>First name: " + firstName);
    out.println("<br>MI: " + mi);
    out.println("<br>Telephone: " + telephone);
    out.println("<br>Email: " + email);
    out.println("<br>Address: " + street);
    out.println("<br>City: " + city);
    out.println("<br>State: " + state);
    out.println("<br>Zip: " + zip);
  
    // Set the action for processing the answers
    out.println("<p><form method=\"post\" action=" +
      "/MCS3603/Registration>");
    // Set hidden values
    out.println("<p><input type=\"hidden\" " +
      "value=" + lastName + " name=\"lastName\">");
    out.println("<p><input type=\"hidden\" " +
      "value=" + firstName + " name=\"firstName\">");
    out.println("<p><input type=\"hidden\" " +
      "value=" + mi + " name=\"mi\">");
    out.println("<p><input type=\"hidden\" " +
      "value=" + telephone + " name=\"telephone\">");
    out.println("<p><input type=\"hidden\" " +
      "value=" + email + " name=\"email\">");
    out.println("<p><input type=\"hidden\" " +
      "value=" + street + " name=\"street\">");
    out.println("<p><input type=\"hidden\" " +
      "value=" + city + " name=\"city\">");
    out.println("<p><input type=\"hidden\" " +
      "value=" + state + " name=\"state\">");
    out.println("<p><input type=\"hidden\" " +
      "value=" + zip + " name=\"zip\">");
    out.println("<p><input type=\"submit\" value=\"Confirm\" >");
    out.println("</form>");
    out.println("</center>");
    out.close(); // Close stream
  }

  /** Process the HTTP Post request */
  public void doPost(HttpServletRequest request, HttpServletResponse
      response) throws ServletException, IOException {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();

    try {
      String lastName = request.getParameter("lastName");
      String firstName = request.getParameter("firstName");
      String mi = request.getParameter("mi");
      String telephone = request.getParameter("telephone");
      String email = request.getParameter("email");
      String street = request.getParameter("street");
      String city = request.getParameter("city");
      String state = request.getParameter("state");
      String zip = request.getParameter("zip");

      storeStudent(lastName, firstName, mi, telephone, email,
        street, city, state, zip);
      out.println("<center>");
      out.println(firstName + " " + lastName +
        " is now registered in the database");
		out.println("</center>");
    }
    catch(Exception ex) {
      out.println("Error: " + ex.getMessage());
      return; // End the method
    }
  }

  /** Initialize database connection */
  private void initializeJdbc() {
    try {
      // Declare driver and connection string
      //String driver = "sun.jdbc.odbc.JdbcOdbcDriver";
      //String connectionString = "jdbc:odbc:exampleMDBDataSource";
      /* For Oracle
      String driver = "oracle.jdbc.driver.OracleDriver";
      String connectionString = "jdbc:oracle:" +
        "thin:scott/tiger@liang.armstrong.edu:1521:orcl";
      */
	  String driver = "oracle.jdbc.driver.OracleDriver";
      // Load the Oracle JDBC Thin driver
      Class.forName(driver);
	  String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
	  String user = "CSIPROJECT";
      String password = "mohammed";
      // Connect to the sample database
      Connection conn = DriverManager.getConnection
        (url,user, password);

      // Create a Statement
      pstmt = conn.prepareStatement("insert into Address " +
        "(lastName, firstName, mi, telephone, email, street, city, "
        + "state, zip) values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
    }
    catch (Exception ex) {
      System.out.println(ex);
    }
  }

  /** Store a student record to the database */
  private void storeStudent(String lastName, String firstName,
      String mi, String phone, String email, String address,
      String city, String state, String zip) throws SQLException {
    pstmt.setString(1, lastName);
    pstmt.setString(2, firstName);
    pstmt.setString(3, mi);
    pstmt.setString(4, phone);
    pstmt.setString(5, email);
    pstmt.setString(6, address);
    pstmt.setString(7, city);
    pstmt.setString(8, state);
    pstmt.setString(9, zip);
    pstmt.executeUpdate();
  }
}
