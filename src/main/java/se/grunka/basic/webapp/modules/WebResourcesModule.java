package se.grunka.basic.webapp.modules;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import org.apache.log4j.Logger;
import se.grunka.basic.webapp.jersey.JsonProvider;

public class WebResourcesModule extends AbstractModule {

	private static final Logger LOGGER = Logger.getLogger(WebResourcesModule.class);

	@Override
	protected void configure() {
		Context environmentContext;
		try {
			InitialContext initialContext = new InitialContext();
			environmentContext = (Context) initialContext.lookup("java:/comp/env");
		} catch (NamingException e) {
			LOGGER.error("Could not lookup environment context", e);
			return;
		}
		int connectTimeout = 10000;
		try {
			connectTimeout = (Integer) environmentContext.lookup("webResourceConnectTimeout");
			LOGGER.info("Will use a connect timeout of " + connectTimeout + " milliseconds for all web resources");
		} catch (NamingException e) {
			LOGGER.warn("Could not read connect-timeout, will use default " + connectTimeout);
		}
		int readTimeout = 30000;
		try {
			readTimeout = (Integer) environmentContext.lookup("webResourceReadTimeout");
			LOGGER.info("Will use a read timeout of " + readTimeout + " milliseconds for all web resources");
		} catch (NamingException e) {
			LOGGER.warn("Could not read read-timeout, will use default " + readTimeout);
		}

		ClientConfig config = new DefaultClientConfig();
		config.getClasses().add(JsonProvider.class);
		Client client = Client.create(config);
		client.setConnectTimeout(connectTimeout);
		client.setReadTimeout(readTimeout);

		bind(Client.class).toInstance(client);

		try {
			bindWebResources(client, environmentContext);
		} catch (NamingException e) {
			LOGGER.error("Could not bind web resources", e);
		}
	}

	private void bindWebResources(Client client, Context environmentContext) throws NamingException {
		NamingEnumeration<NameClassPair> webResources;
		try {
			webResources = environmentContext.list("webResource");
		} catch (NamingException e) {
			LOGGER.warn("Could not find any WebResources (java:/comp/env/webResource/*)");
			return;
		}
		while (webResources.hasMore()) {
			NameClassPair jdbc = webResources.next();
			String name = jdbc.getName();
			String webResourceUrl = (String) environmentContext.lookup("webResource/" + name);
			bind(WebResource.class).annotatedWith(Names.named(name)).toInstance(client.resource(webResourceUrl));
			LOGGER.info("Bound WebResource to annotation @Named(\"" + name + "\")");
		}
	}
}
