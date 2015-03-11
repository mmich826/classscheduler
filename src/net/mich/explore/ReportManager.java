package net.mich.explore;

import net.mich.explore.report.ActivityReportGenerator;
import net.mich.explore.report.FullReportGenerator;
import net.mich.explore.report.StudentReportGenerator;

import org.apache.log4j.Logger;

public class ReportManager {
	private static final Logger LOGGER = Logger.getLogger(ReportManager.class);

	
	public void runReports(SchedulerMain th) {
		
		String rpt = null;
		
		new FullReportGenerator().generate(th);
		
		rpt = new ActivityReportGenerator().generate(th);

		rpt = new StudentReportGenerator().generate(th);
		
		LOGGER.debug(rpt);
	}

}
