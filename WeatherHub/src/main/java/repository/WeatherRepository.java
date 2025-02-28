package repository;

import dao.WeatherRequestDao;
import dao.WeatherResponseDao;
import dto.WeatherAverageDto;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.time.Instant;
import java.util.List;

public class WeatherRepository {

    private final SessionFactory sessionFactory;

    public WeatherRepository() {
        this.sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(WeatherRequestDao.class)
                .addAnnotatedClass(WeatherResponseDao.class)
                .buildSessionFactory();
    }

    public WeatherRequestDao saveWeatherRequest(String city) {

        WeatherRequestDao weatherRequestDao = new WeatherRequestDao();
        weatherRequestDao.setCity(city);
        weatherRequestDao.setCreated(Instant.now());

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            try {

                session.persist(weatherRequestDao);
                transaction.commit();
            } catch (Exception e) {

                if (transaction != null) {
                    transaction.rollback();
                }
                throw e;
            }
        }

        return weatherRequestDao;
    }

    public WeatherResponseDao saveWeatherResponse(WeatherResponseDao weatherResponseDao) {

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            try {

                session.persist(weatherResponseDao);
                transaction.commit();
            } catch (Exception e) {

                if (transaction != null) {
                    transaction.rollback();
                }
                throw e;
            }
        }

        return weatherResponseDao;
    }

    public WeatherResponseDao getLastWeatherRequest(String city) {

        try (Session session = sessionFactory.openSession()) {

            Transaction transaction = session.beginTransaction();
            WeatherResponseDao lastWeatherRequestDao;
            try {

                CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

                CriteriaQuery<WeatherResponseDao> criteriaQuery = criteriaBuilder.createQuery(WeatherResponseDao.class);
                Root<WeatherResponseDao> root = criteriaQuery.from(WeatherResponseDao.class);

                Predicate locationPredicate = criteriaBuilder.equal(root.get("location"), city);
                criteriaQuery.select(root)
                        .where(locationPredicate)
                        .orderBy(criteriaBuilder.desc(root.get("created")));

                lastWeatherRequestDao = session.createQuery(criteriaQuery)
                        .setMaxResults(1)
                        .uniqueResult();

                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                throw e;
            }
            return lastWeatherRequestDao;
        }
    }

    public WeatherAverageDto getAverageWeather(String city) {

        WeatherAverageDto averageWeather = null;

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {

                String jpql = "SELECT AVG(r.temperature), AVG(r.windSpeed) FROM WeatherResponseDao r " +
                        "WHERE r.location = :city";

                TypedQuery<Object[]> query = session.createQuery(jpql, Object[].class);
                query.setParameter("city", city);

                List<Object[]> result = query.getResultList();

                if (!result.isEmpty() && result.getFirst() != null) {
                    Object[] averages = result.getFirst();
                    Integer averageTemperature = (averages[0] != null) ? ((Number) averages[0]).intValue() : null;
                    Integer averageWindSpeed = (averages[1] != null) ? ((Number) averages[1]).intValue() : null;

                    averageWeather = new WeatherAverageDto();
                    averageWeather.setCity(city);
                    averageWeather.setTemperature(averageTemperature);
                    averageWeather.setWindSpeed(averageWindSpeed);
                }

                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                throw e;
            }
        }

        return averageWeather;
    }
}
