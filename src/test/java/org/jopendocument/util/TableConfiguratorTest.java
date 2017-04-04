package org.jopendocument.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.jdom.JDOMException;
import org.jopendocument.dom.ODSingleXMLDocument;
import org.jopendocument.dom.template.JavaScriptEngineTemplate;
import org.jopendocument.dom.template.TemplateException;
import org.junit.Test;

/**
 *
 * @author lali
 */
public class TableConfiguratorTest {

    @Test
    public void testSomeMethod() throws JDOMException, IOException, TemplateException {
        FileInputStream inputStram = getTemplate();
        JavaScriptEngineTemplate engineTemplate = new JavaScriptEngineTemplate(inputStram);
        ODSingleXMLDocument doc = engineTemplate.createDocument();
        
        TableConfigurator configurator = new TableConfigurator(doc);
        configurator.denyRowToBreak();
        
        doc.saveAs(new File("target/deny-row-to-break.odt"));
    }

    private FileInputStream getTemplate() throws FileNotFoundException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("template/allow-row-to-break.odt").getFile());
        FileInputStream inputStram = new FileInputStream(file);
        return inputStram;
    }
}
