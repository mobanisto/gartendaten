package de.mobanisto.gartendaten;

import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import de.topobyte.cachebusting.CacheBusting;
import de.topobyte.melon.commons.io.Resources;

@WebListener
public class InitListener implements ServletContextListener
{

	final static Logger logger = LoggerFactory.getLogger(InitListener.class);

	@Override
	public void contextInitialized(ServletContextEvent sce)
	{
		logger.info("context initialized");
		long start = System.currentTimeMillis();

		logger.info("setting up website factories");
		Website.INSTANCE.setCacheBuster(filename -> {
			return CacheBusting.resolve(filename);
		});

		logger.info("loading configuration...");
		Properties config = new Properties();
		try (InputStream input = Resources.stream("config.properties")) {
			config.load(input);
		} catch (Throwable e) {
			logger.error("Unable to load configuration", e);
		}

		Config.INSTANCE.setData("nothing");

		logger.info("loading secure configuration...");
		Properties secureConfig = new Properties();
		try (InputStream input = Resources.stream("secure.properties")) {
			secureConfig.load(input);
		} catch (Throwable e) {
			logger.error("Unable to load secure configuration", e);
		}

		long stop = System.currentTimeMillis();

		logger.info("done");
		logger.info(String.format("Initialized in %.2f seconds",
				(stop - start) / 1000d));
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce)
	{
		logger.info("context destroyed");

		logger.info("shutting down Logback");
		LoggerContext loggerContext = (LoggerContext) LoggerFactory
				.getILoggerFactory();
		loggerContext.stop();
	}

}
