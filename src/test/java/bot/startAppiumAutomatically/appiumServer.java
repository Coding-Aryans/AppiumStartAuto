package bot.startAppiumAutomatically;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;


public class appiumServer {
	
	static String Nodepath="C:\\Program Files\\nodejs\\node.exe";
	static String AppiumMainJs_path="C:\\Users\\Akshay Katoch\\AppData\\Local\\Programs\\Appium\\resources\\app\\node_modules\\appium\\build\\lib\\main.js";
	static AppiumDriverLocalService service;
	public static AndroidDriver<MobileElement> driver;
	
	static SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH-mm:ss.SSS");
	
	@BeforeSuite
	public void startServer() throws InterruptedException {
		service=AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
				.usingDriverExecutable(new File(Nodepath))
				.withAppiumJS(new File(AppiumMainJs_path))
				.withIPAddress("127.0.0.1")
				.usingPort(4723)
//				.withLogFile(new File("C:\\Users\\Akshay Katoch\\Downloads\\appiumlogs\\logs.txt"))
				);
		
		System.out.println("Starting Appium server..."+"\n"+df.format(new Date())+
				            "\n-------------------------------------------------------------------------");
	service.start();
	Thread.sleep(1000);
	}
	@Test(priority = 1)
	public void testserver() throws MalformedURLException {
		
		
//		System.out.println("the URL IS:"+service.getUrl().toString());
//		System.out.println("service is running"+service.isRunning());
		
		DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability(CapabilityType.BROWSER_NAME, "Chrome");
		cap.setCapability(MobileCapabilityType.DEVICE_NAME, "Android");
		driver = new AndroidDriver<MobileElement>(new URL(service.getUrl().toString()), cap);
		driver.manage().timeouts().implicitlyWait(10L, TimeUnit.SECONDS);
		driver.get("https://dtappdemo.wpengine.com");
		driver.findElement(By.cssSelector("#user_login")).sendKeys("hello appium........");
//		Thread.sleep(1000);
		
		
	}
	
//	@Test(priority = 2)
//	public void setup() throws InterruptedException, MalformedURLException {
//		DesiredCapabilities cap = new DesiredCapabilities();
//        cap.setCapability(CapabilityType.BROWSER_NAME, "Chrome");
//		cap.setCapability(MobileCapabilityType.DEVICE_NAME, "Android");
//		driver = new AndroidDriver<MobileElement>(new URL(service.getUrl().toString()), cap);
//		Thread.sleep(1000);
//	}
//	
	@AfterSuite
	public  void topServer() {
		if (service.isRunning()==true) {
			service.stop();
			System.out.println("\n----------------------------------------------------------------------------"+
			                   "\n Stopping Appium Server.."+"\n"+df.format(new Date()));
		}
	}

}
