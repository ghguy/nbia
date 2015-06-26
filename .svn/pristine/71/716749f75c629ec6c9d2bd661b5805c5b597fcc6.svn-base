package gov.nih.nci.ncia.viewer;

import gov.nih.nci.ncia.db.DataAccessProxy;
import gov.nih.nci.ncia.db.Hibernate3DataAccess;
import gov.nih.nci.ncia.db.IDataAccess;
import gov.nih.nci.ncia.security.AuthorizationManager;
import gov.nih.nci.ncia.security.AuthorizationManager.RoleType;
import gov.nih.nci.ncia.internaldomain.GeneralImage;
import gov.nih.nci.ncia.internaldomain.TrialDataProvenance;
import gov.nih.nci.ncia.util.NCIAConfig;
import gov.nih.nci.ncia.util.SiteData;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class ImageServlet extends HttpServlet {
    private static Logger logger = Logger.getLogger(ImageServlet.class);
    private Hibernate3DataAccess dataAccess;

    public void init() {
        try {
            dataAccess = (Hibernate3DataAccess) new DataAccessProxy().getInstance(IDataAccess.HIBERNATE3);
        } catch (Exception e) {
            throw new RuntimeException(
                "Could not initialize Hibernate3DataAccess in QueryStorageManager");
        }
    }


    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     */

    protected boolean hasAccess(String userName, TrialDataProvenance site, String ssg)
    {
       try {
           String project = site.getProject();
           String siteName = site.getDpSiteName();

           AuthorizationManager am = new AuthorizationManager(userName);
           List<SiteData> siteList = am.getAuthorizedSites(RoleType.READ);
           List<String> ssgList = am.getAuthorizedSeriesSecurityGroups(RoleType.READ);

            for (SiteData siteData : siteList)
            {
                 if ((siteData.getCollection().equals(project) &&
                        siteData.getSiteName().equals(siteName))) {
                    if (!isBlank(ssg)) {
                        for (String authSsg : ssgList) {
                            if (authSsg.equals(ssg)) {
                                return true;
                            }
                        }
                    } else if (isBlank(ssg)) {
                        return true;
                    }
                }
            }
            dataAccess.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            return false;
        }

        return false;
    }

    protected String fileNameReMap(String fullName)
    {
    	String [] patterns = NCIAConfig.getImagePathPattern().split(",");
    	String [] headers=NCIAConfig.getMappedImagePathHead().split(",");
    	int pos = -1;
    	for (int i = 0; i < patterns.length; ++i) {
    		if (fullName.indexOf(patterns[i])!= -1) {
    			pos = i;
    			break;
    		}
    	}

    	if (pos==-1) {
    		return "NCIA File Mapping Error!";
    	}

        String [] parts=fullName.split(patterns[pos]);
        StringBuffer sb = new StringBuffer(headers[pos]);
        sb.append(parts[1]);
        logger.debug("mapped file name="+sb.toString().replaceAll("/", "\\\\"));

        return sb.toString().replaceAll("/", "\\\\");
    }

    protected List<GeneralImage> getFilePathes(String seriesId)
    {
        String query = "select distinct gimg from GeneralImage gimg join gimg.dataProvenance dp where gimg.seriesInstanceUID = '"+
        seriesId + "'";
        try {
            dataAccess.open();

            List<GeneralImage> results = dataAccess.search(query);
            return results;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
     }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        String uid = request.getParameter("uid");
        String usr = request.getParameter("usr");

        List<GeneralImage> results = getFilePathes(uid);

        if ((results == null) || results.isEmpty()) {
            logger.info("No image found for request uid="+uid);
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "No image found for series:"+uid);
        }
        else {
            TrialDataProvenance site = results.get(0).getDataProvenance();
            String ssg = results.get(0).getGeneralSeries().getSecurityGroup();

            if (hasAccess(usr, site, ssg)) {
                //print out html
                response.setContentType("text/plain;charset=UTF-8");
                PrintWriter out = response.getWriter();

                for (GeneralImage gi: results)
                {
                    String fileName = gi.getFilename();
                    String mappedName = fileNameReMap(fileName);
                    out.println(mappedName);
                }
                out.close();
            }
            else {
                logger.info("No user found. The user ID in IGS request =" +usr);
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "user "+usr+ " does not have access for series "+uid);
            }
         }

        //used for debug
        String  qString =request.getQueryString();
        logger.info("!!!query string from Cedara IGS= "+qString);

    }

    /** Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        processRequest(request, response);
    }

    /** Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
       processRequest(request, response);
    }

    /** Returns a short description of the servlet.
     */
    public String getServletInfo()
    {
        return "Series file URI list";
    }

    private boolean isBlank(String value) {
        return ((value == null) || value.equals("") || value.equals("\n") ||
        (value.length() == 0));
    }
}
