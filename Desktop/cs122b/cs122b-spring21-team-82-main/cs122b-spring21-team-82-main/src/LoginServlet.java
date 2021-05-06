import com.google.gson.JsonObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@WebServlet(name = "LoginServlet", urlPatterns = "/api/login")
public class LoginServlet extends HttpServlet {

    //declare a data base
    private DataSource dataSource;

    //connect data source to database
    public void init(ServletConfig config) {
        try {
            dataSource = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/moviedb");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
        //try logging in
        try {
            //get connection from data source
            Connection conn = dataSource.getConnection();
            //declare statement
            Statement statement = conn.createStatement();

            //grab username and password from login.js
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            //check if corresponding email and password is in the customers table
            String user_valid = String.format("SELECT * from customers " +
                    "where email = '%s' and password = '%s';", username, password);

            //save the query results
            ResultSet rs = statement.executeQuery(user_valid);

            //convert query results to strings
            String email_result = "";
            String password_result = "";
            while (rs.next()) {
//                email_result = rs.getString("email");
//                password_result = rs.getString("password");
                email_result = "found";
                password_result = "found";
            }
            rs.close();
            statement.close();

            //json response object to send to
            JsonObject responseJsonObject = new JsonObject();

            //recaptcha checker
            if(RecaptchaVerifyUtils.verify(gRecaptchaResponse) == true){
                if (email_result.equals("found") && password_result.equals("found")) {
                    request.getSession().setAttribute("user", new User(username));
                    responseJsonObject.addProperty("status", "success");
                    responseJsonObject.addProperty("message", "success");
                }
                //failure --> set message to frontend to show that the credentials are invalid
                else {
                    // Login fail
                    responseJsonObject.addProperty("status", "fail");
                    responseJsonObject.addProperty("message", "invalid credentials");
                }
            }
            else{
                responseJsonObject.addProperty("status", "fail");
                responseJsonObject.addProperty("message", "reCaptcha failed");
            }
            //if the username and password exists and matches, set up the user into the session
            //write json string to output
            response.getWriter().write(responseJsonObject.toString());
        }
        catch (Exception e) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("errorMessage", e.getMessage());
            response.setStatus(500);
        }
        finally {
            System.out.close();
        }
    }
}