
import Model.Person;
import Util.ConnectionUtil;
import Util.FileUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Lab1Test {

    /**
     * In this test we are retrieving everything in the users table to ensure that Steve was successfully
     * removed and comparing it to the hardcoded values below.
     */
    @Test
    public void deleteTest(){
        //arrange
        Person person2 = new Person(2,"Alexa");
        Person person3 = new Person(4, "Brandon");
        Person person4 = new Person(5, "Adam");
        List<Person> expectedResult = new ArrayList<>();
        expectedResult.add(person2);
        expectedResult.add(person3);
        expectedResult.add(person4);

        //act
        String sql = FileUtil.parseSQLFile("src/main/lab1.sql");
        if(sql.isBlank()){
            Assert.fail("There is no SQL statement in lab1.sql.");
        }
        try {
            Connection connection = ConnectionUtil.getConnection();
            Statement s = connection.createStatement();
            s.executeUpdate(sql);
        } catch (SQLException e) {
            Assert.fail("There was an issue with your SQL statement in lab.sql: "
            +e.getMessage());
        }
        List<Person> actualResult = new ArrayList<>();
        try {
            Connection connection = ConnectionUtil.getConnection();
            String sql2 = "SELECT * FROM person;";
            PreparedStatement ps = connection.prepareStatement(sql2);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                actualResult.add(new Person(rs.getInt("id"), rs.getString("firstname")));
            }
        } catch (SQLException e) {
            Assert.fail("There was an issue querying from the Person table. "+e.getMessage());
        }
        //assert
        Assert.assertEquals("The table should have the deleted record removed.",
                expectedResult, actualResult);
    }

    /**
     * The @Before annotation runs before every test so that way we create the tables required prior to running the test
     */
    @Before
    public void beforeEach(){

        try {

            Connection connection = ConnectionUtil.getConnection();
            //Write SQL logic here
            String sql1 = "CREATE TABLE Person (id SERIAL PRIMARY KEY, firstname varchar(100));";
            String sql2 = "INSERT INTO Person (firstname) VALUES ('Steve');";
            String sql3 = "INSERT INTO Person (firstname) VALUES ('Alexa');";
            String sql4 = "INSERT INTO Person (firstname) VALUES ('Steve');";
            String sql5 = "INSERT INTO Person (firstname) VALUES ('Brandon');";
            String sql6 = "INSERT INTO Person (firstname) VALUES ('Adam');";
            PreparedStatement ps = connection.prepareStatement(sql1 + sql2 + sql3 + sql4 + sql5 + sql6);

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("There was some issue with table setup");
        }
    }

    /**
     * The @After annotation runs after every test so that way we drop the tables to avoid conflicts in future tests
     */
    @After
    public void afterEach(){
        try {
            Connection connection = ConnectionUtil.getConnection();
            String sql = "DROP TABLE Person;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException e) {
        }
    }
}
