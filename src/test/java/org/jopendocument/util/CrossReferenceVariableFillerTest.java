/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jopendocument.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.jdom.JDOMException;
import org.jopendocument.dom.ODSingleXMLDocument;
import org.jopendocument.dom.template.JavaScriptEngineTemplate;
import org.jopendocument.dom.template.TemplateException;
import org.junit.Test;

/**
 *
 * @author lali
 */
public class CrossReferenceVariableFillerTest {

    @Test
    public void testReferenceVariableReplace() throws JDOMException, IOException, TemplateException {
        Map<String, String> variables = new HashMap<String, String>();
        variables.put("AKTUALIS-IROMANY", "5000");
        variables.put("KOVETKEZO-IROMANY", "5000");

        FileInputStream inputStram = getTemplate();
        JavaScriptEngineTemplate engineTemplate = new JavaScriptEngineTemplate(inputStram);
        ODSingleXMLDocument doc = engineTemplate.createDocument();

        CrossReferenceVariableFiller filler = new CrossReferenceVariableFiller(doc, variables);
        filler.fillVariables();

        doc.saveAs(new File("target/filled.odt"));
    }

    @Test
    public void testTwoDoc() throws JDOMException, IOException, TemplateException {
        Map<String, String> variables = new HashMap<String, String>();
        variables.put("AKTUALIS-IROMANY", "5000");
        variables.put("KOVETKEZO-IROMANY", "5001");

        JavaScriptEngineTemplate firstEngine = new JavaScriptEngineTemplate(getTemplate());
        ODSingleXMLDocument firstDoc = firstEngine.createDocument();

        CrossReferenceVariableFiller firstFiller = new CrossReferenceVariableFiller(firstDoc, variables);
        firstFiller.fillVariables();

        variables.clear();
        variables.put("AKTUALIS-IROMANY", "5001");
        variables.put("KOVETKEZO-IROMANY", "5002");
        JavaScriptEngineTemplate secundEngine = new JavaScriptEngineTemplate(getTemplate());
        ODSingleXMLDocument secundDoc = secundEngine.createDocument();

        CrossReferenceVariableFiller secundFiller = new CrossReferenceVariableFiller(secundDoc, variables);
        secundFiller.fillVariables();

        firstDoc.add(secundDoc);

        firstDoc.saveAs(new File("target/filled2.odt"));
    }

    private FileInputStream getTemplate() throws FileNotFoundException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("template/cross_reference_fill.odt").getFile());
        FileInputStream inputStram = new FileInputStream(file);
        return inputStram;
    }

}
