package com.ipph;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.google.common.base.Strings;
import com.google.common.io.Files;
import com.ipph.domain.T_Compare;
import com.ipph.service.T_CompareRepository;
import com.ipph.util.Computeclass;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
	
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
    	WebDriver driver = new FirefoxDriver();
    	//驱动的网址
        driver.get("http://www.baidu.com/");


        //浏览器窗口变大
        driver.manage().window().maximize();
        //定位输入框元素
        WebElement txtbox = driver.findElement(By.name("wd"));

        //在输入框输入文本
        txtbox.sendKeys("HelloWorld");

        //定位按钮元素
        WebElement btn = driver.findElement(By.id("su"));

        //点击按钮
        btn.click();


        //关闭驱动
        driver.close();

    }
    
    public void testChrome() throws IOException{
 		Resource resource = new ClassPathResource("chromedriver.exe");
		File file = resource.getFile();
		
    	System.setProperty("webdriver.chrome.driver",file.getPath());
    	WebDriver driver = new ChromeDriver();
    	//驱动的网址
        driver.get("http://www.baidu.com/");


        //浏览器窗口变大
        driver.manage().window().maximize();
        //定位输入框元素
        WebElement txtbox = driver.findElement(By.name("wd"));

        //在输入框输入文本
        txtbox.sendKeys("HelloWorld");
        String source = driver.getPageSource();
        System.out.println(source);
        //定位按钮元素
        WebElement btn = driver.findElement(By.id("su"));

        //点击按钮
        btn.click();

        driver.quit();
        //关闭驱动
        driver.close();

    }
   
    
    public void testChrome_sogou() throws Exception{
 		Resource resource = new ClassPathResource("chromedriver.exe");
		File file = resource.getFile();
		
    	System.setProperty("webdriver.chrome.driver",file.getPath());
    	WebDriver driver = new ChromeDriver();
    	//驱动的网址
        driver.get("http://baike.sogou.com/");

        //浏览器窗口变大
        driver.manage().window().maximize();
        String[] dicts = {"俺咱","揜于","叶公好龙","邯郸学步"};
        try{
        for(int i=0;i<dicts.length;i++){
        	
        	WebElement webElement_searchText = driver.findElement(By.id("searchText"));
        	if(webElement_searchText == null){
        		driver.get("http://baike.sogou.com/v10000.htm");
        		webElement_searchText = driver.findElement(By.id("searchText"));
        	}
        	webElement_searchText.clear();
        	webElement_searchText.sendKeys(dicts[i]);
            //定位按钮元素
            WebElement btn = driver.findElement(By.id("enterLemma"));
            //点击按钮
            btn.submit();
            String url = driver.getCurrentUrl();
//        	String url = "http://baike.sogou.com/v19959.htm?fromTitle=" + row.getSx_title();
//        	driver.get(url);
//        	String source = driver.getPageSource();
        	WebElement webElement = driver.findElement(By.className("abstract"));
        	WebElement webElement_title = driver.findElement(By.id("title"));
        	if(webElement_title == null && webElement == null ) continue;
        	
        	String content = webElement.getText();
        	if(Strings.isNullOrEmpty(content)){
        		List<WebElement> list_catalog_wrap = driver.findElement(By.className("catalog_wrap")).findElements(By.tagName("a"));
            	List<WebElement> list_order = driver.findElements(By.className("order"));
            	List<WebElement> list_section = driver.findElements(By.tagName("section"));
            	for(int order_index = 0; order_index< list_catalog_wrap.size();order_index++){
            		if(list_catalog_wrap.get(order_index).getText().equals("词语解释")){
            			content = list_section.get(order_index).findElement(By.className("section_content")).getText();
            		}
            	}
        	}
        	
            System.out.println(dicts[i]);
            Thread.sleep(2000);
//            Files.write(source.getBytes(), new File(dicts[i] + ".html"));
        }
        }catch(Exception ex){
        	throw ex;
        }finally{
            driver.quit();
            //关闭驱动
            driver.close();
        }

    }
     public void testCompare(String temp_strA,String temp_strB){
    	
    	temp_strA = "人工智能（Artificial Intelligence），英文缩写为AI。它是研究、开发用于模拟、延伸和扩展人的智能的理论、方法、技术及应用系统的一门新的技术科学。";  
        temp_strB = "人工智能（Artificial Intelligence），英文缩写为AI。它是研究、开发用于模拟、延伸和扩展人的智能的理论、方法、技术及应用系统的一门新的技术科学。 人工智能是计算机科学的一个分支，它企图了解智能的实质，并生产出一种新的能以人类智能相似的方式做出反应的智能机器，该领域的研究包括机器人、语言识别、图像识别、自然语言处理和专家系统等。人工智能从诞生以来，理论和技术日益成熟，应用领域也不断扩大，可以设想，未来人工智能带来的科技产品，将会是人类智慧的“容器”，也可能超过人的智能。[1]";
        compare(temp_strA,temp_strB);
    }
    private void compare(String temp_strA,String temp_strB){
    	
        String strA,strB;  
        //如果两个textarea都不为空且都不全为符号，则进行相似度计算，否则提示用户进行输入数据或选择文件  
        if(!(Computeclass.removeSign(temp_strA).length() == 0 && Computeclass.removeSign(temp_strB).length() == 0)){  
            strA = temp_strA;  
            strB = temp_strB;  
            double result = Computeclass.SimilarDegree(strA, strB);   
            //显示相似内容于textarea_res  
            System.out.println("相似的内容为："+Computeclass.longestCommonSubstring(strA, strB));
            System.out.println("相似度为：" + Computeclass.similarityResult(result));
        }else{  
            ;  
        }  
    }
    public void test() throws IOException{
    	
    	String temp_strA="叶公好龙（shè【yè】 gōng hào lóng）是个成语：比喻自称爱好某种事物，实际上并不是真正爱好，甚至是惧怕、反感。出自 汉·刘向《新序·杂事》。";
    	List<String> list =Files.readLines(new File("叶公好龙.html"),  StandardCharsets.UTF_8);
    	String temp_strB = String.join("\r\n", list);
        compare(temp_strA,temp_strB);
    }
    
}
