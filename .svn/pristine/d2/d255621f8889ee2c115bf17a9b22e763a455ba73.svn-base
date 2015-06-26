package gov.nih.nci.ncia.dao;

import gov.nih.nci.ncia.internaldomain.SubmissionHistory;
import gov.nih.nci.ncia.dto.DayCountDTO;
import gov.nih.nci.ncia.dto.SeriesSubmissionCountDTO;
import gov.nih.nci.ncia.dto.SubmissionCountsDTO;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.DateType;
import org.hibernate.type.Type;

/**
 * This object encapsulates data access pertaining to the submission_history table.
 * Theoretically would contain all data access CRUD operations, but currently only
 * has "find" operations.
 */
public class SubmissionHistoryDAO extends AbstractDAO {

	/**
	 * Legal value for operationType
	 */
	public static final int NEW_IMAGE_SUBMISSION_OPERATION = SubmissionHistory.NEW_IMAGE_SUBMISSION_OPERATION;
	public static final int REPLACE_IMAGE_SUBMISSION_OPERATION = SubmissionHistory.REPLACE_IMAGE_SUBMISSION_OPERATION;
	public static final int ANNOTATION_SUBMISSION_OPERATION = SubmissionHistory.ANNOTATION_SUBMISSION_OPERATION;

   	/**
	 * For a given project/site in a date range (inclusive), return an object for each series
	 * that had a submission of the given type (annotation, new image, replace image).
	 *
	 * <p>For each series, the study id, patient id, and count of submissions for that series are
	 * included.
	 *
	 * <p>Theoretically, this method could return quite a bit of data so be careful.
	 */
    public List<SeriesSubmissionCountDTO> findSeriesSubmissionCountInTimeFrame(Date startDate,
    		                                                                   Date endDate,
                                                                               String projectName,
                                                                               String siteName,
                                                                               int operationType) {
//        String hql = "SELECT gi.seriesInstanceUID, "+
//                     "       gi.studyInstanceUID,"+  //this is cool because it must be same per group of series
//                     "       gi.patientId,"+//this is cool be cause per group
//                     "       COUNT(*) as submissionCount "+
//                     "FROM SubmissionHistory gi "+
//                     "WHERE gi.project='"+projectName+"' and "+
//                     "      gi.site='"+siteName+"' and "+
//                     "      gi.operationType="+operationType+" AND"+
//                     "      gi.submissionDate between '"+startDate+"' and '"+endDate+"' "+
//                     "GROUP BY gi.seriesInstanceUID";


        try {
        	dataAccess.open();

            ProjectionList projectionList = Projections.projectionList();
            projectionList.add(Projections.property("seriesInstanceUID"));
            projectionList.add(Projections.property("studyInstanceUID"));
            projectionList.add(Projections.property("patientId"));
            projectionList.add(Projections.rowCount());
            projectionList.add(Projections.groupProperty("seriesInstanceUID"));
            //this leads to extra select, but not going to customize code around
            //this hidden detail

            Criteria criteria = dataAccess.createCriteria(SubmissionHistory.class);
            criteria.setProjection(projectionList);
            criteria.add(Restrictions.eq(PROJECT, projectName));
            criteria.add(Restrictions.eq(SITE, siteName));
            criteria.add(Restrictions.eq(OPERATION_TYPE, operationType));
            criteria.add(createDateRangeRestriction(startDate, endDate));
            criteria.addOrder(Order.asc("patientId"));

            List<SeriesSubmissionCountDTO> seriesDtos = new ArrayList<SeriesSubmissionCountDTO>();

            List<Object[]> seriesResultList = (List<Object[]>)criteria.list();
            for(Object[] seriesResult : seriesResultList) {
            	String seriesInstanceUid = (String)seriesResult[0];
            	String studyInstanceUid = (String)seriesResult[1];
            	String patientId = (String)seriesResult[2];
            	long submissionCount = (Integer)seriesResult[3];

            	SeriesSubmissionCountDTO vsSeriesDTO  = new SeriesSubmissionCountDTO(patientId,
            			                                                             studyInstanceUid,
            			                                                             seriesInstanceUid,
            			                                                             submissionCount);

                seriesDtos.add(vsSeriesDTO);
            }
            return seriesDtos;
        }
        catch(Exception ex) {
        	throw new RuntimeException(ex);
        }
        finally {
            closeConnection(dataAccess);
        }
   }

