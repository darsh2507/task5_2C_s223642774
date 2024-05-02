package sit707_week5;

import org.junit.Assert;
import org.junit.Test;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import java.text.SimpleDateFormat;
import java.util.Date;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WeatherControllerTest {
// This will hold the instance of the WeatherController
    private static WeatherController wController;
 // This array will store the hourly temperature values
    private static double[] hourlyTemperatures;
    
    private static SimpleDateFormat sdf;
	@Test
	public void testStudentIdentity() {
		String studentId = "s223642774";
		Assert.assertNotNull("Student ID is null", studentId);
	}

	@Test
	public void testStudentName() {
		String studentName = "Darsh Patel";
		Assert.assertNotNull("Student name is null", studentName);
	}

	 @BeforeClass
	    public static void setUp() {
	        System.out.println("+++ starting up tests +++"); 
	        // Get an instance of the WeatherController
	        wController = WeatherController.getInstance();
	        
	        // Get the total number of hours for which temperature data is available
	        int nHours = wController.getTotalHours();
	        // Initialize simple date format here
	        sdf = new SimpleDateFormat("H:m:s");
	        // Create an array to store the hourly temperature values
	        hourlyTemperatures = new double[nHours];
	       
	        // Fetch the temperature values for each hour and store them in the array
	        for (int i = 0; i < nHours; i++) {
	            hourlyTemperatures[i] = wController.getTemperatureForHour(i + 1);   
	        }
	    }
	
	  // This method will be executed once after all tests have finished
	    @AfterClass
	    public static void tearDown() {
	        System.out.println("+++ tearing down tests +++");
	        
	        // Close the WeatherController instance
	        wController.close();
	    }
	 
	@Test
	public void testTemperatureMin() {
		
		System.out.println("+++ testTemperatureMin +++");
        // Initialize the minimum temperature with a very large value
        double minTemperature = Double.MAX_VALUE;    
        // Iterate over the hourly temperature values
        for (double temp : hourlyTemperatures) {
            // Update the minimum temperature if a smaller value is found
            if (temp < minTemperature) {
                minTemperature = temp;
            }
        }
        // Assert that the calculated minimum temperature matches the cached value
        assertTrue(wController.getTemperatureMinFromCache() == minTemperature);	
	}
	
	@Test
	public void testTemperatureMax() {

		System.out.println("+++ testTemperatureMax +++");
		// Initialize the maximum temperature with a very small value
		        double maxTemperature = Double.MIN_VALUE;
		        // Iterate over the hourly temperature values
		        for (double temp : hourlyTemperatures) {
		            // Update the maximum temperature if a larger value is found
		            if (temp > maxTemperature) {
		                maxTemperature = temp;
		            }
		        }
	 // Assert that the calculated maximum temperature matches the cached value
		        assertTrue(wController.getTemperatureMaxFromCache() == maxTemperature);
	}

	@Test
	public void testTemperatureAverage() {

		 System.out.println("+++ testTemperatureAverage +++");
	        // Variable to store the sum of all temperature values
	        double sumTemp = 0;
	        // Iterate over the hourly temperature values and calculate the sum
	        for (double temp : hourlyTemperatures) {
	            sumTemp += temp;
	        }
	        // Calculate the average temperature by dividing the sum by the number of hours
	        double averageTemp = sumTemp / hourlyTemperatures.length;

	   // Assert that the calculated average temperature matches the cached value
	        assertTrue(wController.getTemperatureAverageFromCache() == averageTemp);
	}
	@Test
	public void testTemperaturePersist() {
		 int hour = 10;
	        double temperature = 19.5;
	        String currentTime = sdf.format(new Date());
	        // Act: Persist the temperature and get the persisted time
	        String persistedTime = wController.persistTemperature(hour, temperature);
	        // Wait for the simulated delay in the persistTemperature method
	        WeatherController.sleep(3); 
	        // Assert that the persisted time matches the expected current time
	        assertEquals("Persisted time should match the current time", currentTime, persistedTime);
	   	
	}
}
