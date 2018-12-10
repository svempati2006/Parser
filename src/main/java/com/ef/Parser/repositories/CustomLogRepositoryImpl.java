package com.ef.Parser.repositories;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;
import com.ef.Parser.dto.RequestDto;
import com.ef.Parser.model.LogEntry;

@Repository
public class CustomLogRepositoryImpl implements CustomLogRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	@Transactional
	public void deleteAllMessages() throws Exception {
		String[] sqlScript = { "delete from log_entries", "delete from log_comment" };
		Session session = em.unwrap(Session.class);
		for (String sql : sqlScript) {
			@SuppressWarnings("rawtypes")
			NativeQuery q = session.createNativeQuery(sql);
			q.executeUpdate();
			session.flush();
		}
	}

	@Override
	@Transactional
	public List<LogEntry> find(RequestDto params) throws Exception {
		TypedQuery<LogEntry> queryString = em
				.createQuery("SELECT l FROM LogEntry l " + " WHERE l.requestDate BETWEEN :startDate AND :endDate "
						+ "GROUP BY l.ipAddress HAVING count(l.ipAddress) >= :threshold", LogEntry.class)
				.setParameter("startDate", params.getStartDate()).setParameter("endDate", params.getEndDate())
				.setParameter("threshold", params.getThreshold());
		return queryString.getResultList();
	}

}