   /**
    * For a given project/site in a date range (inclusive), find the number
    * of distinct patient id/study id/series id that were associated with
    * new image submissions.  Also return the number of new image submissions.
    */
   public SubmissionCountsDTO findImageCounts(Date startDate,
                                              Date endDate,
                                              String projectName,
                                              String siteName) {
       return findAffectedCounts(startDate,
                                  endDate,
                                  projectName,
                                  siteName,
                                  SubmissionHistory.NEW_IMAGE_SUBMISSION_OPERATION);
   }

   /**
    * For a given project/site in a date range (inclusive), find the number
    * of distinct patient id/study id/series id that were associated with
    * corrected/replace image submissions.  Also return the number of corrected/replaced image submissions.
    */
   public SubmissionCountsDTO findCorrectedCounts(Date startDate,
                                                  Date endDate,
                                                  String projectName,
                                                  String siteName) {
       return findAffectedCounts(startDate,
                                  endDate,
                                  projectName,
                                  siteName,
                                  SubmissionHistory.REPLACE_IMAGE_SUBMISSION_OPERATION);
   }

   /**
    * For a given project/site in a date range (inclusive), find the number
    * of distinct patient id/study id/series id that were associated with
    * annotation submissions.  Also return the number of annoatation submissions.
    */
   public SubmissionCountsDTO findAnnotationCounts(Date startDate,
                                                   Date endDate,
                                                   String projectName,
                                                   String siteName) {
       return findAffectedCounts(startDate,
                                  endDate,
                                  projectName,
                                  siteName,
                                  SubmissionHistory.ANNOTATION_SUBMISSION_OPERATION);
   }

   /**
    * For a given project/site in a date range (inclusive), return the number
    * of new image submissions.
    */
   public long findImageSubmissionCountInTimeFrame(Date startDate,
                                                   Date endDate,
                                                   String projectName,
                                                   String siteName) {

        return findOperationTypeCountInTimeFrame(startDate,
                                                 endDate,
                                                 projectName,
                                                 siteName,
                                                 SubmissionHistory.NEW_IMAGE_SUBMISSION_OPERATION);
   }


   /**
    * For a given project/site in a date range (inclusive), find the number
    * of series that had a submission before the time frame, and a submission in the
    * time frame, aka the count of "updated" series.
    */
   public int findUpdatedSeriesCountInTimeFrame(Date startDate,
                                                Date endDate,
                                                String projectName,
                                                String siteName) {

       return findCountInTimeFrame("updatedSeriesCountQuery",
                                   startDate,
                                   endDate,
                                   projectName,
                                   siteName);
   }

   /**
    * For a given project/site in a date range (inclusive), find the number
    * of series that DID NOT have a submission before the time frame, and a
    * they DO have a submission in the time frame, aka the count of "new" series.
    */
   public int findNewSeriesCountInTimeFrame(Date startDate,
                                            Date endDate,
                                            String projectName,
                                            String siteName) {
       return findCountInTimeFrame("newSeriesCountQuery",
                                   startDate,
                                   endDate,
                                   projectName,
                                   siteName);
   }

   /**
    * For a given project/site in a date range (inclusive), find the number
    * of studies that had a submission before the time frame, and a submission in the
    * time frame, aka the count of "updated" studies.
    */
   public int findUpdatedStudyCountInTimeFrame(Date startDate,
                                               Date endDate,
                                               String projectName,
                                               String siteName) {

       return findCountInTimeFrame("updatedStudyCountQuery",
                                   startDate,
                                   endDate,
                                   projectName,
                                   siteName);
   }

   /**
    * For a given project/site in a date range (inclusive), find the number
    * of studies that DID NOT have a submission before the time frame, and a
    * they DO have a submission in the time frame, aka the count of "new" studies.
    */
   public int findNewStudyCountInTimeFrame(Date startDate,
                                           Date endDate,
                                           String projectName,
                                           String siteName) {
       return findCountInTimeFrame("newStudyCountQuery",
                                   startDate,
                                   endDate,
                                   projectName,
                                   siteName);
   }

   /**
    * For a given project/site in a date range (inclusive), find the number
    * of patients that had a submission before the time frame, and a submission in the
    * time frame, aka the count of "updated" patients.
    */
   public int findUpdatedPatientCountInTimeFrame(Date startDate,
                                                 Date endDate,
                                                 String projectName,
                                                 String siteName) {

       return findCountInTimeFrame("updatedPatientCountQuery",
                                   startDate,
                                   endDate,
                                   projectName,
                                   siteName);
   }

