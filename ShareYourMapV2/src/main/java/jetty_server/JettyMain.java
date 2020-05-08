package jetty_server;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import dao.datanucleus.MapDAOPersistence;
import dao.datanucleus.UserDAOPersistence;
import provider.AuthenticationFilter;

public class JettyMain {

	public static void main(String[] args) throws Exception {
		// Initialize the server
		Server server = new Server();

		// Add a connector
		ServerConnector connector = new ServerConnector(server);
		connector.setHost("0.0.0.0");
		connector.setPort(8080);
		connector.setIdleTimeout(30000);
		server.addConnector(connector);

		// Configure Jersey
		ResourceConfig rc = new ResourceConfig();
		
		rc.packages(true, "jetty_server.ws");
		rc.register(JacksonFeature.class);
		rc.register(LoggingFilter.class);
		rc.register(MultiPartFeature.class); //add MultiPart compatibility
        //rc.register(AuthenticationFilter.class);

		// Add a servlet handler for web services (/ws/*)
		ServletHolder servletHolder = new ServletHolder(new ServletContainer(rc));
		ServletContextHandler handlerWebServices = new ServletContextHandler(ServletContextHandler.SESSIONS);
		handlerWebServices.setContextPath("/ws");
		handlerWebServices.addServlet(servletHolder, "/*");

		// Add a handler for resources (/*)
		ResourceHandler handlerPortal = new ResourceHandler();
		handlerPortal.setResourceBase("src/main/webapp");
		handlerPortal.setDirectoriesListed(false);
		handlerPortal.setWelcomeFiles(new String[] { "home.html" });
		ContextHandler handlerPortalCtx = new ContextHandler();
		handlerPortalCtx.setContextPath("/");
		handlerPortalCtx.setHandler(handlerPortal);

		// Activate handlers
		ContextHandlerCollection contexts = new ContextHandlerCollection();
		contexts.setHandlers(new Handler[] { handlerWebServices, handlerPortalCtx });
		server.setHandler(contexts);

		// Cr�� user test
		/*PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("gl");
		UserDAOPersistence u = new UserDAOPersistence(pmf);
		MapDAOPersistence m = new MapDAOPersistence(pmf);
		u.createUser("Thomas", "psw", "psw");
		m.createMap(1, "Shop");
		*/
		// Start server
		server.start();

	}

}
