package kz.demo.kursValut;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class SimpleBot extends TelegramLongPollingBot {
    private String curApi = "https://v6.exchangerate-api.com/v6/b4731054a5d115d6243db615/latest/USD";
    private String botUserName = "valutKursBot"; // add here your botusername
    private String botToken = "5336957855:AAE7krT-1en2Js-0hz_6zjR6uJnZzMDS2m0"; // Add here your token
    int numChecker = 0;
    // 1 for rub, 2 for kzt

    @Override
    public void onUpdateReceived(Update update) {
        String command = update.getMessage().getText();

        if(command.equals("/global")) { // PRINTS 1DOLLAR and how much rub and kzt
            String strRUB = "";
            String strEUR = "";
            String strKZT = "";
            try {
                strEUR = urlConnector.CursReatern(3);
                strKZT = urlConnector.CursReatern(2);
                strRUB = urlConnector.CursReatern(1);

            } catch (IOException | JSONException e1) {
                e1.printStackTrace();
            }
            
             String msg = "USD - 1$\nRUB - " + strRUB + "₽\nKZT - " + strKZT + "₸\nEUR - " + strEUR+"€";

             SendMessage resp = new SendMessage();
             resp.setChatId(update.getMessage().getChatId().toString());
             resp.setText(msg);

             try {
                execute(resp);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

        // with entering symbol to convert
        if(command.equals("/entercurrency")) {
            SendMessage resp = new SendMessage();
            resp.setChatId(update.getMessage().getChatId().toString());
            resp.setParseMode(ParseMode.MARKDOWN);
            //System.out.println("Started Working");
            resp.setText("Click button to choose unit!");
            //Button
            ReplyKeyboardMarkup replyMarkup = new ReplyKeyboardMarkup();
            replyMarkup.setResizeKeyboard(true);
            List<KeyboardRow> keyboardRowsList = new ArrayList<>();
            KeyboardRow keyboardRow1 = new KeyboardRow();
            KeyboardButton keyboardButton1 = new KeyboardButton();
            KeyboardButton keyboardButton2 = new KeyboardButton();
            KeyboardButton keyboardButton3 = new KeyboardButton();
            KeyboardButton keyboardButton4 = new KeyboardButton();
            keyboardButton1.setText("USD");
            keyboardButton2.setText("KZT");
            keyboardButton3.setText("EUR");
            keyboardButton4.setText("RUB");
            keyboardRow1.add(keyboardButton1);
            keyboardRow1.add(keyboardButton2);
            keyboardRow1.add(keyboardButton3);
            keyboardRow1.add(keyboardButton4);
            
            keyboardRowsList.add(keyboardRow1);

            replyMarkup.setKeyboard(keyboardRowsList);
            resp.setReplyMarkup(replyMarkup);;
            
            try {
                execute(resp);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
            
        }
        else if(command.equals("USD")) {
            SendMessage resp = new SendMessage();
            resp.setChatId(update.getMessage().getChatId().toString());
            resp.setParseMode(ParseMode.MARKDOWN);
            resp.setText("Enter number:");
            numChecker = 4;
            try {
                execute(resp);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

        else if(command.equals("RUB")) {
            SendMessage resp = new SendMessage();
            resp.setChatId(update.getMessage().getChatId().toString());
            resp.setParseMode(ParseMode.MARKDOWN);
            resp.setText("Enter number:");
            numChecker = 1;
            try {
                execute(resp);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
            
        }
        else if(command.equals("KZT")) {
            SendMessage resp = new SendMessage();
            resp.setChatId(update.getMessage().getChatId().toString());
            resp.setParseMode(ParseMode.MARKDOWN);
            resp.setText("Enter number:");
            numChecker = 2;
            try {
                execute(resp);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

        else if(command.equals("EUR")) {
            SendMessage resp = new SendMessage();
            resp.setChatId(update.getMessage().getChatId().toString());
            resp.setParseMode(ParseMode.MARKDOWN);
            resp.setText("Enter number:");
            numChecker = 3;
            try {
                execute(resp);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

        if(urlConnector.isNumeric(command) && numChecker == 2) { //KZT

            SendMessage message = new SendMessage();
            message.setChatId(update.getMessage().getChatId().toString());
            message.setParseMode(ParseMode.MARKDOWN);
            double typedKzt = Double.parseDouble(update.getMessage().getText().toString());

            double eur = urlConnector.cursNum(3); // eur - rub
            double kzt = urlConnector.cursNum(2); // 1eur - x
            double rub = urlConnector.cursNum(1); // 1usd - rub
            double usd = 1.0;
            
            double totalEur = (eur / kzt) * typedKzt;
            double totalUsd = (usd/kzt) * typedKzt;
            double totalRub = (rub / kzt) * typedKzt;

            String msg = "KZT - "+ typedKzt +
            "₸\nUSD - " + totalUsd + "$\nRUB - " + totalRub + "₽\nEUR - " + totalEur+"€";
            
            message.setText(msg);
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

        if(urlConnector.isNumeric(command) && numChecker == 1) { //RUB
            SendMessage message = new SendMessage();
            message.setChatId(update.getMessage().getChatId().toString());
            message.setParseMode(ParseMode.MARKDOWN);
            double typedRub = Double.parseDouble(update.getMessage().getText().toString());

            double eur = urlConnector.cursNum(3); // eur - rub
            double kzt = urlConnector.cursNum(2); // 1eur - x
            double rub = urlConnector.cursNum(1); // 1usd - rub
            double usd = 1.0;
            
            double totalEur = (eur / rub) * typedRub;
            double totalUsd = (usd / rub) * typedRub;
            double totalKzt = (kzt / rub) * typedRub;

            String msg = "RUB - " + typedRub +
            "₽\nUSD - " + totalUsd + "$\nKZT - " + totalKzt + "₸\nEUR - " + totalEur+"€";
            
            message.setText(msg);
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

        if(urlConnector.isNumeric(command) && numChecker == 4) { // USD
            SendMessage message = new SendMessage();
            message.setChatId(update.getMessage().getChatId().toString());
            message.setParseMode(ParseMode.MARKDOWN);
            double typedUsd = Double.parseDouble(update.getMessage().getText().toString());
            double eur = urlConnector.cursNum(3); // eur - rub
            double kzt = urlConnector.cursNum(2); // 1eur - x
            double rub = urlConnector.cursNum(1); // 1usd - rub
            
            double totalEur = eur * typedUsd;
            double totalRub = rub * typedUsd;
            double totalKzt = kzt * typedUsd;
            System.out.println("Total eur: " + totalEur);
            System.out.println("total usd: " + totalRub);
            System.out.println("total kzt: " + totalKzt);
            String msg = "USD - "+ typedUsd +
            "$\nRUB - " + totalRub + "₽\nKZT - " + totalKzt + "₸\nEUR - " + totalEur+"€";
            
            message.setText(msg);
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

        else if(urlConnector.isNumeric(command) && numChecker == 3) { // EUR
            SendMessage message = new SendMessage();
            message.setChatId(update.getMessage().getChatId().toString());
            message.setParseMode(ParseMode.MARKDOWN);
            double typedEur = Double.parseDouble(update.getMessage().getText().toString());
            double eur = urlConnector.cursNum(3); // eur - rub
            double kzt = urlConnector.cursNum(2); // 1eur - x
            double rub = urlConnector.cursNum(1); // 1usd - rub
            
            double totalUsd = typedEur/eur;
            double totalRub = (rub/eur) * typedEur;
            double totalKzt = (kzt/eur) * typedEur;
            String msg = "EUR - "+ typedEur +
            "€\nRUB - " + totalRub + "₽\nKZT - " + totalKzt + "₸\nUSD - " + totalUsd+"$";
            
            message.setText(msg);
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        

        // ABOUT TELEGRAM TEXT
        if(command.equals("/about")) {
            String Message = "I'm just noob of creating telegram bots,\n but I tried very hard to make better code to read.\n" +
            "And this Telegram bot is created by zhanta <zhantoreyesen@gmail.com>";
            SendMessage responce = new SendMessage();
            responce.setChatId(update.getMessage().getChatId().toString());
            responce.setText(Message);

            try {
                execute(responce);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

        }
        if(command.equals("/start")) {
            String Message = "Click to MENU and select service!";
            SendMessage responce = new SendMessage();
            responce.setChatId(update.getMessage().getChatId().toString());
            responce.setText(Message);

            try {
                execute(responce);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public String getBotUsername() {
        return botUserName;
    }

    @Override
    public String getBotToken() {

        return botToken;
    }

    
}
