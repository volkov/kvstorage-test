package git.volkov.kvstorage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.ClassPathResource;

import git.volkov.kvstorage.storage.MongoStorage;

/**
 * Main class of test project.
 * @author Sergey Volkov
 *
 */
public class Main {
	
	private static Logger LOG = LoggerFactory.getLogger(Main.class);
	
	public static void main(String[] args) throws Exception {
		
        LOG.info("Loading context...");
		final GenericApplicationContext context = new GenericApplicationContext();
        XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(context);
        xmlReader.loadBeanDefinitions(new ClassPathResource(
                "applicationContext.xml"));
        context.refresh();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                context.close();
                LOG.info("Context is closed");
            }
        });
        StorageTester tester = (StorageTester) context.getBean("tester");
        tester.runTest();
	}
}
