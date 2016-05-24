package org.jopendocument.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jdom.Attribute;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jopendocument.dom.ODSingleXMLDocument;

/**
 *
 * @author lali
 */
public class CrossReferenceVariableFiller {

    private final static Namespace TEXT_NAMESPACE = Namespace.getNamespace("text", "urn:oasis:names:tc:opendocument:xmlns:text:1.0");

    private final ODSingleXMLDocument document;
    private final Map<String, String> variables;
    private final String regexp;

    public CrossReferenceVariableFiller(ODSingleXMLDocument document, Map<String, String> variables) {
        this.document = document;
        this.variables = variables;
        regexp = "(.+-)\\[(.+)\\]";
    }

    public CrossReferenceVariableFiller(ODSingleXMLDocument document, Map<String, String> variables, String regexp) {
        this.document = document;
        this.variables = variables;
        this.regexp = regexp;
    }

    public void fillVariables() {
        List<Element> contents = document.getBody().getContent();
        explore(contents);
    }

    private void explore(List<Element> elements) {
        if (elements == null) {
            return;
        }

        for (Element element : elements) {
            replaceMarkerVariables(element);

            explore(element.getChildren());
        }
    }

    private void replaceMarkerVariables(Element element) {
        List<Element> markers = getMarkers(element);
        for (Element marker : markers) {
            fillMarkerVariable(marker);
        }

        List<Element> references = getReference(element);
        for (Element reference : references) {
            fillReferenceVariable(reference);
        }
    }

    private List<Element> getMarkers(Element element) {
        ArrayList<Element> markers = new ArrayList<Element>();

        markers.addAll(element.getChildren("reference-mark", TEXT_NAMESPACE));
        markers.addAll(element.getChildren("reference-mark-start", TEXT_NAMESPACE));
        markers.addAll(element.getChildren("reference-mark-end", TEXT_NAMESPACE));

        return markers;
    }

    private void fillMarkerVariable(Element element) {
        Attribute nameAttribute = element.getAttribute("name", TEXT_NAMESPACE);
        fillVariable(nameAttribute);
    }

    private List<Element> getReference(Element element) {
        return element.getChildren("reference-ref", TEXT_NAMESPACE);
    }

    private void fillReferenceVariable(Element element) {
        Attribute nameAttribute = element.getAttribute("ref-name", TEXT_NAMESPACE);
        fillVariable(nameAttribute);
    }

    private void fillVariable(Attribute nameAttribute) {
        Matcher nameMatcher = createMatcher(regexp, nameAttribute.getValue());
        if (nameMatcher.find()) {
            String referenceNamePrefix = nameMatcher.group(1);
            String iromanyId = getVariable(nameMatcher.group(2));
            nameAttribute.setValue(referenceNamePrefix + iromanyId);
        }
    }

    private String getVariable(String variableName) {
        String value = variables.get(variableName);
        if (value == null) {
            throw new IllegalArgumentException("Unknown variable: " + variableName);
        }

        return value;
    }

    private Matcher createMatcher(String regexp, String text) {
        Pattern pattern = Pattern.compile(regexp, 100);
        return pattern.matcher(text);
    }
}