   /**
    * For a given project/site in a date range (inclusive), find the number
    * of patients that DID NOT have a submission before the time frame, and a
    * they DO have a submission in the time frame, aka the count of "new" patients.
    */
   public int findNewPatientCountInTimeFrame(Date startDate,
                                             Date endDate,
                                             String projectName,
                                             String siteName) {

       return findCountInTimeFrame("newPatientCountQuery",
                                   startDate,
                                   endDate,
                                   projectName,
                                   siteName);
   }


   /**
    * For a given project/site in a date range (inclusive), find the days where
    * a submission of the given type occurred.  The day returned will be associated
    * with the count of submissions for that day (the sum of all the specified
    * operation types on that day).
    */
   public List<DayCountDTO> findSubmissionDatesInTimeFrame(Date startDate,
                                                           Date endDate,
                                                           String projectName,
                                                           String siteName,
                                                           Integer[] operationTypes) {





//       String hql = "SELECT distinct submissionDate "+
//                     "FROM SubmissionHistory gi "+
//                     "WHERE gi.project='"+projectName+"'"+_AND_+
//                     "      gi.site='"+siteName+"'"+_AND_+
//                     "      gi.operationType="+operationType+_AND_+
//                     "      gi.submissionDate between '"+startDate+"'"+_AND_+"'"+endDate+"' "+
//                     "ORDER BY gi.submissionDate";
        try{
            dataAccess.open();

            Criteria criteria = dataAccess.createCriteria(SubmissionHistory.class);

            ProjectionList projectionList = Projections.projectionList();
            projectionList.add(Projections.sqlProjection("date(submission_timestamp) as the_date",
            		                                     new String[]{"the_date"},
            		                                     new Type[] {Hibernate.DATE}));
            projectionList.add(Projections.rowCount());
            projectionList.add(Projections.sqlGroupProjection("date(submission_timestamp) as just_date",
            		                                          "just_date",
            		                                          new String[]{"just_date"},
            		                                          new Type[] {Hibernate.DATE}));
            criteria.setProjection(projectionList);
            criteria.add(Restrictions.eq(PROJECT, projectName));
            criteria.add(Restrictions.eq(SITE, siteName));
            criteria.add(Restrictions.in(OPERATION_TYPE, operationTypes));
            criteria.add(createDateRangeRestriction(startDate, endDate));
            criteria.addOrder(Order.asc(SUBMISSION_DATE));

            List<DayCountDTO> dayCountDtoList = new ArrayList<DayCountDTO>();
            List<Object[]> submissionDayAndCountList = (List<Object[]>)criteria.list();
            for(Object[] submissionDayAndCount : submissionDayAndCountList) {
            	Date day = (Date)submissionDayAndCount[0];
            	int count = (Integer)submissionDayAndCount[1];
            	DayCountDTO dayCountDTO = new DayCountDTO(day, count);
            	dayCountDtoList.add(dayCountDTO);
            }
            return dayCountDtoList;
        }
        catch(Exception ex) {
            throw new RuntimeException(ex);
        }
        finally {
            closeConnection(dataAccess);
        }
    }


   /**
    * For a given project/site in a date range (inclusive), return the number
    * of annotation submissions.
    */
    public long findAnnotationSubmissionCountInTimeFrame(Date startDate,
                                                         Date endDate,
                                                         String projectName,
                                                         String siteName) {

        return findOperationTypeCountInTimeFrame(startDate,
                                                 endDate,
                                                 projectName,
                                                 siteName,
                                                 SubmissionHistory.ANNOTATION_SUBMISSION_OPERATION);
    }

    ///////////////////////////////////////PRIVATE//////////////////////////////////////////

    private static final String PROJECT = "project";

    private static final String SITE = "site";

    private static final String OPERATION_TYPE = "operationType";

    private static final String SUBMISSION_DATE = "submissionDate";

    /**
     * This is a helper method to call the named queries that are necessary
     * for the accrual report.  Check out SubmissionHistory.hbm.xml to see the queries.
     */
    private int findCountInTimeFrame(String namedQuery,
                                     Date startDate,
                                     Date endDate,
                                     String projectName,
                                     String siteName) {
       try {
           dataAccess.open();

           SQLQuery newPatientCountQuery = (SQLQuery)dataAccess.createNamedQuery(namedQuery);
           newPatientCountQuery.setParameter("projectName", projectName);
           newPatientCountQuery.setParameter("siteName", siteName);
           newPatientCountQuery.setParameter("startDate", startDate, new DateType());
           newPatientCountQuery.setParameter("endDate", endDate, new DateType());

           List countList = newPatientCountQuery.list();
           //assert countList.size == 1
           return (Integer)countList.get(0);
       }
       catch(Exception ex) {
             throw new RuntimeException(ex);
       }
       finally {
          closeConnection(dataAccess);
       }
   }

