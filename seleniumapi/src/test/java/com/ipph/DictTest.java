package com.ipph;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.SessionId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.nobodxbodon.zhconverter.简繁转换类;
import com.github.nobodxbodon.zhconverter.简繁转换类.目标;
import com.google.common.base.Strings;
import com.google.common.io.Files;
import com.ipph.domain.T_Compare;
import com.ipph.service.T_CompareRepository;
import com.ipph.util.Computeclass;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class DictTest {

	public DictTest() {


	}
	@Autowired
	T_CompareRepository t_CompareRepository;
	

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
   
    
	@Test
    public void testChrome_sogou() throws Exception{
 		Resource resource = new ClassPathResource("chromedriver.exe");
		File file = resource.getFile();
		
    	System.setProperty("webdriver.chrome.driver",file.getPath());
    	ChromeDriver driver = new ChromeDriver();
    	//驱动的网址
        driver.get("http://baike.sogou.com/");

        //浏览器窗口变大
        driver.manage().window().maximize();
        String[] dicts = {"俺咱"};
        try{
//        List<T_Compare> tbl = (List<T_Compare>)t_CompareRepository.findAll();
        for(int i=0;i<dicts.length;i++){
            	
            	WebElement webElement_searchText = driver.findElement(By.id("searchText"));
            	if(webElement_searchText == null){
            		driver.get("http://baike.sogou.com/v10000.htm");
            		webElement_searchText = driver.findElement(By.id("searchText"));
            	}
                Thread.sleep(2000);
            	webElement_searchText.clear();
            	webElement_searchText.sendKeys(dicts[i]);
                //定位按钮元素
                WebElement btn = driver.findElement(By.id("enterLemma"));
                //点击按钮
                btn.submit();
                Thread.sleep(2000);

                String currenturl = driver.getCurrentUrl();
                SessionId session = driver.getSessionId();
                WebElement body = null;
                try{
                    body = driver.findElement(By.id("lemma_container"));
                }catch (NoSuchElementException ex){
                    System.out.println(dicts[i] + "nobody");
                	continue;
                }
//            	String url = "http://baike.sogou.com/v19959.htm?fromTitle=" + row.getSx_title();
//            	driver.get(url);
//            	String source = driver.getPageSource();
            	WebElement webElement = body.findElement(By.className("abstract"));
            	WebElement webElement_title = body.findElement(By.id("title"));
            	if(webElement_title == null && webElement == null ) continue;
            	
            	String content = webElement.getText();
            	String bk_section = "abstract";
            	// 如果没有摘要则获取目录，如果目录只有一个，则取第一个，否则取词语解释
            	if(Strings.isNullOrEmpty(content)){
            		List<WebElement> list_catalog_wrap = body.findElement(By.className("catalog_wrap")).findElements(By.tagName("a"));
                	List<WebElement> list_section = body.findElements(By.tagName("section"));
                	
                	for(int order_index = 0; order_index< list_catalog_wrap.size();order_index++){
                		if(order_index == 0 || list_catalog_wrap.get(order_index).getText().equals("词语解释")){
                			content = list_section.get(order_index).findElement(By.className("section_content")).getText();
                			bk_section = list_catalog_wrap.get(order_index).getText();
                		}
                	}
            	}
            	
                System.out.println(dicts[i]);
                Thread.sleep(2000);
//                Files.write(source.getBytes(), new File(dicts[i] + ".html"));
            }
            }catch(Exception ex){
            	throw ex;
            }finally{
                driver.quit();
                //关闭驱动
                //driver.close();
            }

    }
	@Test
    public void testChrome_sogou_DB() throws Exception{
 		Resource resource = new ClassPathResource("chromedriver.exe");
		File file = resource.getFile();
		
    	System.setProperty("webdriver.chrome.driver",file.getPath());
    	WebDriver driver = new ChromeDriver();
    	//驱动的网址
        //浏览器窗口变大
        driver.manage().window().maximize();
    	driver.get("http://baike.sogou.com/v10000.htm");
    	Pageable page = new PageRequest(0,100);
        Page<T_Compare> tbl2 = (Page<T_Compare>)t_CompareRepository.findAll(page);
        List<T_Compare> tbl= tbl2.getContent();
        try{
        for(int i=0;i<tbl.size();i++){
        	
        	T_Compare row = tbl.get(i);
        	String title = row.getSxtitle();

        	WebElement webElement_searchText = driver.findElement(By.id("searchText"));
        	if(webElement_searchText == null){
        		driver.get("http://baike.sogou.com/v10000.htm");
        		webElement_searchText = driver.findElement(By.id("searchText"));
        	}
        	webElement_searchText.clear();
        	webElement_searchText.sendKeys(title);
            //定位按钮元素
            WebElement btn = driver.findElement(By.id("enterLemma"));
            //点击按钮
            btn.submit();
            Thread.sleep(5000);

            String currenturl = driver.getCurrentUrl();
            WebElement body = null;
            try{
                body = driver.findElement(By.className("lemma_container"));
            }catch (NoSuchElementException ex){
                System.out.println(title + "nobody");
            	row.setBksection("nocontainer");
            	t_CompareRepository.save(row);

            	continue;
            }
        	WebElement webElement = body.findElement(By.className("abstract"));
        	WebElement webElement_title = body.findElement(By.id("title"));
        	if(webElement_title == null && webElement == null ) continue;
        	
        	String content = webElement.getText();
        	String bk_section = "abstract";
        	// 如果没有摘要则获取目录，如果目录只有一个，则取第一个，否则取词语解释
        	if(Strings.isNullOrEmpty(content)){
        		List<WebElement> list_catalog_wrap = body.findElement(By.className("catalog_wrap")).findElements(By.tagName("a"));
            	List<WebElement> list_section = body.findElements(By.tagName("section"));
            	
            	for(int order_index = 0; order_index< list_catalog_wrap.size();order_index++){
            		if(order_index == 0 || list_catalog_wrap.get(order_index).getText().equals("词语解释")){
            			content = list_section.get(order_index).findElement(By.className("section_content")).getText();
            			bk_section = list_catalog_wrap.get(order_index).getText();
            		}
            	}
        	}
        	
        	row.setBkname("搜狗百科");
        	row.setBklink(currenturl);
        	row.setBkcontent(content);
        	row.setBktitle(webElement_title.getText());
        	row.setBksection(bk_section);
        	t_CompareRepository.save(row);
            System.out.println(row.getSxtitle());
//            Files.write(source.getBytes(), new File(dicts[i] + ".html"));
        }
        }catch(Exception ex){
        	throw ex;
        }finally{
            driver.quit();
            //关闭驱动

        }

    }
	
     public void testCompare(String temp_strA,String temp_strB){
    	
    	temp_strA = "人工智能（Artificial Intelligence），英文缩写为AI。它是研究、开发用于模拟、延伸和扩展人的智能的理论、方法、技术及应用系统的一门新的技术科学。";  
        temp_strB = "人工智能（Artificial Intelligence），英文缩写为AI。它是研究、开发用于模拟、延伸和扩展人的智能的理论、方法、技术及应用系统的一门新的技术科学。 人工智能是计算机科学的一个分支，它企图了解智能的实质，并生产出一种新的能以人类智能相似的方式做出反应的智能机器，该领域的研究包括机器人、语言识别、图像识别、自然语言处理和专家系统等。人工智能从诞生以来，理论和技术日益成熟，应用领域也不断扩大，可以设想，未来人工智能带来的科技产品，将会是人类智慧的“容器”，也可能超过人的智能。[1]";
        compare(temp_strA,temp_strB);
    }
    private double compare(String temp_strA,String temp_strB){
    	
        String strA,strB;  
        double result = 0;
        //如果两个textarea都不为空且都不全为符号，则进行相似度计算，否则提示用户进行输入数据或选择文件  
        if(!(Computeclass.removeSign(temp_strA).length() == 0 && Computeclass.removeSign(temp_strB).length() == 0)){  
            strA = temp_strA;  
            strB = temp_strB;  
            result = Computeclass.SimilarDegree2(strA, strB);   
            //显示相似内容于textarea_res  
            System.out.println("相似的内容为："+Computeclass.longestCommonSubstring(strA, strB));
            System.out.println("相似度为：" + Computeclass.similarityResult(result));
        }else{  
            ;  
        }  
        return result;
    }
    public void test() throws IOException{
    	
    	String temp_strA="叶公好龙（shè【yè】 gōng hào lóng）是个成语：比喻自称爱好某种事物，实际上并不是真正爱好，甚至是惧怕、反感。出自 汉·刘向《新序·杂事》。";
    	List<String> list =Files.readLines(new File("叶公好龙.html"),  StandardCharsets.UTF_8);
    	String temp_strB = String.join("\r\n", list);
        compare(temp_strA,temp_strB);
    }
	@Test
    public void testLikevalue() throws Exception{
		// 繁体转简体  
		简繁转换类  converter = 简繁转换类.取实例(目标.简体);  
        String simplifiedStr = converter.转换("遮蔽貌隐藏貌唐韩愈拘幽操目揜揜兮其凝其盲耳肅肅兮聽不聞聲二刻拍案惊奇卷二八一日手中持了鋤頭去圃中掘菜忽見一個人揜揜縮縮在那瓜地中");  
		List<T_Compare> list =  t_CompareRepository.findByBktitleNotNullAndBktitleNot("");
		for(T_Compare row : list){
			double value = compare(converter.转换(row.getSxcontent()),row.getBkcontent());
			row.setLikevalue(value);
			
			t_CompareRepository.save(row);
		}
	}

}
