package se.grunka.basic.webapp.modules;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import org.apache.log4j.Logger;

public class DataSourcesModule extends AbstractModule {

	private static final Logger LOGGER = Logger.getLogger(DataSourcesModule.class);

	@Override
	protected void configure() {
		try {
			bindDataSources();
		} catch (NamingException e) {
			LOGGER.error("Could not bind data sources", e);
		}
	}

	private void bindDataSources() throws NamingException {
		InitialContext initialContext = new InitialContext();
		Context environmentContext = (Context) initialContext.lookup("java:/comp/env");
		NamingEnumeration<NameClassPair> dataSources;
		try {
			dataSources = environmentContext.list("jdbc");
		} catch (NamingException e) {
			LOGGER.warn("Could not find any data sources matching java:/comp/env/jdbc/*");
			return;
		}
		while (dataSources.hasMore()) {
			NameClassPair jdbc = dataSources.next();
			String name = jdbc.getName();
			DataSource dataSource = (DataSource) environmentContext.lookup("jdbc/" + name);
			bind(DataSource.class).annotatedWith(Names.named(name)).toInstance(dataSource);
			LOGGER.info("Bound DataSource to annotation @Named(\"" + name + "\")");
		}
	}
}
