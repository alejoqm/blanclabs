package org.blanclabas.adjudicationperformance.service;

import java.io.IOException;
import org.blanclabas.adjudicationperformance.dao.AdjudicationDao;
import org.blanclabas.adjudicationperformance.dao.DomainActionsDao;
import org.blanclabas.adjudicationperformance.utils.FileUtil;

public class PerformanceService {

	public void executePerformanceTest(String fileName) throws IOException {
		String domainId = DomainActionsDao.getDomainId("DDIQ");
		AdjudicationDao.deleteByUser("batch");
		AdjudicationDao.insertHit("batch", domainId);

		AdjudicationDao.executeBatch(FileUtil.loadFileContent(fileName), DomainActionsDao.getDomainActionId("CONFIRM", domainId));
	}
}
