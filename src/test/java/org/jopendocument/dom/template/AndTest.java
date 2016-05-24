package org.jopendocument.dom.template;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.jdom.JDOMException;
import org.jopendocument.dom.ODSingleXMLDocument;
import org.junit.*;

/**
 * Created by kumm on 2016.05.19..
 */
public class AndTest {

    @org.junit.Test
    public void testSomeMethod() throws IOException, TemplateException, JDOMException {
        EngineTemplate parlexEngine = getEngineTemplate("and.odt");
        Map<String, Boolean> dataMap = new HashMap<String, Boolean>();
        dataMap.put("a", Boolean.TRUE);
        dataMap.put("b", Boolean.TRUE);

        for (Map.Entry<String, Boolean> entrySet : dataMap.entrySet()) {
            String key = entrySet.getKey();
            Boolean value = entrySet.getValue();

            parlexEngine.setField(key, value);
        }

        ODSingleXMLDocument xMLDocument = parlexEngine.createDocument();
        Assert.assertNotNull(xMLDocument);
    }

    private EngineTemplate getEngineTemplate(String path) throws IOException, TemplateException, JDOMException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(path).getFile());
        FileInputStream inputStram = new FileInputStream(file);
        return new JavaScriptEngineTemplate(inputStram);
    }

}
