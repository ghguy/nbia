package gov.nih.nci.ncia.beans.basket;
import gov.nih.nci.ncia.zip.ZipManager;

import com.icesoft.faces.async.render.SessionRenderer;

/**
 * This listens to zip progress and tell ICEfaces to push a change
 * to the UI as progress increases.
 *
 * <p>This object is a result of switching to ICEfaces which
 * broke the old ajax progress bar.
 */
public class ZipManagerProgressManager implements ZipManager.ZipManagerListener {
	public ZipManagerProgressManager(/*BasketBean basketBean*/) {
		//this.basketBean = basketBean;
	}

	public void progressUpdate() {
        // Increase the finished percent, and render the page
       	SessionRenderer.render(ALL);
	}

	public void completed() {
		//let ui set this
        //basketBean.setDownloading(false);
		System.out.println("completed zipping!!!");
        SessionRenderer.render(ALL);
	}


	private static final String ALL = "all";
}