   /**
    * For a given project/site in a date range (inclusive), find the number
    * of submissions of a given type.
    */
   private long findOperationTypeCountInTimeFrame(Date startDate,
                                                  Date endDate,
                                                  String projectName,
                                                  String siteName,
                                                  int operationType) {

        try{
            dataAccess.open();

//
//            String hql = "SELECT count(*) "+
//                         "FROM SubmissionHistory gi "+
//                         "WHERE gi.project='"+projectName+"'"+_AND_+
//                         "      gi.site='"+siteName+"'"+_AND_+
//                         "      gi.operationType="+operationType+_AND_+
//                         "      gi.submissionDate between '"+startDate+"'"+_AND_+"'"+endDate+"'";
//
//            List resultSet = dataAccess.search(hql);


            Criteria criteria = dataAccess.createCriteria(SubmissionHistory.class);

            criteria.setProjection(Projections.rowCount());
            criteria.add(Restrictions.eq(PROJECT, projectName));
            criteria.add(Restrictions.eq(SITE, siteName));
            criteria.add(Restrictions.eq(OPERATION_TYPE, operationType));
            criteria.add(createDateRangeRestriction(startDate, endDate));

            List resultSet = criteria.list();
            long count = (Integer)resultSet.get(0);

            return count;
        }
        catch(Exception ex) {
            throw new RuntimeException(ex);
        }
        finally {
            closeConnection(dataAccess);
        }
    }


    /**
     * For a given project/site in a date range (inclusive), find the number
     * of distinct patient id/study id/series id that were associated with
     * a given submission type. Also return the number of submissions of that type.
     */
    private SubmissionCountsDTO findAffectedCounts(Date startDate,
                                                   Date endDate,
                                                   String projectName,
                                                   String siteName,
                                                   int operationType) {
//        String hql = "SELECT count(distinct patientId), count(distinct studyInstanceUid), count(distinct seriesInstanceUid), count(*)"+
//                     "FROM SubmissionHistory gi "+
//                     "WHERE gi.project='"+projectName+"'"+_AND_+
//                     "      gi.site='"+siteName+"'"+_AND_+
//                     "      gi.operationType=1"+_AND_+
//                     "      gi.submissionDate between '"+startDate+"'"+_AND_+"'"+endDate+"'";

        try{
            dataAccess.open();

            ProjectionList projectionList = Projections.projectionList();
            projectionList.add(Projections.countDistinct("patientId"));
            projectionList.add(Projections.countDistinct("studyInstanceUID"));
            projectionList.add(Projections.countDistinct("seriesInstanceUID"));
            projectionList.add(Projections.rowCount());

            Criteria criteria = dataAccess.createCriteria(SubmissionHistory.class);
            criteria.setProjection(projectionList);
            criteria.add(Restrictions.eq(PROJECT, projectName));
            criteria.add(Restrictions.eq(SITE, siteName));
            criteria.add(Restrictions.eq(OPERATION_TYPE, operationType));
            criteria.add(createDateRangeRestriction(startDate, endDate));

            List<Object[]> countsList = (List<Object[]>)criteria.list();
            //List<Long[]> countsList = (List<Long[]>)dataAccess.search(hql);
            //assert size == 1
            Object[] counts = countsList.get(0);
            long correctedPatients = (Integer)counts[0];
            long correctedStudies = (Integer)counts[1];
            long correctedSeries = (Integer)counts[2];
            long correctedImages = (Integer)counts[3];

            SubmissionCountsDTO correctedCountsDTO = new SubmissionCountsDTO(correctedPatients,
                                                                             correctedStudies,
                                                                             correctedSeries,
                                                                             correctedImages);

            return correctedCountsDTO;
        }
        catch(Exception ex) {
            throw new RuntimeException(ex);
        }
        finally {
            closeConnection(dataAccess);
        }
    }

    private static Criterion createDateRangeRestriction(Date startDate,
                                                        Date endDate) {

        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	String startDateStr = sdf.format(startDate);
    	String endDateStr = sdf.format(endDate);

    	return Restrictions.sqlRestriction("date(submission_timestamp)"+
    			                           " between '"+
    			                           startDateStr+
    			                           "' and '"+
    			                           endDateStr+
    			                           "'");


    }
}
