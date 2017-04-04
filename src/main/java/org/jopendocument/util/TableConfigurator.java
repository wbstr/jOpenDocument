package org.jopendocument.util;

import java.util.List;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jopendocument.dom.ODDocument;
import org.jopendocument.dom.ODSingleXMLDocument;
import org.jopendocument.dom.spreadsheet.Row;
import org.jopendocument.dom.spreadsheet.RowStyle;
import org.jopendocument.dom.spreadsheet.Table;

/**
 *
 * @author lali
 */
public class TableConfigurator {

    private final ODSingleXMLDocument document;

    public TableConfigurator(ODSingleXMLDocument document) {
        this.document = document;
    }

    public void denyRowToBreak() {
        List<Element> contents = document.getBody().getContent();
        collectTables(contents);
    }

    private void collectTables(List<Element> elements) {
        if (elements == null) {
            return;
        }

        for (Element element : elements) {
            alwaysKeepTogether(element);

            collectTables(element.getChildren());
        }
    }

    private void alwaysKeepTogether(Element element) {
        if ("table".equals(element.getName())) {
            ODDocument dDocument = document.getPackage().getODDocument();
            Table<ODDocument> table = new Table<ODDocument>(dDocument, element);

            for (int i = 0; i < table.getRowCount(); i++) {
                Row<ODDocument> row = table.getRow(i);
                RowStyle privateStyle = row.getPrivateStyle();
                Element formattingProperties = privateStyle.getFormattingProperties("table-row");
                Namespace namespace = privateStyle.getNS().getNS("fo");
                formattingProperties.setAttribute("keep-together", "always", namespace);
            }
        }
    }

}
