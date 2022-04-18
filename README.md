# Exchange Rate Telegram Bot
Telegram bot helps you quickly find the dollar exchange rate. There is a currency converter for convenience.
This project is created by technologies like:
- Java
- [Spring Boot](https://github.com/spring-projects/spring-boot)
- [telegrambots API](https://github.com/rubenlagus/TelegramBots)
- [json-simple](https://github.com/fangyidong/json-simple)
### Install 
-   Download project and import to your favourite IDE(Eclipse, Intellij IDEA, Visual Studio and etc.) that has maven!
-   Open IDE and wait installing libraries and frameworks
-   And write your informations in class SimpleBot to this blocks
```java
public class SimpleBot extends TelegramLongPollingBot {
    String curApi = "https://v6.exchangerate-api.com/v6/{Here write your api token or replace with other}/latest/USD"; // <---
    String botUserName = ""; // add here your botusername  <---
    String botToken = ""; // Add here your token <---
```
-   If you wanna use new version of the frameworks, just rename version in pom.xml file
```xml
<dependency>
    <groupId>com.googlecode.json-simple</groupId>
    <artifactId>json-simple</artifactId>
    <version>{here}</version> 
</dependency>
```
# Image
![image](https://github.com/zhanta/kursValut/blob/master/photos/1.png)