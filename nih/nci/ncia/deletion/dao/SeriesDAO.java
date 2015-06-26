package gov.nih.nci.ncia.deletion.dao;

import gov.nih.nci.ncia.internaldomain.GeneralSeries;
import gov.nih.nci.ncia.exception.DataAccessException;

import java.util.List;

public interface SeriesDAO {
	public List<Integer> listAllDeletedSeries() throws DataAccessException;
	public void removeSeries(List<Integer> seriesObjects, String str)throws DataAccessException;
	public List<GeneralSeries> getSeriesObject(List<Integer> seriesIds) throws DataAccessException;
}
