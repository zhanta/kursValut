package kz.demo.kursValut;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
public class KursValutApplication {

	public static void main(String[] args) {
		SpringApplication.run(KursValutApplication.class, args);
		
		try {
			TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
			telegramBotsApi.registerBot(new SimpleBot());

		} catch (TelegramApiException e) {
			e.printStackTrace();
		}

	}

}
