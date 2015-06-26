/**
* $Id: ImageTableRenderer.java 7209 2009-01-23 18:06:35Z kascice $
*
* $Log: not supported by cvs2svn $
* Revision 1.1  2007/08/05 21:06:47  bauerd
* Initial Check in of reorganized components
*
* Revision 1.9  2006/09/27 20:46:27  panq
* Reformated with Sun Java Code Style and added a header for holding CVS history.
*
*/
package gov.nih.nci.ncia.components;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.component.UIColumn;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.el.ValueBinding;

import org.apache.log4j.Logger;
import com.sun.faces.renderkit.html_basic.TableRenderer;



public class ImageTableRenderer extends TableRenderer {
    private static Logger log = Logger.getLogger(ImageTableRenderer.class);

    public void encodeChildren(FacesContext context, UIComponent component)
        throws IOException {
        if ((context == null) || (component == null)) {
        	throw new RuntimeException("image table renderer context comp null");
//            throw new NullPointerException(Util.getExceptionMessageString(
//                    Util.NULL_PARAMETERS_ERROR_MESSAGE_ID));
        }

        if (log.isDebugEnabled()) {
            log.debug("Begin encoding children " + component.getId());
        }

        if (!component.isRendered()) {
            if (log.isDebugEnabled()) {
                log.debug("No encoding necessary " + component.getId() +
                    " since " + "rendered attribute is set to false ");
            }

            return;
        }

        UIData data = (UIData) component;

        // Set up variables we will need
        String[] columnClasses = getColumnClasses(data);
        int columnStyle = 0;
        int columnStyles = columnClasses.length;
        String[] rowClasses = getRowClasses(data);
        int rowStyles = rowClasses.length;
        ResponseWriter writer = context.getResponseWriter();
        Iterator kids = null;
        Iterator grandkids = null;

        // Iterate over the rows of data that are provided
        int processed = 0;
        int rowIndex = data.getFirst() - 1;
        Integer numRows = (Integer) get(context, component, "viewRows");

        if (numRows == null) {
            numRows = 5;
        }

        int rowStyle = 0;
        Integer columns = (Integer) get(context, component, "columns");

        if (columns == null) {
            columns = 3;
        }

        int rows = numRows ;

        writer.startElement("tbody", component);
        writer.writeText("\n", null);

        while (true) {
            // Have we displayed the requested number of rows?
            if ((rows > 0) && (++processed > rows)) {
                writer.endElement("tr");
                writer.writeText("\n", null);

                break;
            }

            // Select the current row
            data.setRowIndex(++rowIndex);

            if (!data.isRowAvailable()) {
                writer.endElement("tr");
                writer.writeText("\n", null);

                break; // Scrolled past the last row
            }

            // Render the beginning of this row
            if (((processed - 1) % columns) == 0) {
                writer.startElement("tr", data);

                if (rowStyles > 0) {
                    writer.writeAttribute("class", rowClasses[rowStyle++],
                        "rowClasses");

                    if (rowStyle >= rowStyles) {
                        rowStyle = 0;
                    }
                }

                writer.writeText("\n", null);
            }

            // Iterate over the child UIColumn components for each row
            columnStyle = 0;
            kids = getColumns(data);

            while (kids.hasNext()) {
                // Identify the next renderable column
                UIColumn column = (UIColumn) kids.next();

                // Render the beginning of this cell
                writer.startElement("td", column);

                if (columnStyles > 0) {
                    writer.writeAttribute("class",
                        columnClasses[columnStyle++], "columnClasses");

                    if (columnStyle >= columnStyles) {
                        columnStyle = 0;
                    }
                }

                // Render the contents of this cell by iterating over
                // the kids of our kids
                grandkids = getChildren(column);

                while (grandkids.hasNext()) {
                    encodeRecursive(context, (UIComponent) grandkids.next());
                }

                // Render the ending of this cell
                writer.endElement("td");
                writer.writeText("\n", null);
            }

            // Render the ending of this row
            if ((processed  % columns) == 0) {
                writer.endElement("tr");
                writer.writeText("\n", null);
            }
        }

        writer.endElement("tbody");
        writer.writeText("\n", null);

        // Clean up after ourselves
        data.setRowIndex(-1);

        if (log.isDebugEnabled()) {
            log.debug("End encoding children " + component.getId());
        }
    }

    private String[] getColumnClasses(UIData data) {
        String values = (String) data.getAttributes().get("columnClasses");

        if (values == null) {
            return (new String[0]);
        }

        values = values.trim();

        ArrayList<String> list = new ArrayList<String>();

        while (values.length() > 0) {
            int comma = values.indexOf(',');

            if (comma >= 0) {
                list.add(values.substring(0, comma).trim());
                values = values.substring(comma + 1);
            } else {
                list.add(values.trim());
                values = "";
            }
        }

        String[] results = new String[list.size()];

        return ((String[]) list.toArray(results));
    }

    private String[] getRowClasses(UIData data) {
        String values = (String) data.getAttributes().get("rowClasses");

        if (values == null) {
            return (new String[0]);
        }

        values = values.trim();

        ArrayList<String> list = new ArrayList<String>();

        while (values.length() > 0) {
            int comma = values.indexOf(',');

            if (comma >= 0) {
                list.add(values.substring(0, comma).trim());
                values = values.substring(comma + 1);
            } else {
                list.add(values.trim());
                values = "";
            }
        }

        String[] results = new String[list.size()];

        return ((String[]) list.toArray(results));
    }

    private Iterator getColumns(UIData data) {
        List<UIComponent> results = new ArrayList<UIComponent>();
        Iterator kids = data.getChildren().iterator();

        while (kids.hasNext()) {
            UIComponent kid = (UIComponent) kids.next();

            if ((kid instanceof UIColumn) && kid.isRendered()) {
                results.add(kid);
            }
        }

        return (results.iterator());
    }

    private static Object get(FacesContext context, UIComponent component,
        String name) {
        ValueBinding binding = component.getValueBinding(name);

        if (binding != null) {
            return binding.getValue(context);
        } else {
            return component.getAttributes().get(name);
        }
    }
}
