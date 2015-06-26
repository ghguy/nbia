package com.ocpsoft.pretty.application;

/*
 * PrettyFaces is an OpenSource JSF library to create bookmarkable URLs.
 * 
 * Copyright (C) 2008 - Lincoln Baxter, III <lincoln@ocpsoft.com>
 * 
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see the file COPYING.LESSER or visit the GNU
 * website at <http://www.gnu.org/licenses/>.
 */
import java.io.IOException;

import javax.faces.application.NavigationHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ocpsoft.pretty.PrettyContext;
import com.ocpsoft.pretty.PrettyException;
import com.ocpsoft.pretty.beans.MappingUrlBuilder;
import com.ocpsoft.pretty.config.PrettyConfig;
import com.ocpsoft.pretty.config.PrettyUrlMapping;

public class PrettyNavigationHandler extends NavigationHandler
{
    private static final Log log = LogFactory.getLog(PrettyNavigationHandler.class);

    private final NavigationHandler parent;
    private final MappingUrlBuilder builder = new MappingUrlBuilder();

    public PrettyNavigationHandler(final NavigationHandler parent)
    {
        this.parent = parent;
    }

    @Override
    public void handleNavigation(final FacesContext context, final String fromAction, final String outcome)
    {
    	if(context==null) {
    		System.out.println("PF.handleNavigation sees null faces context");
    		return;
    	}
        log.debug("Navigation requested: fromAction [" + fromAction + "], outcome [" + outcome + "]");
        if (!prettyRedirect(context, outcome))
        {
            log.debug("Not a PrettyFaces navigation string - passing control to default nav-handler");
            PrettyViewHandler.setInNavigation(true);
            parent.handleNavigation(context, fromAction, outcome);
            PrettyViewHandler.setInNavigation(false);
        }
    }

    private boolean prettyRedirect(final FacesContext context, final String action)
    {
        try
        {
            PrettyContext prettyContext = PrettyContext.getCurrentInstance();
            PrettyConfig config = prettyContext.getConfig();
            if(config==null) {
            	System.out.println("Pf config is null.");
            	return false;
            }
            ExternalContext externalContext = context.getExternalContext();
            if ((PrettyContext.PRETTY_PREFIX.equals(action)) && prettyContext.isPrettyRequest())
            {
                String url = prettyContext.getOriginalRequestUrl();
                log.info("Refreshing requested page [" + url + "]");
                externalContext.redirect(url);
                return true;
            }
            else if ((action != null) && config.isMappingId(action))
            {
                PrettyUrlMapping mapping = config.getMappingById(action);
                if (mapping != null)
                {
                    String url = builder.getURL(mapping);
                    log.info("Redirecting to mappingId [" + mapping.getId() + "], [" + url + "]");
                    externalContext.redirect(url);
                }
                else
                {
                    throw new PrettyException("PrettyFaces: Invalid mapping id supplied to navigation handler: "
                            + action);
                }
                return true;
            }
        }
        catch (IOException e)
        {
            throw new RuntimeException("PrettyFaces: redirect failed for target: " + action, e);
        }
        return false;
    }
}
